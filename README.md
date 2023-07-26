# ♻️멋사마켓♻️
---
## 멋사마켓이란?

<aside>
💡 여러분들이 많이 사용하고 있는 🥕당근마켓, 중고나라 등을 착안하여 여러분들만의 중고 제품 거래 플랫폼을 만들어보는 미니 프로젝트입니다.

사용자가 중고 물품을 자유롭게 올리고, 댓글을 통해 소통하며, 최종적으로 구매 제안에 대하여 수락할 수 있는 형태의 중고 마켓 프로젝트입니다.
</aside>

### 사용한 기술
1. JAVA - 19
2. SpringBoot - 3.1.1
3. SpringDataJPA
4. Lombok
5. SQLite

### 테스트 도구
1. Postman

## 1일차 미션
### 중고물품 관리
---
#### EndPoint
1. POST /items (물픔 등록)
2. GET /items?page={page}&limit={limit} (물품 조회 -> 페이지 네이션)
3. GET /items/{itemId} (물품 단건 조회)
4. PUT /items/{itemId}/image (물품 수정)
5. DELETE /items/{itemId} (물품 삭제)


## 2일차 미션
### 중고 물품 댓글 관리
---
#### EndPoint
1. POST /items/{itemId}/comments (댓글 등록)
2. GET /items/{itemId}/comments (댓글 조회 -> 페이지 네이션)
3. PUT /items/{itemId}/comments/{commentId} (댓글 수정)
4. PUT /items/{itemId}/comments/{commentId}/reply (답글 등록)
5. DELETE /items/{itemId}/comments/{commentId} (댓글 삭제)


## 3일차 미션
### 구매 제안 관리
---
#### EndPoint
1. POST /items/{itemId}/proposals (구매 제안 등록)
2. GET /items/{itemId}/proposals?writer=&password=&page=1 (구매 제안 조회 -> 페이지 네이션)
3. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 수정)
4. DELETE /items/{itemId}/proposals/{proposalId} (구매 제안 삭제)
5. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 상태 변경)
6. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 확정)

## 작업 내용
### 1일차 중요 작업
1. 프로젝트 전반적인 엔티티, 컨트롤러, 서비스, DTO 패키지 및 클래스 생성
2. Exception 패키지 및 Exception관련 클래스 정의 및 생성

### 2일차 중요 작업
1. 2일차 작업에서 사용되는 SalesItem Entity와 DTO를 요구 사항에 맞게 수정
2. Id값이 조회시 리턴되도록 DTO 메소드 수정

### 3일차 중요 작업
1. DTO클래스와 Parameter 클래스로 분리
2. 구매 확정된 물품에 대해 구매 제안의 상태변경, 가격수정, 삭제 등이 진행되지 않도록 수정

# 멋사마켓 고도화
---

## 작업 내용
### 1일차 주요 작업
1. 1. 사용자는 **회원가입**을 진행할 수 있다.
    - 회원가입에 필요한 정보는 아이디와 비밀번호가 필수이다.
    - 부수적으로 전화번호, 이메일, 주소 정보를 기입할 수 있다.
    - 이에 필요한 사용자 Entity는 직접 작성하도록 한다.
2. 아이디 비밀번호를 통해 로그인을 할 수 있어야 한다.
3. 아이디 비밀번호를 통해 로그인에 성공하면, JWT가 발급된다. 이 JWT를 소유하고 있을 경우 **인증**이 필요한 서비스에 접근이 가능해 진다.
    - 인증이 필요한 서비스는 추후(미션 후반부) 정의한다.    
4. JWT를 받은 서비스는 사용자가 누구인지 사용자 Entity를 기준으로 정확하게 판단할 수 있어야 한다.

### 1일차 작업 API 문서
https://documenter.getpostman.com/view/28055564/2s946pYU8f
