<h2>LessonReserve</h2>
운동강사가 자신이 수행할 레슨을 사이트에 올리고, 수강생이 이를 선택하여, 운동레슨을 매칭시켜주는 웹사이트입니다. 주요 내용은 다음과 같습니다. <br><br>

**● 주요기능**

1. 강사 레슨등록 및 수강생 레슨신청<br>
REST 기반으로 강사가 운동레슨를 등록하고, 수강생은 등록된 레슨을 신청하는 기능을 구현했습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/993b4f4f-4fe7-4100-9ad7-b4e68d9b71a7" height="450px"></img>

<br>
2. 마이페이지<br>
마이페이지를 통하여 강사 및 수강생의 본인의 레슨 내역을 한번에 볼 수 있도록 구현했습니다. 또한 강의 등록 및 신청 취소를 할 수 있습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/4437a3f5-74eb-4160-96b4-c6cd467ecc31" height="450px"></img>

<br><br>
3. 외부 API 활용<br>
스프링 Security Oauth2 를 이용해서 카카오 로그인을 구현하였으며, 레슨 장소를 카카오 API를 이용한 카카오지도로 표시하였습니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/0a7c5586-4ebf-4fea-b8b7-d0295d1ab89c" height="450px"></img>

<br><br>
4. 수강평 기능<br>
강의를 마친 후에는 수강생들이 강사에 대한 강의 평가를 남길 수 있습니다. 이러한 평가는 강사별 소개페이지에서 평균으로 계산되어 보여집니다.<br>
<img src="https://github.com/billups1/lessonReserve/assets/123869397/a5904c88-4dff-40ad-806f-2224b6d125fc" height="450px"></img>


<br><br>
**● 사용기술**<br>
기본적으로 SPRING BOOT를 활용하여 본 프로젝트를 제작하였습니다.<br>
JPA, 스프링 시큐리티, 카카오로그인을 위한 Oauth2, 카카오지도 구현을 위한 카카오 API, dto 유효성 검사를 위한 AOP, @Scheduled 어노테이션 기능 등의 백엔드 기술을 활용하였습니다.<br>
아울러 프론트엔드 기술도 사용했습니다. 타임리프, 자바스크립트, 부트스트랩를 이용하여 화면을 직접 구현했고, AJAX를 사용한 서버와의 비동기통신 기술도 활용하였습니다.
