package com.app.chat.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.app.chat.domain.vo.ChatMessageVO;
import com.app.chat.exception.ExternalApiException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class OpenAIClient {
	
	private final WebClient.Builder webClientBuilder;
	
	@Value("${openai.api-key}")
	private String apiKey;
	
	private String apiUrl;
	
	private int lastTotalTokens;
	
	public String askWithContext(String summary ,List<ChatMessageVO> history) {
		
		WebClient webClient = webClientBuilder.build();
		List<Map<String, String>> inputList = new ArrayList<>();
		
//		1) 기본 시스템 프롬프트
		inputList.add(Map.of(
				"role", "system",
				"content", "너는 친절한 메신저 AI 비서야."
		));
		
//		2) summary 추가 ( 있으면 )
//		summary 제공 시 기존 토큰 2996개 에서 1240개로 절감
		if(summary != null && !summary.isBlank()) {
			inputList.add(Map.of(
					"role", "system",
					"content", "이전 대화 요약: " + summary
					));
		}
		
		for(ChatMessageVO message:history) {
			
			String role = message.getUserId().equals("AI") ? "assistant" : "user";
			
			inputList.add(Map.of(
					"role", role,
					"content", message.getContent()
					));
		}
		
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-4.1-mini",
				"input", inputList
		);
		
		try {
			Map<?, ?> response = webClient.post()
				.uri(apiUrl)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> 
						clientResponse.bodyToMono(String.class)
								.map(body -> new ExternalApiException("OPENAI_CLIENT_ERROR","OpenAI 클라이언트 요청 오류: " + body))
				)
				.onStatus(HttpStatusCode::is5xxServerError, clientResponse -> 
				clientResponse.bodyToMono(String.class)
						.map(body -> new ExternalApiException("OPENAI_SERVER_ERROR","OpenAI 서버 오류: " + body))
				)
				.bodyToMono(Map.class)
				.block();
					
			return extractTextFromResponse(response, true);
					// usage 출력 (토큰 확인용)
//					input_tokens 상승 = 대화 길어짐
//					output_tokens 상승 = AI 답변 길어짐
//					total_tokens 상승 = 비용 증가
//					
		} catch (ExternalApiException e) {
			throw e;
		} catch (WebClientResponseException.TooManyRequests e) { 
			throw new ExternalApiException("OPENAI_RATE_LIMIT", "OpenAI 요청 한도를 초과하였습니다.", e);
		} catch (WebClientResponseException e) {
			throw new ExternalApiException("OPENAI_HTTP_ERROR", "OpenAI 호출 중 HTTP 오류가 발생했습니다.", e);
		} catch (Exception e) {
			throw new ExternalApiException("OPENAI_CALL_FAILED", "OpenAI 호출 중 예외가 발생했습니다.", e);
		}
	}
	
	public String summarize(List<ChatMessageVO> oldMessage) {
		
		WebClient webClient = webClientBuilder.build();
		
		StringBuilder conversation = new StringBuilder();
		
		for(ChatMessageVO message:oldMessage) {
			conversation.append(message.getUserId())
						.append(": ")
						.append(message.getContent())
						.append("\n");
		}
		
		String prompt = """
		다음 대화를 5줄 이내로 요약해줘.
		핵심 주제와 사용자의 의도를 중싱으로 정리해줘.
		""" + conversation.toString();
		
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-4.1-mini",
				"input", prompt
		);
				
		try {
			Map<?, ?> response = webClient.post()
					.uri(apiUrl)
					.header("Authorization", "Bearer " + apiKey)
					.header("Content-Type", "application/json")
					.bodyValue(requestBody)
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, clientRepsonse -> 
					clientRepsonse.bodyToMono(String.class)
					.map(body -> new ExternalApiException("OPENAI_CLIENT_ERROR", "OpenAI 요약 요청 오류: " + body))
							)
					.onStatus(HttpStatusCode::is5xxServerError, clientResponse -> 
					clientResponse.bodyToMono(String.class)
					.map(body -> new ExternalApiException("OPENAI_SERVER_ERROR", "OpenAI 요약 서버 오류: " + body))
					
							)
					.bodyToMono(Map.class)
					.block();
			
			return extractTextFromResponse(response, false);
			
		} catch(ExternalApiException e) {
			throw e;
		} catch(Exception e) {
			throw new ExternalApiException("OPENAI_SUMMARIZE_FAILED", "대화 요약 중 오류가 발생했습니다.", e);
		}
	}
	
	public String summarizeText(String combinedText) {
		
		WebClient webClient = webClientBuilder.build();
		
		String prompt = """
		다음 대화를 전체 맥락이 유지되도록 간결하게 요약해줘.
		핵심 주제와 사용자 의도를 중심으로 정리해줘.
		""" + combinedText;
		
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-4.1-mini",
				"input", prompt
		);
		
		try {
			Map<?, ?> response = webClient.post()
					.uri(apiUrl)
					.header("Authorization", "Bearer " + apiKey)
					.header("Content-Type", "application/json")
					.bodyValue(requestBody)
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, clientRepsonse -> 
					clientRepsonse.bodyToMono(String.class)
					.map(body -> new ExternalApiException("OPENAI_CLIENT_ERROR", "OpenAI 요약 요청 오류: " + body))
							)
					.onStatus(HttpStatusCode::is5xxServerError, clientResponse -> 
					clientResponse.bodyToMono(String.class)
					.map(body -> new ExternalApiException("OPENAI_SERVER_ERROR", "OpenAI 요약 서버 오류: " + body))
					
							)
					.bodyToMono(Map.class)
					.block();
			
			return extractTextFromResponse(response, false);
			
		} catch(ExternalApiException e) {
			throw e;
		} catch(Exception e) {
			throw new ExternalApiException("OPENAI_SUMMARIZE_FAILED", "대화 요약 중 오류가 발생했습니다.", e);
		}
	}
	
	public String extractTextFromResponse(Map<?, ?> response, boolean parseUsage) {
		try {
			
			if(response == null) {
				throw new ExternalApiException("OPENAI_EMPTY_RESPONSE", "OpenAI 응답이 비어 있습니다.");
			}
			
			if(parseUsage) {
				Map<?, ?> usage = (Map<?, ?>) response.get("usage");
				if(usage != null && usage.get("total_tokens") instanceof Number tokenNumber) {
					lastTotalTokens = tokenNumber.intValue();
					System.out.println("TOTAL TOKENS: " + lastTotalTokens);
				} else {
					lastTotalTokens = 0;
				}
			}
			
			Object outputObj = response.get("output");
			if(!(outputObj instanceof List<?> output) || output.isEmpty()) {
				throw new ExternalApiException("OPENAI_INVALID_RESPONSE", "OpenAI 응답 output 형식이 올바르지 않습니다.");
			}
			
			Object firstObj = output.get(0);
			if(!(firstObj instanceof Map<?, ?> first)) {
				throw new ExternalApiException("OPENAI_INVALID_RESPONSE", "OpenAI 응답 첫 번째 output 형식이 올바르지 않습니다.");
			}
			
			Object contentObj = first.get("content");
			if(!(contentObj instanceof List<?> contentList) || contentList.isEmpty()) {
				throw new ExternalApiException("OPENAI_INVALID_RESPONSE", "OpenAI 응답 content 형식이 올바르지 않습니다.");
			}
			
			Object textObjRaw = contentList.get(0);
			if(!(textObjRaw instanceof Map<?, ?> textObj)) {
				throw new ExternalApiException("OPENAI_INVALID_RESPONSE", "OpenAI 응답 text 객체 형식이 올바르지 않습니다.");
			}
			
			Object text = textObj.get("text");
			if(text == null) {
				throw new ExternalApiException("OPENAI_EMPTY_TEXT", "OpenAI 응답 텍스트가 비어 있습니다.");
			}
			
			return text.toString();
		} catch (ExternalApiException e) {
			throw e;
		} catch (Exception e) {
			throw new ExternalApiException("OPENAI_RESPONSE_PARSE_ERROR", "OpenAI 응답 파싱 중 오류가 발생했습니다.", e);
		}
	}
}
