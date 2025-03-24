# Schedule Project

## 개요

Schedule Project는 ..

## 주요 기능

- **....**

- **....**

## 기술 스택

- **언어**: Java
- **개발 도구**: IntelliJ IDEA,.......
- **기타**: .............

## API 명세서

| 기능 이름      | Method | Url                                 | Request                                                                       | Response | Status    |
|------------|--------|-------------------------------------|-------------------------------------------------------------------------------|----------|-----------|
| 일정 등록      | POST   | /schedule/v1/schedule               | 요청 body{    "userName" : "wonjun",    "content":"test",    "userPwd": "1234"} | 등록 정보    | 200: 정상등록 |    
| 전체 일정 조회   | GET    | /schedule/v1/schedules              | 요청 param                                                                      | 다건 응답 정보 | 200: 정상조회 |
| 선택 일정 조회   | GET    | /schedule/v1/schedule/{schedulesId} | 요청 param                                                                      | 단건 응답 정보 | 200: 정상조회 |
| 선택 일정 업데이트 | PATCH  | /schedule/v1/schedule/{schedulesId} | 요청 body{    "userName":"wonjun",    "content": "test"}                        | -        | 200: 정상수정 |

| 선택 일정 삭제 | DELETE | /schedule/v1/schedule | 요청 body | - | 200: 정상삭제 |

## DB 구조

LV 0,1,2

![img.png](img.png)

LV 도전과제

![img_1.png](img_1.png)

```
굳

```

## 코드 리뷰

3. [🛠 코드 순서](#-코드-순)
    - [Main](#아이디)

