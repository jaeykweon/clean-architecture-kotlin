# Example Implementation of a Hexagonal Architecture - kotlin ver.

`만들면서 배우는 클린 아키텍처` 코틀린 버전

---

## 소개

- 도서 [만들면서 배우는 클린 아키텍처](https://wikibook.co.kr/clean-architecture/) 의 [예제 코드](https://github.com/wikibook/clean-architecture) 를 코틀린으로 마이그레이션 한 프로젝트입니다.
    - 대부분 동일하게 변환하였으나 일부 변경 사항이 있고, 해당 내용들은 아래 '프로젝트 안내' 항목을 참고해주시면 됩니다.
  
- 이 프로젝트의 목적은 다음과 같습니다
  - 클린 아키텍처 (특히 포트 앤 어댑터 패턴)의 설계 및 구현
  - 자바&코틀린 이해도 업그레이드

### 프로젝트 안내

#### 파일 위치를 동일하게 작성하였습니다. 

#### 원본 코드와 최대한 동일하게 동작하도록 작성하였지만, 코틀린의 언어적 특성으로 인해 생략되거나 변경된 코드들이 있습니다.

#### kotlin test framework인 kotest의 편리함을 널리 알리고자, assertThat 대신 shouldBe를 사용하였습니다.

## 더 알아보기

- [[네이버 엔지니어링] 지속 가능한 소프트웨어 설계 패턴: 포트와 어댑터 아키텍처 적용하기](https://engineering.linecorp.com/ko/blog/port-and-adapter-architecture)
- [[우아한 기술블로그] 주니어 개발자의 클린 아키텍처 맛보기](https://techblog.woowahan.com/2647/)
- [NHN FORWARD 22] 클린 아키텍처 애매한 부분 정해 드립니다.
  - [(영상)](https://www.youtube.com/watch?v=g6Tg6_qpIVc)
  - [(정리한 글)](https://velog.io/@ja2ykweon/%EA%B0%95%EC%97%B0-%EB%82%B4%EC%9A%A9-%EC%A0%95%EB%A6%ACNHN-FORWARD-22-%ED%81%B4%EB%A6%B0-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%95%A0%EB%A7%A4%ED%95%9C-%EB%B6%80%EB%B6%84-%EC%A0%95%ED%95%B4-%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4)
