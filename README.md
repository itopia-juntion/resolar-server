# 📚 Resolar - AI 학습 자료 관리 시스템

Spring Boot 기반의 AI 지원 학습 자료 수집 및 관리 시스템입니다.

## 🚀 주요 기능

- **🔐 사용자 인증**: JWT 기반 회원가입/로그인
- **📂 주제 관리**: 학습 주제별 자료 분류 및 관리
- **🤖 AI 콘텐츠 분석**: 자동 요약, 중요도 평가, 유사도 검색
- **📄 PDF 보고서 생성**: 학습 자료 요약본 및 종합 보고서
- **🔍 스마트 검색**: AI 의미 검색과 키워드 검색 지원

## 🛠 기술 스택

- **Backend**: Spring Boot 3.5.5, Java 17
- **Database**: MySQL (운영) / H2 (개발)
- **Security**: JWT, Spring Security
- **PDF**: iText7 (한글 폰트 지원)
- **API 문서**: OpenAPI 3.0 (Swagger)
- **Container**: Docker, MySQL 8.0

## 📊 시스템 아키텍처

```
Client → Spring Boot API → AI Service (ngrok)
              ↓
           MySQL DB
```

## 🏗 프로젝트 구조

```
src/main/java/itopia/resolar/
├── application/        # 애플리케이션 계층
│   ├── config/        # 설정 (Security, CORS, RestClient)
│   ├── external/      # 외부 AI 서비스 연동
│   ├── pdf/           # PDF 생성 서비스
│   └── security/      # JWT 인증 처리
├── domain/            # 도메인 계층
│   ├── user/         # 사용자 관리
│   ├── subject/      # 주제 관리
│   └── page/         # 학습 자료 관리
└── common/           # 공통 유틸리티
```

## 📋 API 엔드포인트

### 🔐 인증
- `POST /api/auth/join` - 회원가입
- `POST /api/auth/login` - 로그인

### 📂 주제 관리
- `GET /api/subjects` - 내 주제 목록
- `POST /api/subjects` - 주제 생성
- `POST /api/subjects/summary/{id}` - PDF 요약본 생성
- `DELETE /api/subjects/{id}` - 주제 삭제

### 📄 학습 자료 관리
- `POST /api/pages` - 자료 추가 (AI 분석)
- `GET /api/pages` - 주제별 자료 목록
- `GET /api/pages/search` - 키워드 검색

## 🚦 실행 방법

### 1. 데이터베이스 실행
```bash
docker-compose up -d
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. API 문서 확인
- Swagger UI: http://localhost:8080/swagger-ui/

## 🔧 환경 설정

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/resolar
    username: resolar
    password: resolar123
ai:
  service:
    url: https://your-ai-service.ngrok.app
```

## 🤖 AI 서비스 연동

외부 AI 서비스를 통해 다음 기능을 제공합니다:

- **콘텐츠 분석** (`/api/v1/analyze`): 요약 및 중요도 자동 생성
- **의미 검색** (`/api/v1/search`): AI 기반 유사 콘텐츠 검색
- **보고서 생성** (`/api/v1/generate-report`): 종합 학습 보고서

## 📱 사용자 플로우

1. **회원가입/로그인** → JWT 토큰 발급
2. **주제 생성** → 학습 분야별 분류
3. **자료 수집** → 웹페이지 URL 입력 → AI 분석
4. **자료 검색** → 키워드/의미 기반 검색
5. **보고서 생성** → PDF 요약본 다운로드

## 🔒 보안

- **JWT 인증**: HS256 알고리즘, 1시간 유효
- **CORS 설정**: 프론트엔드 연동 지원
- **접근 제어**: 사용자별 데이터 격리

## 📊 데이터 모델

- **User**: 사용자 정보 및 권한
- **Subject**: 학습 주제 (1:N with Pages)
- **Page**: 학습 자료 (URL, 요약, 중요도)

## 🌟 특징

- **한글 완벽 지원**: PDF 생성, 검색, UI
- **AI 기반 분석**: 자동 요약 및 중요도 평가
- **유연한 검색**: 키워드 + AI 의미 검색
- **모바일 친화적**: RESTful API 설계
- **확장 가능**: 마이크로서비스 아키텍처 준비

## 📝 라이선스

MIT License

## 👥 기여

이슈나 PR은 언제든 환영합니다!