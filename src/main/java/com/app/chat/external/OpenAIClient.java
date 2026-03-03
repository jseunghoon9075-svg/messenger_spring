package com.app.chat.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.chat.domain.vo.ChatMessageVO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class OpenAIClient {
	
	private final WebClient.Builder webClientBuilder;
	
	@Value("${openai.api-key}")
	private String apiKey;
	
	@Value("${openai.url}")
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
		
		return webClient.post()
				.uri(apiUrl)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(Map.class)
				.map(response -> {
					
					// usage 출력 (토큰 확인용)
//					input_tokens 상승 = 대화 길어짐
//					output_tokens 상승 = AI 답변 길어짐
//					total_tokens 상승 = 비용 증가
//					
					Map<?, ?> usage = (Map<?, ?>) response.get("usage");
					if(usage != null) {
						lastTotalTokens = ((Number) usage.get("total_tokens")).intValue();
						System.out.println("TOTAL TOKENS: " + usage.get("total_tokens"));
					}
					var output = (List<?>) response.get("output");
					var first = (Map<?, ?>) output.get(0);
					var contentList = (List<?>) first.get("content");
					var textObj = (Map<?, ?>) contentList.get(0);
					return textObj.get("text").toString();
				})
				.block();
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
				
		
		return webClient.post()
				.uri(apiUrl)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(Map.class)
				.map(response -> {
					var output = (List<?>) response.get("output");
					var first = (Map<?, ?>) output.get(0);
					var contentList = (List<?>) first.get("content");
					var textObj = (Map<?, ?>) contentList.get(0);
					return textObj.get("text").toString();
				})
				.block();
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
		
		return webClient.post()
				.uri(apiUrl)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(Map.class)
				.map(response -> {
					var output = (List<?>) response.get("output");
					var first = (Map<?, ?>) output.get(0);
					var contentList = (List<?>) first.get("content");
					var textObj = (Map<?, ?>) contentList.get(0);
					return textObj.get("text").toString();
				})
				.block();
	}
}
