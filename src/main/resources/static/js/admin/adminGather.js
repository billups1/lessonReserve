
if (window.location.pathname == '/admin/gather/list') {
    getGatherList();
} else if (window.location.pathname.startsWith('/admin/gather/') && !isNaN(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)) ) {
    getGatherUserList();
    getGatherApplyList();
}

// 레슨 리스트 불러오기
var gatherId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
function getGatherUserList(page) {
    $.ajax({
        url: `/api/admin/gatherUser/list/gatherId/{gatherId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 모임회원 리스트 조회 성공", res);
        $('#gatherUserTableData').empty();
        res.data.content.forEach((dto) => {
            gatherUserTableData = `
                <tr onclick="location.href = '/admin/user/${dto.userId}'">
                  <td>${dto.id}</td>
                  <td>${dto.userName}</td>
                  <td>${dto.position}</td>
                  <td>${dto.createTime}</td>
                  <td><button class="btn btn-danger" onclick="gatherUserWithdraw(${dto.id})">탈퇴</button></td>
                </tr>
            `
            $('#lessonTableData').append(lessonTableData);
        });
        pagination(res.data, 'GatherUser');
    }).fail(error => {
        console.log("Admin 모임회원 리스트 조회 실패", error);
    });
}

function getGatherApplyList(page) {
    $.ajax({
        url: `/api/admin/gatherApply/list/gatherId/{gatherId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 모임신청 리스트 조회 성공", res);
        $('#gatherApplyTableData').empty();
        res.data.content.forEach((dto) => {
            gatherUserTableData = `
                <tr>
                  <td>${dto.id}</td>
                  <td>${dto.userName}</td>
                  <td>${dto.createTime}</td>
                  <td>${dto.acceptStatus}</td>
                </tr>
            `
            $('#gatherApplyTableData').append(lessonTableData);
        });
        pagination(res.data, 'GatherApply');
    }).fail(error => {
        console.log("Admin 모임신청 리스트 조회 실패", error);
    });
}


//레슨 삭제
$('#gatherDelete').click(function() {
    $.ajax({
        type: 'delete',
        url:`/api/gather/${gatherId}`,  // 만들어야됨!
        dataType:"json"
    }).done(res=>{
        console.log("모임 삭제 성공",res);
    }).fail(error=>{
        console.log("모임 삭제 실패",error);
    });
})

function gatherUserWithdraw(gatherUserId) {


}
