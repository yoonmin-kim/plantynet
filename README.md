## [목표]
<br>

### (주)플랜티넷 자격요건에 해당하는 기술들을 활용하여 간단한 CRUD구현

<br>
<hr>
<br>

### [Skills & Tools]

* Spring Boot
* Spring Security
* JWT
* Redis 
* JSP/JSTL
* Thymeleaf
* React
* MySQL, Mybatis
* gradle
* IntelliJ

<hr>
<br>

### [구성]

* Thymeleaf 컨트롤러
  * /thymeleaf/** 요청은 Thymeleaf 컨트롤러에서 RequestMapping 처리한다
  * Thymeleaf 컨트롤러는 뷰화면을 Thymeleaf 템플릿으로 작성한 페이지로 처리한다
* Jsp 컨트롤러
  * /jsp/** 요청은 Jsp 컨트롤러에서 RequestMapping 처리한다
  * Jsp 컨트롤러는 뷰화면을 Jsp 템플릿으로 작성한 페이지로 처리한다
* React 컨트롤러
  * /api/** 요청은 React 컨트롤러에서 RequestMapping 처리한다
  * React 컨트롤러는 뷰화면을 React 로 작성한 페이지로 처리한다
  * RestController 로 작성한다
* User 컨트롤러
  * /user/** 요청은 User 컨트롤러에서 RequestMapping 처리한다
  * 로그인화면, 회원가입화면을 Thymeleaf 템플릿으로 작성한 페이지로 처리한다
  * 회원가입 기능을 처리한다
  <br>

### [기능목록]

* 회원가입
* 로그인(인증)
* 상품등록
* 상품목록
* 상품수정(인가)
* 상품삭제(인가)
* 검증기능
<br>

### [제한조건]

* 검증기능은 다음의 제한조건을 충족해야한다
  * 등록 및 수정시 상품명, 가격, 수량을 필수로 입력받는다
  * 가격과 수량의 입력값은 정해진 범위를 만족해야 한다
  * 위 두 조건을 만족하지 못 할 경우 사용자 입력화면에 잘못<br>
    된 값에 대한 표시를 한다
* 인증기능은 다음의 제한조건을 충족해야한다
  * 인증되지 않은 사용자는 로그인, 회원가입 페이지 외 다른페이지에는 접근할 수 없다
  * Thymeleaf, Jsp 로그인 페이지에서 인증요청을 할 경우 세션을 통해 처리한다
  * React 로그인 페이지에서 인증요청을 할 경우 JWT 를 통해 처리한다
* 인가기능은 다음의 제한조건을 충족해야한다
  * 상품수정 페이지는 MANAGER, ADMIN 권한의 사용자만 접근할 수 있다
  * 상품삭제 페이지는 ADMIN 권한의 사용자만 접근할 수 있다
<br>







