# Messenger Spring Backend

실시간 채팅 기능에 LLM 기반 AI 응답을 통합한 Spring Boot 백엔드 프로젝트입니다.  
단순 AI 연동이 아닌, **대화 컨텍스트 관리 및 토큰 최적화 전략**을 적용했습니다.

---

##  주요 기능

- WebSocket 기반 실시간 채팅
- REST API 기반 메시지 조회
- LLM 기반 AI 응답 생성
- 대화 요약(Summary) 자동 생성
- 토큰 사용량 기반 비용 관리

---

##  LLM 통합 설계

###  문제 정의

LLM은 상태를 저장하지 않기 때문에:

- 최근 메시지만 전달 → 맥락 손실
- 전체 대화 전달 → 토큰 초과 및 비용 증가
- 대화가 길어질수록 응답 지연 가능

이를 해결하기 위해 서버에서 대화 컨텍스트를 재구성합니다.

---

###  해결 전략

1. 메시지 저장 (DB)
2. 일정 개수 초과 시 Summary 자동 생성
3. Summary + 최근 메시지를 조합해 LLM 호출
4. 응답 저장 후 WebSocket으로 실시간 전송
5. OpenAI `usage.total_tokens` 파싱 → 토큰 기준 초과 시 자동 요약 트리거

예시:

```json
{
  "usage": {
    "input_tokens": 123,
    "output_tokens": 45,
    "total_tokens": 168
  }
}
```
```text
  User Client (React/Vue)
        ↓ WebSocket / REST
Spring Controller
        ↓
ChatService
   (1) 메시지 저장 → DB
   (2) Summary 필요 여부 판단
   (3) Context 조합 → LLM 호출
        ↓
LLM 응답 수신
        ↓
DB 저장
        ↓
WebSocket 브로드캐스트
```

---

### 계층 책임

- Controller: 요청 처리 및 라우팅
- Service: 비즈니스 로직, 트랜잭션, LLM 호출
- Repository: 영속성 관리

---

### 설계 의도

Stateless LLM 특성을 보완하기 위해 서버에서 상태 관리

토큰 한계 및 비용 문제를 고려한 요약 전략

확장성을 고려한 계층 분리 구조

단순 AI API 호출이 아닌,
실무형 AI 통합 구조를 목표로 설계했습니다.

---

### 기술 스택

- Java 17
- Spring Boot
- WebSocket
- MyBatis
- OpenAI API
- Oracle

---

### 사용 툴

- VSCode
- STS
- DBeaver
