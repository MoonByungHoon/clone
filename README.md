# 배달 어플 Clone 코딩

- 배달 어플 API.

## 프로젝트 소개

- 학습한 내용을 활용하여 구축한 배달 어플 API.
- DB 커넥션을 최적화 하기 위한 OSIV OFF.
- tdd 개발

## 개발 기간

- 24.01.23 ~

## ERD 다이어그램

![erd](https://github.com/MoonByungHoon/clone/assets/106061341/9d144988-316e-4602-bf39-d2f9fa1deb7d)

### 개발환경

1. 언어 : Java
2. 프레임워크, 라이브러리 : Spring Spring Boot, JPA, Spring Data JPA, QueryDSL, Spring Security, AssertJ(test)
3. DB, 서버 : H2 DataBase(test), MySQL, AWS
4. Tool : Github, PostMan

### 주요 기능

- 회원가입(Oauth 2.0 추가)
- 로그인(JWT 발급)
- 회원정보 수정 및 탈퇴
- ID찾기, PW찾기
- 스토어 등록, 수정, 삭제
- 상품 등록, 수정, 삭제
- 주문, 주문 취소
- 상품 후기 및 자영업자 코멘트
