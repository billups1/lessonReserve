$('.navbar-toggler').on('click', function() {
    $('#list-group').toggle();
})

// 알림창 열기
$('#alarmCount').click(function(){
    $.ajax({
        url: '/api/alarm/list',
        dataType:"json"
    }).done(res=>{
        console.log("현재 알람 리스트 불러오기 성공",res);
        $('#modalAlarmTable').empty();
        $('#modalAlarmTable').append(`<tbody> <tr>`)
        res.data.forEach(a => {
            var alarm = `
                <tr>
                <td>${a.message}</td>
                <td>
                <div id="gatherApplyBtnContainer">`

            if(a.gatherApplyAcceptStatus == 'ACCEPT') {
                alarm += `<span class="badge text-bg-primary">승인완료</span>`
            } else if (a.gatherApplyAcceptStatus == 'REJECT') {
                alarm += `<span class="badge text-bg-warning">거부완료</span>`
            } else if (a.gatherApplyAcceptStatus == 'APPLY') {
                alarm += `<button type="button" class="btn btn-primary" onclick="gatherApplyAccept(this, ${a.gatherApplyId}, ${a.alarmId})" id="gatherApplyAcceptBtn-${a.alarmId}">승인</button>
                     <button type="button" class="btn btn-danger" onclick="gatherApplyReject(this, ${a.gatherApplyId}, ${a.alarmId})" id="gatherApplyRejectBtn-${a.alarmId}">거부</button>`
            }

                alarm += `</div>
                </td>
                </tr>
            `
            $('#modalAlarmTable').append(alarm);
        });
        $('#modalAlarmTable').append(`</tbody> </tr>`)
    }).fail(error=>{
        console.log("현재 알람 리스트 불러오기 실패",error);
    });

    $('#alarmModal').addClass("show-modal");
    
})

function gatherApplyAccept(obj, gatherApplyId, alarmId) {
    $.ajax({
        type: "put",
        url: `/api/gather/${gatherApplyId}/accept`,
        dataType:"json"
    }).done(res=>{
        console.log("모임 가입신청 승인하기 성공",res);
        $(`#gatherApplyAcceptBtn-${alarmId}`).addClass("hide");
        $(`#gatherApplyRejectBtn-${alarmId}`).addClass("hide");
        $(obj).parent().append(`
            <span class="badge text-bg-primary">승인완료</span>
        `)
    }).fail(error=>{
        console.log("모임 가입신청 승인하기 실패",error);
    });
}

function gatherApplyReject(obj, gatherApplyId, alarmId) {
    $.ajax({
        type: "put",
        url: `/api/gather/${gatherApplyId}/reject`,
        dataType:"json"
    }).done(res=>{
        console.log("모임 가입신청 거부하기 성공",res);
        $(`#gatherApplyAcceptBtn-${alarmId}`).addClass("hide");
        $(`#gatherApplyRejectBtn-${alarmId}`).addClass("hide");
        $(obj).parent().append(`
            <span class="badge text-bg-warning">거부완료</span>
        `)
    }).fail(error=>{
        console.log("모임 가입신청 거부하기 실패",error);
    });
}

$('#close').click(function() {
    $('.black-bg').removeClass("show-modal");
})

$(".black-bg").on("click", function (e) {
    if (e.target == document.querySelector(".black-bg")) {  // (참고) JQuery는 .black-bg 찾을때 다른 형태로 나와서 바로 사용 불가
        $(".black-bg").removeClass("show-modal");
    }
});

$.ajax({
    url: '/api/alarm/count',
    dataType:"json"
}).done(res=>{
    console.log("현재 알람 개수 불러오기 성공",res);
    var alramCount = res.data;
    var alramCountText = "알람 " + alramCount + "개";
    $('#alarmCount').html(alramCount);
}).fail(error=>{
    console.log("현재 알람 개수 불러오기 실패",error);
});


$(document).ready(function() {

$.ajax({
    url: "/api/user",
    dataType: "json"
}).done(res=>{
    $('#userId').val(res.data.userId);
    $('#userName').val(res.data.userName);
    console.log($('#userId').val());

    if ($('#userId').val()) {
        $('#navBar').append(`
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/">레슨</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/gather">마이페이지</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/gather/mypage">내 레슨(강사용)</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/student/mypage">MyPage(수강생용)</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/teacher/mypage">MyPage(강사용)</a>
            </li>
        `)

        $('#logoutContainer').append(`
            <a class="nav-link" href="/logout">로그아웃</a>
        `)

    } else {
        $('#navBar').append(`
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/">레슨</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/gather">모임</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/student/join">회원가입(수강생용)</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/teacher/join">회원가입(강사용)</a>
            </li>
        `)
    }
}).fail(error=>{
    console.log("유저 정보 불러오기 실패", error);
});
})