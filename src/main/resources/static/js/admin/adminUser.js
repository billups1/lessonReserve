
if (window.location.pathname == '/admin/user/list') {
    getUserList();
} else if (window.location.pathname.startsWith('/admin/user/') && !isNaN(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)) ) {
    // 수강생
    getApplyList();
    getLessonReviewListByStudentId();

    // 강사
    getLessonList();
    getLessonReviewListByTeacherId();

    // 수강생, 강사
    getGatherList();
}

// 모임 리스트 불러오기
function getUserList(page) {
    let searchCond = getSearchCond();

    $.ajax({
        url: `/api/admin/user/list?page=${page}`,
        data: searchCond,
        dataType: "json"
    }).done(res => {
        console.log("Admin 유저 리스트 조회 성공", res);
        $('#userTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr onclick="location.href = '/admin/user/${dto.id}'">
                  <td>${dto.id}</td>
                  <td>${dto.name}</td>
                  <td>${dto.role}</td>
                  <td>${dto.email}</td>
                  <td>${dto.phone}</td>
                  <td>${dto.createTime}</td>
                </tr>
            `
            $('#userTableData').append(tableData);
        });

        pagination(res.data, 'user');

    }).fail(error => {
        console.log("Admin 유저 리스트 조회 실패", error);
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

    if (value == "none" || value == "id" || value == "name" || value == "role" || value == "email" || value == "phone") {
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



// 레슨신청 리스트(By studentId) 불러오기
function getApplyList(page) {
    let studentId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/Apply/list/studentId/${studentId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨신청 리스트(By studentId) 조회 성공", res);
        $('#applyTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr id="apply-${dto.applyId}" onclick="location.href = '/admin/apply/${dto.applyId}'">
                  <td>${dto.applyId}</td>
                  <td>${dto.paymentId}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.price}</td>
                  <td>${dto.paymentCreateTime}</td>
                  <td>${dto.cancelTime}</td>
                </tr>
            `
            $('#applyTableData').append(tableData);
        });
        pagination(res.data, 'apply');
    }).fail(error => {
        console.log("Admin 레슨신청 리스트(By studentId) 조회 실패", error);
    });
}

// 레슨리뷰 리스트(By studentId) 불러오기
function getLessonReviewListByStudentId(page) {
    let studentId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/lessonReview/list/studentId/${studentId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨리뷰 리스트(By studentId) 조회 성공", res);
        $('#lessonReviewTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr id="lessonReview-${dto.id}">
                  <td>${dto.id}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.score}</td>
                  <td>${dto.content}</td>
                  <td>${dto.createTime}</td>
                  <td><button class="btn btn-danger btn-sm" onclick="lessonReviewDelete(${dto.id})">삭제</button></td>
                </tr>
            `
            $('#lessonReviewTableData').append(tableData);
        });
        pagination(res.data, 'lessonReview');
    }).fail(error => {
        console.log("Admin 레슨리뷰 리스트(By studentId) 조회 실패", error);
    });
}

function lessonReviewDelete(lessonReviewId) {
    if(confirm(`정말 레슨리뷰를 삭제하시겠습니까?`)){
		$.ajax({
            type: 'delete',
            url:`/api/lessonReview/delete/${lessonReviewId}`,
            dataType:"json"
        }).done(res=>{
            console.log("레슨리뷰 삭제 성공",res);
            $(`#lessonReview-${lessonReviewId}`).remove();
        }).fail(error=>{
            console.log("레슨리뷰 삭제 실패",error);
        });
	}
}

// 레슨 리스트 불러오기(강사)
function getLessonList(page) {
    let teacherId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/lesson/list/teacherId/${teacherId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨 리스트(By teacherId) 조회 성공", res);
        $('#lessonTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr id="apply-${dto.applyId}" onclick="location.href = '/admin/lesson/${dto.id}'">
                  <td>${dto.id}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.lessonStartDate}~<br>${dto.lessonEndDate}</td>
                  <td>${dto.price}</td>
                  <td>${dto.lessonCreateTime}</td>
                </tr>
            `
            $('#lessonTableData').append(tableData);
        });
        pagination(res.data, 'lesson');
    }).fail(error => {
        console.log("Admin 레슨 리스트(By teacherId) 조회 실패", error);
    });
}

// 레슨리뷰 리스트(By teacherId) 불러오기
function getLessonReviewListByTeacherId(page) {
    let teacherId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/lessonReview/list/teacherId/${teacherId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨리뷰 리스트(By teacherId) 조회 성공", res);
        $('#lessonReviewTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr id="lessonReview-${dto.id}">
                  <td>${dto.id}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.score}</td>
                  <td>${dto.content}</td>
                  <td>${dto.createTime}</td>
                  <td><button class="btn btn-danger btn-sm" onclick="lessonReviewDelete(${dto.id})">삭제</button></td>
                </tr>
            `
            $('#lessonReviewTableData').append(tableData);
        });
        pagination(res.data, 'lessonReview');
    }).fail(error => {
        console.log("Admin 레슨리뷰 리스트(By teacherId) 조회 실패", error);
    });
}


// 모임 리스트 불러오기
function getGatherList(page) {
    let userId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
    $.ajax({
        url: `/api/admin/gather/list/userId/${userId}?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("Admin 모임 리스트 조회 성공", res);
        $('#gatherTableData').empty();
        res.data.content.forEach((dto) => {
            tableData = `
                <tr id="lessonReview-${dto.id}" onclick="location.href = '/admin/gather/${dto.id}'">
                  <td>${dto.id}</td>
                  <td>${dto.name}</td>
                  <td>${dto.address}</td>
                  <td>${dto.userRole}</td> // 모임내 역할
                </tr>
            `
            $('#gatherTableData').append(tableData);
        });
        pagination(res.data, 'gather');
    }).fail(error => {
        console.log("Admin 모임 리스트 조회 실패", error);
    });
}