# 🦮NewsFeedProject - 반려멍!🐶

 반려인들이 한데 모여 반려동물과 함께한 소소한 일상들을 기록&공유할 수 있는 뉴스피드입니다.
</br></br>

 ### 팀원
 >신유섭, 유동근, 최창규, 장숭혁

</br></br>

### 💻 프로젝트 소개
---
**회원가입 절차는 이메일로 발송된 인증코드를 사용하여 진행합니다. 인증코드를 입력하여 회원가입을 완료하면 로그인이 가능합니다. 로그인 후에는 토큰을 사용하여 글을 수정할 수 있습니다.** 
>1. 프로필 기능 - 개인정보가 기재된 페이지로, 로그인 후에 조회 및 수정이 가능합니다. 사용자는 자신의 개인정보를 확인하고 필요에 따라 수정할 수 있습니다.
>2. 게시글 기능 - 한 명의 유저가 여러 개의 게시글을 작성할 수 있는 기능입니다. 사용자는 자신의 글을 게시, 수정, 삭제할 수 있으며, 누구나 조회할 수 있습니다.
>3. 댓글 기능 - 하나의 게시글에 여러 댓글을 작성할 수 있는 기능입니다. 게시글과 마찬가지로, 댓글 작성자는 자신의 댓글만 게시, 수정, 삭제할 수 있고, 누구나 조회할 수 있습니다.
>4. 회원 탈퇴 - 회원 탈퇴 기능을 제공합니다. 로그인 후 자신의 아이디를 삭제하여 회원 탈퇴할 수 있습니다.

</br></br>


### 📄간략한 API 명세서 -- ( [🗞자세한 APT 명세서👈](https://www.notion.so/673f1f087f094ba88422c8b03080bb04?v=a188fd957cc944eaa582d5107610007b)  )
기능|Method|url|카테고리|
---|---|---|---|
댓글생성|POST|/api/v1/comment|댓글기능
댓글수정|PATCH|/api/v1/comment|댓글기능
댓글삭제|DELETE|/api/v1/comment/{id}|댓글기능
댓글목록조회|GET|/api/v1/comment/{postId}|댓글기능
게시글목록|GET|/api/v1/posts|게시글기능
게시글생성|POST|/api/v1/post|게시글기능
게시글수정|PATCH|/api/v1/post/{id}|게시글기능
게시글선택|GET|/api/v1/post/{id}|게시글기능
게시글삭제|DELETE|/api/v1/post/{id}|게시글기능
프로필조회|GET|/api/v1/profile/{id}|프로필기능
프로필수정|PATCH|/api/v1/profile/{id}|프로필기능
로그인|POST|/api/v1/login|인증기능
회원가입|POST|/api/v1/signup|인증기능
토큰재발급|GET|/api/v1/refresh|인증기능
탈퇴|DELETE|/api/v1/signout|인증기능
이메일인증|POST|/api/v1/signup/email|인증기능

<br/><br/>

### 💿 ERD 
---
![Untitled](https://github.com/shin3766/NewsFeedProject/assets/79851594/45e0fdbe-3117-40d2-86dc-456c1c77f925)

<br/><br/>

### 🖥 와이어 프레임

<img width="664" alt="와이어 프레임" src="https://github.com/shin3766/NewsFeedProject/assets/79851594/0b07839e-5200-49d7-9e45-f33470c2ce47">

<br/><br/>

### 🌳기술 스택
---

<img src="https://img.shields.io/badge/intellijidea-E34F26?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/docker-blue?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">





