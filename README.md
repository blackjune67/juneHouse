JUNEHOUSE
=========

## 프로젝트 설명
> 기본적인 블로그 프로젝트

### * 기술 스택
    -  백엔드 스택
        - JAVA 11
        - SpringBoot 2.7.8
        - JPA
        - Lombok
        - h2
        - MariaDB

    - 프론트 스택
        - Vue3 (추후 변경 예정)

    - 배포 스택
        - AWS (GCP로 전환 가능)
        - 개인서버
        
### * 프로젝트를 하면서 기대하는 것
    - JAVA 8 이후의 추가된 문법 활용을 통한 학습
    - JPA를 통한 Entity 형태의 프로젝트 이해
    - 기존 sout가 아닌 단위 테스트 활용 학습
    - 백과 프론트의 API 상호작용을 통해 데이터흐름 이해
    - AWS 배포 과정을 진행해보고 서비스를 운영에 대한 이해
        - 모니터링 및 부하테스트 진행도 해볼 예정
---

### 새로운 기능
    - 문서화 기능 추가
        - Spring REST Docs 추가
    - 인증 기능 추가
        - Interceptor => ArgumentResovler로 변환
    - 회원가입 및 로그인 인증 기능 추가
        ㄴ 세션 기능 테스트
        ㄴ 쿠키 header 저장 (ResponseCookie 클래스 이용)
