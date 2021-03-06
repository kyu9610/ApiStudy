# ApiStudy
(Spring Security + JWT 적용) 게시판 만들기

### 프로젝트 설명
- Spring Security 와 JWT를 활용하여 간단한 게시판을 만드는 프로젝트입니다.

### 프로젝트 설정
- Gradle
- Java 11

### dependency 
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- JWT


Application.properties
```
server.port=8080

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.username=아이디
spring.datasource.password=패스워드
spring.datasource.url=jdbc:mysql://localhost:3306/스키마명

# create, update, none, create-drop, validate,
spring.jpa.hibernate.ddl-auto=update

## Jpa Properties
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

## Logging Level
logging.level.org.hibernate.type.descriptor.sql=debug

spring.security.user.name=root
spring.security.user.password=root
spring.security.user.roles=ADMIN
```
