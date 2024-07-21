

<!--

# Dopamines
<h1 align="center">DB 구현 👍</h1>
> [플레이 데이터] 한화시스템 BEYOND SW캠프 / BOOT_UP

-->
<div align="center">
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/125132754/63bb8c42-2a83-42cb-b037-6d42a7d87404"  width="1000px" align="center"/>
</div>


<!--
==============Todo==============

🎬[CI/CD 시연영상](https://www.youtube.com/watch?v=dhMrKTwNI8U&lc=UgzCJR3WxkvsckRyyO94AaABAg&ab_channel=%EB%94%B0%EB%9D%BC%ED%95%98%EB%A9%B4%EC%84%9C%EB%B0%B0%EC%9A%B0%EB%8A%94IT)  
📃[프로젝트 회고록](블로그주소)

-->

## 📌 프로젝트 주제

> BOOT_UP 시스템은 한화 시스템 BEYOND SW캠프를 수강하는 학생들만을 위한 커뮤니티 기능을 주 목적으로 삼는다.
부가적으로 공지사항, 출결 알림, 스터디룸 예약 등의 기능을 통해 과정 간 편의성을 증진시켜준다.
이를 통해 BOOT_UP 시스템은 실속있는 부트캠프 관리 서비스를 제공한다.
<br>
<br>

## 🖼 배경

![Group 1410086963 (1)](https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/138289674/60eb3482-7b86-4de0-9247-98cd5eb6e070)



자체적인 플랫폼이 부재한 한화시스템 BEYOND SW캠프는 소통의 창구로 디스코드를 사용하고 있다. 위 플랫폼을 통해 공지사항이 전달되고  스터디 모집과 소통이 이루어지고 있다.
현 플랫폼인 디스코드는 오로지 “해당 기수"만의 소통창구로 이용되고 있다. 이는 전 기수간 혹은 취업준비를 하는 수료자들 간 소통이  불가하다는 점에서 불편함을 야기시킨다. 이에, 원내에서 원활히 사용할 수 있는 소통 및 커뮤니티 플랫폼을 구축하여 BEYOND SW캠프 기수 전체적으로 활동할 수 있는 창구를 마련하고자 한다.
<br>
<br>
## 📋 요구사항 정의서 / ERD

<summary> <b>요구사항 정의서</b> </summary>
</br>
<p>
  <a href= "https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EC%A0%95%EC%9D%98%EC%84%9C">🔗 요구사항 정의서</a>
</p>

<summary> <b>ERD</b> </summary>
  
![be06-ERD 최종](https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/4c4f33c2-4043-4121-8dcb-3e0eaba1e2d3)



<br>

## 📚 기술 스택
### OS <img src="https://img.shields.io/badge/Linux-FCC624?style=flat&logo=linux&logoColor=black"/> <img src="https://img.shields.io/badge/Vmware-607078?style=flat&logo=Vmware&logoColor=white"/>  <img src="https://img.shields.io/badge/CentOS-262577?style=flat&logo=CentOS&logoColor=white"/> 
### Database <img src="https://img.shields.io/badge/mariaDB-003545?style=flat&logo=mariaDB&logoColor=white"/> <img src="https://img.shields.io/badge/HAPROXY-blue?style=flat&logo=googlepubsub&logoColor=white"/> <img src="https://img.shields.io/badge/Keepalived-FF3E00?style=flat&logo=amazondynamodb&logoColor=white"/>


<br>

## 🖥️ 시스템 아키텍처

<div align="center">
  <img width="500px" alt="스크린샷 2024-05-21 오후 2 19 50" src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/c50febe8-a54b-4e62-8d0a-327926be524e">
</div>


<br>
<br>

## ✨ DR(재난 복구)

### 재난 복구 계획

본 DRP는 데이터베이스의 고가용성과 무중단 서비스를 제공하기 위한 재난 복구 절차를 설명한다.
<br>
<br>

#### 시스템 구성

- `Master Slave`:
커뮤니티 프로젝트 특성 상 게시글을 조회하는 select작업이 글을 작성하는 insert작업보다 주요적으로 요구된다. 이러한 점을 감안하였을 때, 읽기 작업을 slave 서버에 위임하는 것이 효율성을 증대시킬 수 있다고 판단하여 Master - Slave 구성을 채택하였다.

- `HAProxy`: 
슬레이브가 추가 될 시, 로드 밸런서로서 HAProxy를 활용한다. 쓰기작업은 Master에게, 읽기 작업을 각 Slave에게 분배하여 부하를 분산시킨다.
- `Keepalived`: 
다중으로 Master 서버를 구성시켰을 때, 유휴 상태로 인한 종료를 방지하기 위해 사용될 수 있다.
<br>
<br>

#### 재난 복구 시나리오

- `장애 탐지` : HAProxy가 주기적으로 노드 상태를 체크하여 장애를 감지.
- `장애 노드 격리`: HAProxy는 자동으로 장애 노드를 로드 밸런싱 풀에서 제외.
- `알림`: 시스템 관리자에게 장애 발생 사실을 이메일/문자로 알림.
<!--
==============Todo==============
- CI : 어떤 과정을 통해 자동으로 테스트 후 결과에 따라 통합 된다는 내용 추가
- CD : 어떤 과정을 통해자동으로 운영중인 서버에 무중단 배포 된다는 내용 추가
-->
<br>
<br>


## 👨‍💻 SQL 실행 결과
<!--
==============Todo==============
-->

<details>
<summary>회원 가입</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/9622e6e1-6bf0-4a37-846f-ee75f3cc4338"/>
    <p>회원가입을 할 때 이메일, 비밀번호 , 이름, 닉네임, 핸드폰번호, 주소, 기수정보를 기입해야 한다.(없으면 없음 선택이 가능하다.)<br> 권한여부, 생성날짜, 소셜 로그인 여부의 정보들을 저장할 수 있다.
    </p>
 </figure>
</div>
</details>

<details>
<summary>중고 마켓 게시판</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/62980463-4f70-4276-b3c5-d74e05804bf6"/>
    <p> - 제목,내용,이미지,상품 가격이 필수적으로 들어가야 한다. <br>
    - 판매자가 원하는 상품 정보들을 등록할 수 있다.<br>
    - 한번 올리면 수정은 불가하다.
    - 판매자와 1:1 채팅을 할 수 있다.</p>
 </figure>
</div>
</details>

<details>
<summary>채팅방 생성</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/d2ffdd03-fe08-4861-a62c-7f86c057d940"/>
    <p> - 1:1 채팅방이 생성되었을때, 요청 user 와 수신 user 의 정보 저장한다. <br>
      - 최초 채팅방 생성 날짜와 최종 대화 날짜가 나온다.<br>
      - 최종 대화 기준 30일이 지나면 비활성화 상태가 된다.</p>
 </figure>
</div>
</details>

<details>
<summary>채팅방 메세지</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/eb323095-ee43-4948-97b4-a12bce241170"/>
    <p>채팅방 안의 대화 내역들을 볼 수 있다.<br>
    대화를 보낸 사람, 보낸 시간, 텍스트 파일 외의 사진,영상 등 파일 형식들을 저장 할 수 있다.</p>
 </figure>
</div>
</details>

<details>
<summary>스터디 예약 좌석 목록</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/4020f3d0-fc44-4e67-bbc5-735c8c3f643f"/>
    <p>3~5층 각 층별 좌석 목록들이 저장된다.</p>
 </figure>
</div>
</details>

<details>
<summary>스터디 예약 정보</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/debba837-6b59-4387-aab2-1f37a34e980a"/>
    <p>스터디 예약 목록들이 저장된다.<br>
    신청 회원, 신청 명수, 원하는 스터디 예약 시간 정보가 저장된다.</p>
 </figure>
</div>
</details>

<details>
<summary>실시간 스터디 예약 정보</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/751dd9d7-376d-40e8-948d-4a9ce56e6951"/>
    <p>실시간으로 예약되어 있는 스터디 좌석 목록이 저장된다.</p>
 </figure>
</div>
</details>

<details>
<summary>예비 수강생 게시판</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/151d2aef-4ee1-4a7d-a71c-6df6eabcf1c4"/>
    <p>회원가입을 했지만, 한화 부트캠프 수강생이 아닌 회원들에 대해서는 해당 예비 수강생 게시판만 이용 가능하다. <br>
    예비 수강생이 부트캠프를 다니고 있는 수강생들에게 여러 Q&A를 주고 받을 수 있다.</p>
 </figure>
</div>
</details>

<details>
<summary>게시글 좋아요</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/580c7f28-85fa-40b7-bea8-cd80ab47b2d6"/>
    <p>유저가 어떤 게시글에 좋아요를 눌렀는지 확인할 수 있다.</p>
 </figure>
</div>
</details>

<details>
<summary>댓글 작성, 조회</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/cb714207-8415-4c02-a374-661cb4721cd7"/>
    <p>게시글에 대한 댓글을 저장한다.</p>
 </figure>
</div>
</details>

<details>
<summary>댓글 좋아요</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/bde06873-8205-46c3-b644-b3d715a4795f"/>
    <p>댓글에 대해서 좋아요 정보를 저장한다.</p>
 </figure>
</div>
</details>

<details>
<summary>대댓글 작성, 조회</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/5c3e6ed2-477c-4b88-bba8-a067474537f6"/>
    <p>대댓글에 대한 정보를 저장한다.<br>
    단, 하나의 댓글 안에 대댓글의 깊이(depth)는 동일하다.</p>
 </figure>
</div>
</details>

<details>
<summary>대댓글 좋아요</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/6b1827b0-83b6-4d29-8c96-a96302e3510c"/>
    <p>대댓글에 대한 좋아요 정보를 저장한다.</p>
 </figure>
</div>
</details>

<details>
<summary>최종 프로젝트 게시판</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/1e0bba74-9302-402d-a577-81a95c5ee12d"/>
    <p>최종 프로젝트를 끝내고 수료한 수강생들 프로젝트 정보를 저장한다.<br>
    시연 영상을 볼 수 있으며, 해당 프로젝트의 깃허브 링크가 추가적으로 기재된다.</p>
 </figure>
</div>
</details>

<details>
<summary>관리자 정보</summary>
<div>
<figure align="center"> 
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/ed18c468-8b32-41d8-9347-6357f3f43865"/>
    <p>관리자는 테이블이 따로 명시되어 저장된다.</p>
 </figure>
</div>
</details>

<br>

## 🔍 SQL 튜닝

<details>
<summary>월 별 게시글 조회</summary>
<div>
<figure align="center"> 
  <br><br>
  
  <b> 1. 인덱스 생성 전 → 전체 데이터(약 7만 개)에서 데이터 조회
    
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/90b35b18-37d0-4deb-9f5f-dd4ad1c61815"/>  
  
  <br><br>

  <b> 2. 인덱스 생성 후 → 약 700개에서 데이터 조회  

   <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/08c497c2-f181-4924-9506-b81357ee2afe"/>   
 </figure>
</div>
</details>

<details>
<summary>중고마켓 제목 키워드 검색</summary>
<div>
<figure align="center"> 
  <br><br>
  
  <b> 1. 인덱스 생성 전 → 전체 데이터에서 키워드 검색 조회 (0.047 sec)
    
  <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/390095c8-f86f-4a84-bf50-c65bb23b2f19"/>  
  
  <br><br>

  <b> 2. 인덱스 생성 후 → 풀텍스트 인덱스에서 키워드 검색 조회 (0.015 sec)

   <img src="https://github.com/beyond-sw-camp/be06-1st-Dopamines-BOOT_UP/assets/104816530/ad34d9a4-5c6d-411d-8d78-ba517314890e"/>   
 </figure>
</div>
</details>


## 🤼‍♂️팀원

### Members
<table>
  <tr>
    <td>
      <a href="https://github.com/syy0O">
        <img src="https://avatars.githubusercontent.com/u/86238720?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/SihyunSeo">
        <img src="https://avatars.githubusercontent.com/u/63051137?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/xeunnie">
        <img src="https://avatars.githubusercontent.com/u/138289674?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/subi930">
        <img src="https://avatars.githubusercontent.com/u/125132754?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/706com">
        <img src="https://avatars.githubusercontent.com/u/104816530?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/syy0O">유송연</a>
    </td>
    <td align="center">
      <a href="https://github.com/SihyunSeo">서시현</a>
    </td>
    <td align="center">
      <a href="https://github.com/xeunnie">최승은</a>
    </td>
    <td align="center">
      <a href="https://github.com/subi930">최수빈</a>
    </td>
    <td align="center">
      <a href="https://github.com/706com">곽동현</a>
    </td>
  </tr>
</table>
<br>

<!--

Team Leader : 🐯**유철수**

Backend : 🐶 **김철수**

Backend : 🐺 **박철수**

Frontend : 🐱 **이철수**

인공지능 : 🦁 **최철수**

-->
<br>

## 🔍 API 명세서(Swagger-ui)

<br>

## 🔍 시퀀스 다이어그램

<br>

## 🔍 기능 테스트

<br>

## 🔍 코딩 컨벤션

<br>

## 🔍 시스템 아키텍처

<br>

## 🔍 소프트웨어 아키텍
