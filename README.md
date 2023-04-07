# 바로고 API 과제

# API 문서 : http://localhost:9090/swagger-ui.html

# 프로젝트 실행
## git clone 이후 gradle 빌들 후 application 실행
## clone url : https://github.com/objgogo/barogo.git

## application 실행(jar or IDE를 활용해 실행)

# 개발 환경
      java 11
      spring-boot 2.7.10
      spring-security
      jpa
      H2
      gradle

# API test 방법   
### Swagger를 활용
      1. Application 실행 후 http://localhost:9090/swagger-ui.html 접속
      2. 회원 가입 API 실행( role은 ADMIN-관리자, USER-사용자, DELIVERY-라이더)
      
      
### Test Code 실행
      1. ApiTest 파일 확인
      2. 독립적으로 실행 해야 테스트 가능