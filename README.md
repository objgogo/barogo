# 바로고 API 과제

# API 문서 : http://localhost:9090/swagger-ui.html

# 프로젝트 실행
## git clone 이후 gradle 빌들 후 application 실행

## application 실행(jar or IDE를 활용해 실행)

## API test 방법
    1.Swagger를 활용
    2.Test Code 실행

회원 가입, 로그인, 배달 조회, 배달 주문 수정 서비스를 위한 Back-End API 를 정의하고,
JAVA (Spring Boot) 프로젝트로 구현해 주세요.

전제조건 (1) : 회원 가입 API를 구현해 주세요. -- ok
1.	회원가입시 필요한 정보는 ID, 비밀번호, 사용자 이름 입니다.
2.	비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로
      12자리 이상의 문자열로 생성해야 합니다.

전제조건 (2) : 로그인 API 를 구현해 주세요. -- ok
1.	사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다.
2.	ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는
      응답으로 AccessToken 을 제공합니다.

전제조건 (3) : 배달 조회 API 를 구현해 주세요. -- 
1.	배달 조회 시 필요한 정보는 기간 입니다. (기간은 최대 3일)
2.	기간 내에 사용자가 주문한 배달의 리스트를 제공합니다.

전제조건 (4) : 배달 주문 수정 API (도착지 주소 변경) 를 구현해 주세요.
1.	사용자로부터 도착지 주소를 요청 받아 처리합니다.
2.	사용자가 변경 가능한 배달인 경우에만 수정이 가능합니다.


기타
1.	DataBase 를 반드시 사용해주세요.
      (필수 데이터 외에 추가가 기본적으로 필요하다고 생각되는 데이터들도 같이 설계 해주세요.)
2.	Spring MVC 기반으로 전체 Application 설계를 해주세요.
3.	최대한 많은 예외케이스를 표현해 주세요.
4.	AccessToken 은 특별한 사유가 없다면 JWT 를 적용해주세요.
5.	Test Code 는 작성이 가능하다면 작성해주세요.
6.	API 명세서 작성이 가능하다면 같이 제공해주세요.