
### <빅데이터를 활용한 촬영공간 중계 020 서비스 플랫폼>

## 목차
>### 1. 프로젝트 착수
>- 주제
>- 기획의도
>- 개발환경 및 Spec
>- Software Architecture
>- 개발일정
>- 개발팀 소개
> ### 2. 설계 및 분석
>- 요구사항 정의서(SRS)
>- UseCaseDiagram
>- ER Diagram
>- Class Entity  Association
>- Service Flow
> ### 3. 구현
>- Software Architecture
>- 시연 영상
>- 구현 및 코드

## 1. 프로젝트 착수

### (1) 주제
- 머신러닝을 활용한 촬영공간 중계 O2O 서비스 플랫폼 개발
- 서비스명 : Pics (Picture + Square)
	사진의 틀, 사진의 액자
![](https://images.velog.io/images/wpdud94/post/7d933196-d234-42b8-9c54-f9f1fd305e17/0_0_main.png)
 
### (2) 기획의도
- **콘텐츠 산업의 변화로 인한, 촬영공간에 대한 수요 증가**
>- 유튜브, 아프리카TV 등 쌍뱡항 미디어 콘텐츠 플랫폼의 등장으로 개인이 제작한 콘텐츠를 통해 수익 창출이 가능해짐
>- 개인이 컨텐츠를 생산하고 소비하는 "프로슈머" 개념의 등장
![](https://images.velog.io/images/wpdud94/post/55cc2ab4-dfe3-42e8-a8a2-ca4a676bc2f6/1_2_%ED%94%84%EB%A1%9C%EC%8A%88%EB%A8%B8%20%EB%93%B1%EC%9E%A5.png)
>- 개인과 기업 모두 온라인 콘텐츠를 대량으로 생산
>- 콘텐츠 산업의 매우 빠른 성장 야기
![](https://images.velog.io/images/wpdud94/post/6ac92c95-fb58-4fff-837e-4dbf0cdec73a/1_2_%EC%BD%98%ED%85%90%EC%82%B0%20%EC%82%B0%EC%97%85%EC%9D%98%20%EC%84%B1%EC%9E%A5.png)

  
 - **콘텐츠산업 성장 속도에 미치지 못하는 촬영공간 인프라**
>- 콘텐츠 제작에 있어, 촬영공간은 필수적
>- 촬영공간 탐색 및 예약하는 인프라는 매우 열악한 
>- 분산된 촬영공간 사이트로 인해, 촬영장소 탐색과 선택에 있어 복잡한 과정을 거쳐야 함
>- 촬영장소 탐색 및 선정의 알고리즘은 아래와 같음
    ![](https://images.velog.io/images/wpdud94/post/173d79cb-4e38-4c45-88a1-969b855a55aa/1_2_%ED%83%90%EC%83%89%20%EC%84%A0%EC%A0%95%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98.png)
    
- **더 쉽고 편한 촬영공간 검색 및 예약 알고리즘 제공**
>- 조건 검색, 바로 예약, 자동 추천 등의 기능으로 앞서 언급한 인프라 한계 극복
>- 고객은 원하는 장소를 더 쉽게 찾고 업체는 더 많은 고객을 모을 수 있는 플랫폼 구축
    ![](https://images.velog.io/images/wpdud94/post/a4699446-ab55-4099-b808-46a9559a9232/1_2_%EB%8D%94%20%EC%89%BD%EA%B3%A0%20%ED%8E%B8%ED%95%9C%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98.png)
    
### (3) 개발환경 및 Spec
![](https://images.velog.io/images/wpdud94/post/6b54caf3-8f8d-46ac-b6bc-e5b5e313bb9f/1_3_%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD%EB%B0%8F%EC%8A%A4%ED%8C%A9.png)

### (4) Software Architecture (Plannned)
![](https://images.velog.io/images/wpdud94/post/50c6a9f5-4df7-4258-b533-390a85a5ee1f/1_4_software%20architecuture.png)

### (5) 개발 일정
- 개발 기간 : 2020.08.24(월) ~ 2020.09.18(금) (총 4주) 
![](https://images.velog.io/images/wpdud94/post/00f2276c-0f84-47ef-92cf-cf887364a41e/1_5_development%20schedule.png)

<br>
### (6) 개발팀 소개
![](https://images.velog.io/images/wpdud94/post/a66322de-67ca-4116-bf1f-a193c2639e1e/1_6_members.png)

-------------------
-----------------

## 2. 설계 및 분석
### (1) 요구사항 정의서 (SRS)

<a href="https://docs.google.com/spreadsheets/d/1CxmQgOvFC54igRmkpnXn2LXLlSx0lkSVa7FuXIMufws/edit?usp=sharing">
요구사항 정의서 링크
</a>

### (2) UseCaseDiagram

![](https://images.velog.io/images/wpdud94/post/5dafb19e-9b02-4184-acab-33539b2c2206/2_2_UCD.png)

### (3) ER Diagram
![](https://images.velog.io/images/wpdud94/post/bde4d5da-985c-4923-ab76-5e0447bd42fa/2_3_ERD.png)

### (4) Class Entity Association
![](https://images.velog.io/images/wpdud94/post/31707a4f-bd58-4a39-a156-4c9a62d80625/2_4_CEA.png)

### (5) 촬영공간 Service Flow
1) 스튜디오 검색(F)
![](https://images.velog.io/images/wpdud94/post/596678a0-d676-4381-901b-223cec751e79/2_4_serviceflow1.png)

2) 스튜디오 예약(S002)
![](https://images.velog.io/images/wpdud94/post/490b3364-aaf1-4c12-8040-94f729dbd3ae/2_4_serviceflow2.png)

-------------------
-------------------

## 3. 구현
### (1) Software Architecture (Completed)
#### A. Restful - SpringBoot
- 전체
![](https://images.velog.io/images/wpdud94/post/45e11aab-5256-4746-a543-ea77e65454f6/3_1_softwareAchritecture1.png)
- 상세
![](https://images.velog.io/images/wpdud94/post/b0c6f57c-9941-438c-9cba-0995a79ad5ad/3_1_softwareAchritecture2png.png)
- 폴더구조
![](https://images.velog.io/images/wpdud94/post/0c2be9a1-6f97-40af-a759-22dcaf9d31f6/3_1_folderstructure1.png)
![](https://images.velog.io/images/wpdud94/post/2581e275-eaf0-41bd-aacd-e21934daa7fd/3_1_folderstructure2.png)
![](https://images.velog.io/images/wpdud94/post/a6b33b30-e760-4e6a-b782-c61bd68f7f15/3_1_folderstructure3.png)

#### B. Restful - Flask
- 전체
![](https://images.velog.io/images/wpdud94/post/9fc7f939-2231-43ea-bf88-9419ffe07d07/3_1_softwareAchritecture_flask1.png)
- 상세
![](https://images.velog.io/images/wpdud94/post/40bfaec4-da9d-4883-be1d-8b5c0333e9a7/3_1_softwareAchritecture_flask2.png)
- 폴더구조
![](https://images.velog.io/images/wpdud94/post/26251aef-8076-4bc3-a776-da874c7ed4d8/3_2_softwareAchritecture_folder1.png)
![](https://images.velog.io/images/wpdud94/post/72355c12-b520-4084-992a-09a57730db6e/3_2_softwareAchritecture_folder2.png)  

### (2) 시연 영상  
[![Video Label](https://img.youtube.com/vi/Oz6t3wccgOo/maxresdefault.jpg)](https://youtu.be/Oz6t3wccgOo?t=0s)  

### (3) 구현 및 코드  
<a href="https://drive.google.com/file/d/15XsCKff8jFVu8iNK09mMp_M-iUgW52tR/view?usp=sharing">
구현부분 링크이동
</a>

---------
--------
### 감사합니다

