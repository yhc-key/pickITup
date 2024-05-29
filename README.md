# pick IT up (사용자 맞춤형 채용 공고 추천 서비스✨)

### 💙 SSAFY 10기 특화 프로젝트 서울 4반 🐥F5 세희고침🐥 (2024.2.17 ~ 2024.04.04)

<img src="./assets/pickitup.png" width="80%">

<br/>

# 📜 목차

##### 1️⃣ [서비스 소개](#-서비스-소개)

##### 2️⃣ [서비스 화면](#-서비스-화면)

##### 3️⃣ [개발 환경](#-개발-환경)

##### 4️⃣ [기술 특이점](#-기술-특이점)

##### 5️⃣ [기획 및 설계 산출물](#-기획-및-설계-산출물)

##### 6️⃣ [Conventions](#-conventions)

##### 7️⃣ [팀원 소개 및 개발 회고](#-팀원-소개-및-개발-회고)

<br/>

# 서비스 소개 🎈

### 📌 Overview
-  `취업 준비생`을 위한 `All-in-One` 플랫폼
-  취업 준비 과정의 효율성을 극대화

### 🎯 타겟

- 기술 스택 기반으로 IT 채용 공고를 보고 싶은 취준생들
- 너무 많은 정보량으로 힘들어하는 추천이 필요한 취준생들
- 기술 면접 대비를 하고 싶은 사람들
- 자기소개서를 관리하고 싶은 취준생, 이직생들
    > 👉 **취업을 준비하는 모든 사람들!**

### ☁ 기획 배경

- 현존하는 대부분의 취업 공고 관련 사이트들은 `공고를 클릭하여 자격요건 및 우대사항을 확인`해야 하기 때문에 자신과 맞는 기술 스택을 찾을 때까지 `여러 번의 탐색 과정`을 거쳐야 한다.
- 그래서 `역으로 기술 스택을 가지고 채용 공고를 사용자에게 개인화하여 추천`하는 취업 준비생들의 부담을 덜어 줄 수 있는 서비스를 기획
- IT 취준생이 취업 준비에 필요한 자기소개서 및 스케줄 관리를 도와주는 서비스를 기획

### 📌 서비스 구현(✅ : 구현 완료 ❌ : 미구현)

#### 1️. 기본기능 ✅
- 회원가입 / 회원탈퇴 ✅
- 로그인 / 로그아웃 ✅
- 채용 공고 조회 ✅
- 채용 공고 스크랩 ✅
- 활동 업적 카운트 및 뱃지 서비스 ✅

#### 2️. 추가기능 ✅
- 소셜 로그인( 구글 / 네이버 / 카카오) ✅
- 채용 공고 검색 및 tech 필터 ✅
- 스피드 퀴즈 / OX 퀴즈 ✅
- 자기소개서 이력 관리 ✅

#### 3️. 심화기능 ⚠️

- 채용 공고 추천 ✅
- 기술 블로그 추천 ❌
- 캘린더 일정 관리 기능 ❌

<br >

# 서비스 화면🎬
### ✨ 모든 페이지 `모바일(아이폰 12 Pro 기준 max-width:480px)` 지원

### 온보딩
- `fullpage`를 적용한 온보딩

![web_온보딩](./assets/web_온보딩.gif)

### 회원가입 & 로그인
- 카카오, 네이버, 구글 소셜 로그인
- 자체 회원가입 및 로그인

![web_회원가입로그인](./assets/web_회원가입로그인.gif)

### 채용 공고 조회 및 검색
- `마감일순 정렬`로 전체 채용 공고 조회 가능
- `스크랩`하여 마이페이지에서 확인 가능
- `키워드 검색` 및 `기술 스택 필터 적용`하여 검색 가능 

![web_채용공고](./assets/web_채용공고.gif)

### 추천 공고
- `선호하는 기술 스택`, `거주지와의 거리`
- 공고 클릭 수, 스크랩 데이터와 타사용자와의 유사성 분석으로 채용공고 추천

