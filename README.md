<h2>LessonReserve</h2>
운동강사가 자신이 수행할 레슨을 사이트에 올리고, 수강생이 이를 선택하여, 운동레슨을 매칭시켜주는 웹사이트입니다. 주요 내용은 다음과 같습니다. <br><br>

**● 주요기능**

1. 강사 레슨등록 및 수강생 레슨신청<br>
REST 기반으로 강사가 운동레슨를 등록하고, 수강생은 등록된 레슨을 신청하는 기능을 구현했습니다.<br>
레슨의 신청하기 버튼을 누르면 신청 페이지에서 레슨을 신청할 수 있도록 하였고, 신청완료된 레슨은 “신청완료” 표시가 나오도록 하였습니다.<br>
레슨 검색 기능을 구현하였으며, QueryDSL을 통한 동적 쿼리 구현 기술을 사용하였습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/bcd8419c-e51c-45af-9c46-22a98a407788" height="270px"></img>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/7368a318-2526-440c-a4eb-82789588068f" height="550px"></img>
<br>
2. 마이페이지<br>
마이페이지를 통하여 강사 및 수강생의 본인 레슨 내역을 한번에 볼 수 있도록 구현했습니다. <br>
수강생의 경우 수강을 취소하거나 강의 완료 후 수강평을 작성할 수 있습니다. 그에 따른 수강생의 강의별 현재상태를 “취소완료”, “취소하기”, “수강평 쓰기”, “수강평 작성완료”로 나누었습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/fb37c93f-2909-465b-b32e-a29337f31ba6" height="300px"></img>
<br><br>
3. 외부 API 활용<br>
스프링 Security Oauth2 를 이용해서 카카오 로그인을 구현하였으며, 레슨 장소를 카카오 API를 이용한 카카오지도로 표시하였습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/3e292431-1109-4aa9-93a1-c52400bd4315" height="550px"></img>
<br><br>
4. 수강평 기능<br>
강의를 마친 후에는 수강생들이 강의에 대한 평가점수를 남길 수 있습니다. 이러한 평가 점수는 강사별 소개페이지에서 평균으로 계산되어 보여집니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/9be51b2f-53b7-4af8-ba95-592ffa8cdcb3" height="550px"></img>
<br><br>

**● 사용기술**<br>
1. 백엔드<br>

기본적으로 SPRING BOOT를 기반으로 본 프로젝트를 제작하였으며, 데이터베이스는 MySQL을 이용했습니다.<br>

JPA, 스프링 시큐리티, 카카오로그인을 위한 Oauth2, 카카오지도 구현을 위한 카카오 API, 유효성 검사를 위한 AOP, @Scheduled 어노테이션 기능, QueryDSL, GOOGLE SMPT를 이용한 메일인증 등의 백엔드 기술을 활용하였습니다.<br>

2. 프론트엔드<br>

타임리프, 자바스크립트, 부트스트랩을 이용하여 화면을 직접 구현했고, 빠르게 로드되는 홈 화면을 구현하기 위해 AJAX를 활용하였습니다.<br>
