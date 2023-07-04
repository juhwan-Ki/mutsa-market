# MiniProject_Basic_KimJuHwan
---

## 1일차 미션
---
### 중고물품 관리
---
#### EndPoint
1. POST /items (물픔 등록)
2. GET /items?page={page}&limit={limit} (물품 조회 -> 페이지 네이션)
3. GET /items/{itemId} (물품 단건 조회)
4. PUT /items/{itemId}/image (물품 수정)
5. DELETE /items/{itemId} (물품 삭제)
---


## 2일차 미션
---
### 중고 물품 댓글 관리
---
#### EndPoint
---
1. POST /items/{itemId}/comments (댓글 등록)
2. GET /items/{itemId}/comments (댓글 조회 -> 페이지 네이션)
3. PUT /items/{itemId}/comments/{commentId} (댓글 수정)
4. PUT /items/{itemId}/comments/{commentId}/reply (답글 등록)
5. DELETE /items/{itemId}/comments/{commentId} (댓글 삭제)
---


## 3일차 미션
---
### 구매 제안 관리
---
#### EndPoint
---
1. POST /items/{itemId}/proposals (구매 제안 등록)
2. GET /items/{itemId}/proposals?writer=&password=&page=1 (구매 제안 조회 -> 페이지 네이션)
3. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 수정)
4. DELETE /items/{itemId}/proposals/{proposalId} (구매 제안 삭제)
5. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 상태 변경)
6. PUT /items/{itemId}/proposals/{proposalId} (구매 제안 확정)
---

## 작업 내용
---
### 1일차 중요 작업
1. 프로젝트 전반적인 엔티티, 컨트롤러, 서비스, DTO 패키지 및 클래스 생성
2. Exception 패키지 및 Exception관련 클래스 정의 및 생성

### 2일차 중요 작업
---
1. 2일차 작업에서 사용되는 SalesItem Entity와 DTO를 요구 사항에 맞게 수정
2. Id값이 조회시 리턴되도록 DTO 메소드 수정

### 3일차 중요 작업
---
1. DTO클래스와 Parameter 클래스로 분리
2. 구매 확정된 물품에 대해 구매 제안의 상태변경, 가격수정, 삭제 등이 진행되지 않도록 수정
