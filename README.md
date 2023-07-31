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
1. 사용자는 **회원가입**을 진행할 수 있다.
 - 회원가입에 필요한 정보는 아이디와 비밀번호가 필수이다.
 - 부수적으로 전화번호, 이메일, 주소 정보를 기입할 수 있다.
 - 이에 필요한 사용자 Entity는 직접 작성하도록 한다.
3. 아이디 비밀번호를 통해 로그인을 할 수 있어야 한다.
4. 아이디 비밀번호를 통해 로그인에 성공하면, JWT가 발급된다. 이 JWT를 소유하고 있을 경우 **인증**이 필요한 서비스에 접근이 가능해 진다.
 - 인증이 필요한 서비스는 추후(미션 후반부) 정의한다.    
5. JWT를 받은 서비스는 사용자가 누구인지 사용자 Entity를 기준으로 정확하게 판단할 수 있어야 한다.

POST /users/join
```json
{
    "username": "kevin2",
    "password": "kevin1234",
    "passwordCheck": "kevin1234"
}
```

POST /users/login
```
{
    "username": "kevin",
    "password": "kevin1234"
}
```


### 2일차 주요 작업
1. 아이디와 비밀번호를 필요로 했던 테이블들은 실제 사용자 Record에 대응되도록 ERD를 수정하자.
    - ERD 수정과 함께 해당 정보를 적당히 표현할 수 있도록 Entity를 재작성하자.
    - 그리고 ORM의 기능을 충실히 사용할 수 있도록 어노테이션을 활용한다.
    
2. 다른 작성한 Entity도 변경을 진행한다.
    - 서로 참조하고 있는 테이블 관계가 있다면, 해당 사항이 표현될 수 있도록 Entity를 재작성한다.
  
Comment
```java
@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String password;
    private String content;
    private String reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SalesItem salesItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
```

Negotiation
```java
@Entity
@Getter
@Setter
public class Negotiation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nego_id")
    private Long id;

    @Column(name = "suggested_price")
    private Integer suggestedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SalesItem salesItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
```

SalesItem
```java
@Entity
@Table(name = "salse_item")
@Getter
@Setter
public class SalesItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String title;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "min_price_wanted")
    private Integer minPriceWanted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "salesItem")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "salesItem")
    private List<Negotiation> negotiations = new ArrayList<>();
}
```
  

### 3일차 주요 작업
1. 본래 “누구든지 열람할 수 있다”의 기능 목록은 사용자가 인증하지 않은 상태에서 사용할 수 있도록 한다.
    
2. 작성자와 비밀번호를 포함하는 데이터는 인증된 사용자만 사용할 수 있도록 한다.

### salseItem
---
GET /items?page&limit
```
page = 0
limit = 25
```

GET /items/{itemId}

POST /items
```json
{
    "title": "중고 맥북 팝니다2",
    "description": "2019년 맥북 프로 13인치 모델입니다",
    "minPriceWanted": 1000000
}
```

DELETE /items/{itemId}
```json
{
}
```

### comment
---
POST /items/{itemId}/comments
```json
{
    "content": "할인 가능하신가요?"
}
```

GET /items/{itemId}/comments

PUT /items/{itemId}/comments/{commentId}
```json
{
    "content": "할인 가능하신가요? 1000000 정도면 고려 가능합니다"
}
```

PUT /items/{itemId}/comments/{commentId}/reply
```json
{
    "reply": "안됩니다"
}
```

DELETE /items/{itemId}/comments/{commentId}
```json
{}
```

### negotiation
---

POST /items/{itemId}/proposal
```json
{
    "suggestedPrice": 1000000
}
```

GET /items/{itemId}/proposal

(가격변경)PUT /items/{itemId}/proposals/{proposalId}
```json
{
    "suggestedPrice": 1100000
}
```

(상태변경)PUT /items/{itemId}/proposals/{proposalId} 
```json
{
    "status": "확정"
}
```

POST /items/{itemId}/proposals/{proposalId}
```json
{}
```