![web_추천공고](./assets/web_추천공고.gif)

### 미니게임
- 사용자 경험을 위해 데이터 미제공 페이지 추가
- 정답 개수에 따라 승리 횟수 증가 및 `뱃지 획득`

#### 스피드 퀴즈
- 한 문제당 10초의 제한시간을 두고 정답 맞추기

![web_스피드퀴즈](./assets/web_스피드퀴즈.gif)

#### OX 퀴즈
![web_ox퀴즈](./assets/web_ox퀴즈.gif)

### 면접 대비
- 한 문제당 40초의 제한시간을 두고 답안 작성
- 마이페이지에서 `작성 답변` 및 `예시 답변` 조회 가능

![web_면접대비](./assets/web_면접대비.gif)

### 자기소개서 관리
 유사한 항목별 `자기소개서 관리`

![web_자기소개서관리](./assets/web_자기소개서관리.gif)

### 마이 페이지
- `추천`을 위한 `선호 기술 스택 선택 및 변경`
- `프로필 이미지 및 기본정보 변경`
- 내가 `스크랩`한 채용공고
- `뱃지 조회`

![web_마이페이지](./assets/web_마이페이지.gif)

<br />

# 개발 환경 🖥

## 🖱 Frontend

![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC.svg?&style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![tailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)<br>
![Next JS](https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![React Query](https://img.shields.io/badge/-React%20Query-FF4154?style=for-the-badge&logo=react%20query&logoColor=white)
<br>

#### 상세 스택

    VScode 2023.3.4
    Version: 1.86.2 (user setup)
    Node.js: 18.17.1
    V8: 11.8.172.18-electron.0
    Nextjs : 14.1.1
    react: ^18

## 🖱 Backend

![Intellij IDEA](https://img.shields.io/badge/Intellij%20IDEA-000000.svg?&style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white)
![SpringBoot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D.svg?style=for-the-badge&logo=Swagger&logoColor=black)<br>
![SpringSecurity](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring%20Security&logoColor=white)
![Elastircsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white)
![Mysql](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)<br>
![Python](https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![Scala](https://img.shields.io/badge/-Scala-DC322F?style=for-the-badge&logo=scala&logoColor=white)
![Scala](https://img.shields.io/badge/-Apachespark-E25A1C?style=for-the-badge&logo=Apachespark&logoColor=white)

#### 상세 스택

    IntelliJ 2023.3.4
    SpringBoot 3.2.3
    Gradle 8.5
    Lombok 1.18.16
    Hibernate 3.2.1
    Swagger 4.18.2
    Spring Security 6.2.2
    Python 3.12.2
    Scala 2.12.16 ( + openJDK-8u342 )
    sbt 1.7.2
    Play Framework 2.8.21
    Spark 3.0.2
    mongo-spark-connector 3.0.2

## 🖱 CI/CD

![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![EC2](https://img.shields.io/badge/EC2-232F3E?style=for-the-badge&logo=Amazon-ec2&logoColor=white)

#### 상세 스택

    docker 25.0.4
    docker-compose 2.21.0
    jenkins LTS 2.440.1
<br>

## 🎨 UI/UX

![Figma](https://img.shields.io/badge/Figma-F24E1E.svg?style=for-the-badge&logo=Figma&logoColor=white)
<br>

## 👨‍👩‍👧 협업 툴

- <strong>형상 관리<br>
  ![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)
  ![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)

- <strong>이슈 관리<br>
  ![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)

- <strong>커뮤니케이션<br>
  ![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
  ![Mattermost](https://img.shields.io/badge/Mattermost-0072C6?style=for-the-badge&logo=mattermost&logoColor=white)
  ![KakaoTalk](https://img.shields.io/badge/kakaotalk-ffcd00.svg?style=for-the-badge&logo=kakaotalk&logoColor=000000)
  <br>

<br />

# 기술 특이점 🧰

## 🖱 Frontend

### 1. NextJS 활용 SEO를 고려한 SSR방식 웹사이트 제작
### 2. NextJS 활용 데이터 캐싱으로 UX 향상
### 3. Zustand를 활용해 전역 상태 관리
### 4. React-query를 활용해 검색 기능 Infinite Scroll 구현
### 5. refreshToken과 accessToken으로 로그인 로직 구현

## 🖱 Backend

### ✨ 1. MySQL Master-Slave

<div align="start">
<img src="./assets/masterSlave.jpg" alt="masterSlave" width="50%" height="70%">
</div>

**MySQL**을 **Master DB**와 **Slave DB**로 나누어서 **분산 환경을 구축**했습니다.

DB 분산 환경을 통해 **Master DB에 장애가 발생하더라도 Slave DB를 활용해서 복구할 수 있도록** 했습니다.

또한, 읽기 작업을 Slave DB로 분산시켜서 **서비스의 응답을 향상**시키고 **대규모 트래픽에 대응**할 수 있도록 했습니다. 사용자 상호작용 데이터가 쌓이는 쓰기 작업이 DB에 부하를 줄 수 있기 때문에, 읽기 작업을 Slave DB에서 전담하도록 했습니다.

그리고 읽기와 쓰기 작업을 분리하였기 때문에 스프링 프로젝트에서도 **CQRS 패턴을 통해 Command 작업과 Query 작업을 구분해서 작업**하였습니다.

**시스템의 가용성과 성능을 향상** 시킬 수 있으며 **CQRS 패턴과 결합을 통해 시스템의 확장성**을 높일 수 있어서 개발 및 운영 측면에서 많은 이점을 누릴 수 있는 기술이라고 생각합니다.

### ✨ 2. Apache Spark (+ Scala)


<div align="start">
<img src="./assets/spark.png" alt="spark" width="50%" height="70%">
</div>

데이터 처리를 위해 **Apache Spark**를 사용했습니다. Spark 애플리케이션을 작성할 수 있는 언어로 대표적으로 Python과 Scala가 있는데, **Spark 자체가 Scala 언어로 작성**돼있고 **JVM 상에서 구동된다는 점**에서 직접적인 호환성으로 인한 비교적 우수한 성능과 높은 신규 API 커버리지와 더불어 JVM 언어의 타입 안정성 등을 고려해서 **Scala를 사용**하기로 했습니다.

메인 서버인 Spring 서버 또한 같은 JVM 언어인 Java를 사용하기 때문에 Scala로 작성된 Spark 애플리케이션 코드를 Spring 서버 상에서 실행하려 하였으나, **Spring 3.x 버전부터 JDK 17버전을 사용하고 하둡 생태계에 대한 최신 버전 지원이 중단**됨으로 인해 **하둡 생태계에 속한 Spark와의 JDK 버전 호환 문제가 발생**했고, **추천 서버를 분리**하기로 결정했습니다.

**추천 기능 서버**는 2.0 버전부터 Scala를 메인 언어로 지원하는 **경량 웹 프레임워크인 Play framework로 개발**했습니다. Spring 서버와 RESTful API를 통해 통신하며, Spring 서버로 추천 요청이 오면 Play 서버와의 HTTP 통신을 통해 추천 결과를 회신하도록 구축했습니다.

**Spark에서 읽어올 데이터를 저장할 데이터베이스**로는 대규모 데이터셋에 대한 빠른 읽기 성능을 보이며 추후 분산 환경 구축을 고려했을 때 수평적 확장에 용이한 NoSQL 데이터베이스인 **MongoDB**를 채택했습니다.

**데이터 처리**에는 **Spark Dataframe API를 활용**했습니다. Dataframe API는 **SQL과 유사한 방식으로 코드를 더 간결하고 직관적인 방식으로 작성할 수 있고 관계형 DB와 유사한 옵티마이저 기능을 제공**하기 때문에 Spark에서 기본적으로 사용되는 RDD API보다 더 나은 성능으로 데이터를 처리할 수 있다는 점에서 채택하게 되었습니다.

### ✨ 3. Jenkins CI/CD 파이프라인 고도화

Jenkins의 **Generic Webhook Trigger 플러그인**을 활용하여 백엔드 빌드/배포 파이프라인을 세분화    했습니다. 

프로젝트를 위해 주어진 **하나의 GitLab repository를 FE/BE가 공유**해야하고 **BE 내에서도 두 개의 다른 서버가 개발**되다보니 빌드/배포 파이프라인이 세분화할 필요가 있었습니다. 백엔드 서버 간의 통신으로 인해 두 서버의 브랜치를 완전히 분리해서 개발하는 데에는 제한이 있었기 때문에 **하나의 백엔드 develop 브랜치에서 각 서버 브랜치로부터 merge 이벤트의 source branch로 파이프라인을 조건부 실행하는 방법을 탐색**했고 **Generic Webhook Trigger**을 통한 방법을 찾을 수 있었습니다.

 **Generic Webhook Trigger**는 **webhook 요청 내 세부 데이터(이벤트 유형, 소스 브랜치, 타겟 브랜치 등)을 직접 활용**할 수 있기 때문에 **각 webhook 이벤트에 대해 파이프라인을 더욱 세밀한 제어권을 행사**할 수 있었고, **특정 소스/타겟 브랜치에 대한 Merge Request 이벤트(그중에서도 merged 이벤트)에 대해서만 파이프라인이 트리거되도록 설정**하여 두 서버의 빌드/배포 과정을 분리할 수 있었습니다.

또한 두 서버를 동시에 빌드할 경우 **parallel directive를 사용**하여 두 빌드 프로세스를 병렬적으로 처리하였고, 이전과 비교했을 때 **빌드 시간을 30% 정도 단축**시킬 수 있었습니다.

### ✨ 4. Elasticsearch

<div align="start">
<img src="./assets/elasticsearch.png" alt="elasticsearch" width="50%" height="70%">
</div>

**데이터의 양이 많아질수록** 기존에 사용하던 데이터베이스에서 **키워드 기반 검색 성능이 떨어지는 문제**가 있었습니다. 저희 프로젝트 서비스에서는 크롤링 한 비정형 데이터를 처리해야 했기 때문에 이 과정에서 성능 개선이 필요했고 이에 **역색인(inverted index) 방식을 사용하는 Elasticsearch를 선택**하게 되었습니다.

Elasticsearch는 기본적으로 검색 엔진이기 때문에 프로젝트에서 사용할 키워드(기술 스택) 기반 검색에 적절했습니다. 또한 크롤링 한 데이터는 “스프링 부트와 …”, “SpringBoot”, “Spring boot를…” 등 **동의어와 조사 처리가 추가적으로 필요**했기 때문에 index 생성 전 filter와 analyzer 설정을 해주었습니다.

해당 설정을 바탕으로 정형 데이터를 만든 후 MongoDB에 저장하여 **키워드 기반 검색**을 제외한 **채용공고, 회사** 관련 모든 데이터 처리를 하도록 했습니다.

<br />

# 기획 및 설계 산출물 📁

### 🏛 서비스 아키택처

<div align="start">
<img src="./assets/architecture.png" alt="architecture" width="70%" height="70%">
</div>

### 🛢︎ ERD

<div align="start">
<img src="./assets/erd.png" alt="erd" width="70%" height="70%">
</div>

### [📄 API 명세서](https://www.notion.so/API-93984df81dd345d0bca02f77572e75e8)

<div align="start">
<img src="./assets/api명세서.png" alt="api명세서" width="70%" height="70%">
</div>

### [🎨 화면 설계서](https://www.figma.com/file/AlcgDU0rh95PABatRzry87/%F0%9F%93%B0?type=design&node-id=0%3A1&mode=design&t=TrHIwL1fd7BjdxJT-1)

<div align="start">
<img src="./assets/화면설계서.PNG" alt="화면설계서" width="70%" height="70%">
</div>

### 🎥 시연 시나리오

#### 로그인 및 회원가입

<div style="display: flex;">
<img src="./assets/로그인.png" alt="로그인" width="50%" height="70%" style="margin-right: 0.5rem">
<img src="./assets/회원가입.png" alt="회원가입" width="50%" height="70%">
</div>

#### 채용공고

<div align="start">
<img src="./assets/채용공고.png" alt="채용공고" width="70%" height="70%">
</div>

#### 추천공고

<div align="start">
<img src="./assets/추천공고.png" alt="추천공고" width="70%" height="70%">
</div>

#### 미니게임

<div align="start">
<img src="./assets/미니게임_intro.png" alt="미니게임_intro" width="70%" height="70%">
</div>

##### speedQuiz

<div style="display: flex;">
<img src="./assets/미니게임_speed.png" alt="미니게임_speed" width="50%" height="70%" style="margin-right: 0.5rem">
<img src="./assets/미니게임_speed_Result.png" alt="미니게임_speed_Result" width="50%" height="70%">
</div>

##### OXQuiz

<div style="display: flex;">
<img src="./assets/미니게임_ox.png" alt="미니게임_ox" width="50%" height="70%" style="margin-right: 0.5rem">
<img src="./assets/미니게임_ox_result.png" alt="미니게임_ox_Result" width="50%" height="70%">
</div>

#### 면접대비

<div style="display: flex;">
<img src="./assets/면접대비_intro.png" alt="면접대비_intro" width="50%" height="70%" style="margin-right: 0.5rem">
<img src="./assets/면접대비.png" alt="면접대비" width="50%" height="70%">
</div>

#### 마이페이지

##### 내가 찜한 채용 공고

<div align="start">
<img src="./assets/마이페이지_myFavoriteRecruit.png" alt="마이페이지_myFavoriteRecruit" width="70%" height="70%">
</div>

##### 나의 뱃지

<div align="start">
<img src="./assets/마이페이지_myBadge.png" alt="마이페이지_myBadge" width="70%" height="70%">
</div>

##### 과거 답변 내역

<div align="start">
<img src="./assets/마이페이지_myPastAns.png" alt="마이페이지_myPastAns" width="70%" height="70%">
</div>

##### 자소서 관리

<div align="start">
<img src="./assets/마이페이지_myAssay.png" alt="마이페이지_myAssay" width="70%" height="70%">
</div>

##### 개인 정보 수정

<div align="start">
<img src="./assets/마이페이지_updateInfo.png" alt="마이페이지_updateInfo" width="70%" height="70%">
</div>

<br />

# ✨ Conventions

## 📌코드 컨벤션

[🖱 Frontend Conventions](https://www.notion.so/Front-End-59a0c5e4788245a985c65521dc8dff8a)

[🖱 Backend Conventions](https://www.notion.so/Back_End-2e25f8e764d84d929aaf5dfc0a23ba27)

## 📌커밋 컨벤션

```
✨Feat : 새로운 기능 추가
🐛Fix : 버그 수정
📝Docs : 문서 수정
🗃️Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
♻️Refactor : 코드 리펙토링
✅Test : 테스트 코드, 리펙토링 테스트 코드 추가
💡Comment : 필요한 주석 추가 했을 경우
🎨Design : css나 디자인 변경, 이미지 추가 등
🚑Hotfix : 치명적인 버그 수정
👷Build : 배포 관련
🤝🏻Merge : f-기능명 into dev-frontend
```

<br />

# 팀원 소개 및 개발 회고 🐥

## 📆 프로젝트 기간

### 24.02.19 ~ 24.04.04

- 기획 및 설계 : 24.02.19 ~ 24.02.28
- 프로젝트 구현 : 24.02.28 ~ 24.04.01
- 버그 수정 및 산출물 정리 : 24.04.01 ~ 24.04.04
- 코드 리팩토링 : 24.04.04 ~

<br />

## 💞 팀원 소개

<table>
    <tr>
        <td height="140px" align="center"> <a href="https://github.com/hyunsoo10">
            <img src="https://avatars.githubusercontent.com/hyunsoo10" width="140px" /> <br><br> 👑 조현수 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/Song-YoonJae">
            <img src="https://avatars.githubusercontent.com/Song-YoonJae" width="140px" /> <br><br> 👶🏻 송윤재 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/HyeongtaekOh">
            <img src="https://avatars.githubusercontent.com/HyeongtaekOh" width="140px" /> <br><br> 👶🏻 오형택 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/yhc-key">
            <img src="https://avatars.githubusercontent.com/yhc-key" width="140px" />
            <br><br> 조용환 </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/hyeounguk2">
            <img src="https://avatars.githubusercontent.com/hyeounguk2" width="140px" /> <br><br> 👶🏻 전형욱 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/se2develop">
            <img src="https://avatars.githubusercontent.com/se2develop" width="140px" /> <br><br> 👶🏻 노세희 <br>(Front-End) </a> <br></td>
    </tr>
    <tr>
        <td align="center"><br/></td>
        <td align="center"><br/></td>
        <td align="center"><br/></td>
        <td align="center"><br/></td>
        <td align="center"><br/></td>
        <td align="center"><br/></td>
    </tr>
</table>

## 🙌🏻 회고

### [🔥 개발일지 작성](https://cheddar-cloudberry-278.notion.site/e9336f18272941c1989ac898df03c1b1?v=20841ac542dc4b1aa85c028377088822&pvs=74)
<div align="start">
<img src="./assets/개발일지.png" alt="개발일지" width="60%" height="70%">
</div>

##### **조현수**<br>
- MySQL을 Master와 Slave로 나누어서 분산 처리를 해보니 항상 익숙하게 사용했던 MySQL이 새롭게 느껴진 경험이었습니다. 하지만 로컬 서버에서 replication이 끊기는 문제에 대한 대처가 미흡해서 다음번에 더 고민해야 할 부분이라고 생각합니다. 어려운 프로젝트였지만 실력 있는 팀원들과 함께여서 수월하게 완성할 수 있었다고 생각합니다.

##### **송윤재**<br>
- Selenium, Beautiful Soup 등 파이썬 라이브러리를 사용해 크롤링을 해보고 여러 종류의 데이터베이스를 사용하며 어떤 기능엔 어떤 데이터베이스가 적절할지 고민해보았던 좋은 경험이었습니다.

##### **오형택**<br>
- 처음 쓰는 언어, 처음 쓰는 기술, 처음 맡는 도메인에 참고할 소스도 많지 않아서 쉽지 않았지만 새롭게 도전하는 건 늘 즐거운 것 같습니다. 새로운 지식을 학습하고 크고작은 문제들을 해결하며 개발자로서 성장할 수 있는 시간이었습니다.

##### **조용환**<br>
- Next.js, TypeScript, Zustand에 React-query 까지 다양한 기술을 활용해 프로젝트를 완성해 만족스럽습니다.

##### **전형욱**<br>
- 이번에 Front-End 역할을 맡게되어 처음 쓰는 기술(Next.js, TypeScript, Zustand)로 개발을 진행하게 되었습니다.
생각보다 어렵기도 했지만 팀원들과 협업하여 배우는 과정이 즐거웠습니다. 정말 좋은 팀원들과 함께해서 6주 내내 가장 많이 웃었고 행복했습니다.

##### **노세희**<br>
- Next.js와 Typescript를 처음 사용하였는데 컴파일 과정에서 타입을 지정하기 대문에 디버깅이 쉽다는 것을 느낄 수 있었고, 코드를 컴포넌트화하여 재사용성을 높이기 위해 고민했던 의미있는 시간이었습니다.
