
if (window.location.pathname == '/admin/gather/list') {
    getGatherList();
} else if (window.location.pathname.startsWith('/admin/gather/') && !isNaN(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)) ) {
    getGatherUserList();
    getGatherApplyList();
}

// 모임 리스트 불러오기
function getGatherList(page) {
    console.log("page", page);
    let adminGatherSearchCond = getSearchCond();

    $.ajax({
        url: `/api/admin/gather/list?page=${page}`,
        data: adminGatherSearchCond,
        dataType: "json"
    }).done(res => {
        console.log("Admin 모임 리스트 조회 성공", res);
        $('#gatherTableData').empty();
        res.data.content.forEach((dto) => {
            lessonTableData = `
                <tr onclick="location.href = '/admin/gather/${dto.id}'">
                  <td>${dto.id}</td>
                  <td>${dto.name}</td>
                  <td>${dto.leaderName}</td>
                  <td>${dto.createTime}</td>
                  <td>${dto.address}</td>
                  <td>${dto.currentUserCountState}</td>
                </tr>
            `
            $('#gatherTableData').append(lessonTableData);
        });

        pagination(res.data, 'gather');

    }).fail(error => {
        console.log("Admin 모임 리스트 조회 실패", error);
    });
}

function getSearchCond() {
    var cond1 = document.querySelector('#searchCond1 > option:checked').value;
    if (document.querySelector('#searchCond1 > option:checked').value != "none") {
        var cond2 = document.querySelector('#searchCond2 > option:checked').value;
    } else {
        var cond2 = null;
    }
    var searchText = document.getElementById('searchText').value;
    var searchDate = document.getElementById('searchDate').value;
    console.log("컨디션", cond1, "|", cond2, "|", searchText, "|", searchDate);
    let data = {
        cond1: cond1,
        cond2: cond2,
        searchText: searchText,
        searchDate: searchDate
    }
    return data;
}

function itemChange(value) {
    var price = ["이상", "이하"]
    var lessonDate = ["이후", "이전"]

    $("#searchCond2").empty();

    if (value == "none" || value == "id" || value == "name" || value == "gatherLeader" || value == "address") {
        $("#searchCond2").append("<option>none</option>");
        document.getElementById("searchCond2").style.display = "none";
        document.getElementById("searchText").style.display = "inline";
        document.getElementById("searchDate").style.display = "none";
    } else if (value == "createTime") {
        document.getElementById("searchCond2").style.display = "inline";
        for (var count = 0; count < lessonDate.length; count++) {
            $("#searchCond2").append("<option>" + lessonDate[count] + "</option>");
        }
        document.getElementById("searchText").style.display = "none";
        document.getElementById("searchDate").style.display = "inline";
    }
}



// GatherUser 리스트 불러오기
function getGatherUserList(page) {
let gatherId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/gatherUser/list/gatherId/${gatherId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 모임회원 리스트 조회 성공", res);
        $('#gatherUserTableData').empty();
        res.data.content.forEach((dto) => {
            gatherUserTableData = `
                <tr>
                  <td onclick="location.href = '/admin/user/${dto.userId}'">${dto.id}</td>
                  <td onclick="location.href = '/admin/user/${dto.userId}'">${dto.userName}</td>
                  <td onclick="location.href = '/admin/user/${dto.userId}'">${dto.position}</td>
                  <td onclick="location.href = '/admin/user/${dto.userId}'">${dto.createTime}</td>
                  <td><button class="btn btn-danger btn-sm" onclick="gatherUserWithdraw(${dto.id}, '${dto.userName}')">탈퇴</button></td>
                </tr>
            `
            $('#gatherUserTableData').append(gatherUserTableData);
        });
        pagination(res.data, 'GatherUser');
    }).fail(error => {
        console.log("Admin 모임회원 리스트 조회 실패", error);
    });
}

function getGatherApplyList(page) {
let gatherId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/gatherApply/list/gatherId/${gatherId}?page=${page}`,
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
            $('#gatherApplyTableData').append(gatherUserTableData);
        });
        pagination(res.data, 'GatherApply');
    }).fail(error => {
        console.log("Admin 모임신청 리스트 조회 실패", error);
    });
}


//레슨 삭제
$('#gatherDelete').click(function() {
    if(confirm("모임과 관련된 내용이 영구적으로 삭제됩니다. 정말 모임을 삭제하시겠습니까?")){
		$.ajax({
            type: 'delete',
            url:`/api/gather/${gatherId}`,  // 만들어야됨!
            dataType:"json"
        }).done(res=>{
            console.log("모임 삭제 성공",res);
        }).fail(error=>{
            console.log("모임 삭제 실패",error);
        });
	}
})

// 모임에서 회원 탈퇴시키기
function gatherUserWithdraw(gatherUserId, userName) {
console.log(userName);
    if(confirm(`정말 ` + userName + `회원을 모임에서 탈퇴시키겠습니까?`)){
		$.ajax({
            type: 'delete',
            url:`/api/gatherUser/withdraw/${gatherUserId}`,
            dataType:"json"
        }).done(res=>{
            console.log("gatherUser 삭제 성공",res);
        }).fail(error=>{
            console.log("gatherUser 삭제 실패",error);
        });
	}

}
