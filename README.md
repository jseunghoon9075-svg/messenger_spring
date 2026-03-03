##  한 줄 요약

Spring 백엔드에서 **LLM을 통합해 맥락 기반 AI 응답 생성 + 토큰/비용 최적화 전략**을 적용한 대화 서비스입니다.

---

##  문제 정의

LLM 호출 시 다음과 같은 이슈가 존재합니다:

• 최근 메시지만 전달 → **맥락 손실**  
• 전체 대화 전달 → **토큰 한계/비용 증가**  
• 대화 길어질수록 → **응답 지연 가능**

이를 해결하기 위해 서버에서 컨텍스트를 **요약 + 최근 메시지 기반으로 재구성**합니다.

---

##  해결 전략

1. **메시지 저장** → DB  
2. 대화가 일정 개수(예: **20개**) 초과 시 **요약 문 자동 생성**  
3. 요약 + 최근 메시지를 LLM 입력으로 조합  
4. LLM 응답 **저장 + WebSocket 전송**  
5. OpenAI `usage.total_tokens` 파싱 → **토큰 기준 초과 시 자동 요약 트리거**

```json
{
  "usage": {
    "input_tokens": 123,
    "output_tokens": 45,
    "total_tokens": 168
  }
}
```
## 아키텍처 개요

User Client (React/Vue)
        ↓ WebSocket / REST
Spring WebSocket/REST Controller
        ↓ ChatService
(1) 메시지 저장 → DB
(2) Summary 판단 → 요약 저장
(3) Context 조합 → LLM 호출
        ↓ LLM 응답 수신
        ↓ DB 저장
        ↓ WebSocket 브로드캐스트

- Service: 트랜잭션/비즈니스 로직
- Repository: 영속성 처리
- Controller: 입출력/라우팅
