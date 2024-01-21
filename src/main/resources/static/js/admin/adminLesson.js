
if (window.location.pathname == '/admin/lesson/list' || window.location.pathname == '/admin') {
    getLessonList();
} else if (window.location.pathname.startsWith('/admin/lesson/') && !isNaN(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)) ) {
    getPaymentList();
    getLessonReviewList();
}

// 레슨 리스트 불러오기

function getLessonList(page) {
    console.log("page", page);
    var adminLessonSearchCond = getSearchCond();

    $.ajax({
        url: `/api/admin/lesson/list?page=${page}`,
        data: adminLessonSearchCond,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨 리스트 조회 성공", res);
        $('#lessonTableData').empty();
        res.data.content.forEach((dto) => {
            lessonTableData = `
                <tr onclick="location.href = '/admin/lesson/${dto.id}'">
                  <td>${dto.id}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.teacherName}</td>
                  <td>${dto.lessonCreateTime}</td>
                  <td>${dto.lessonStartDate}~<br>${dto.lessonEndDate}</td>
                  <td>${dto.price}원</td>
                  <td>${dto.lessonStudentCount}</td>
                  <td>${dto.lessonProgressStatus}</td>
                </tr>
            `
            $('#lessonTableData').append(lessonTableData);
        });

        pagination(res.data, 'lesson');

    }).fail(error => {
        console.log("Admin 레슨 리스트 조회 실패", error);
    });
}

// 검색조건 불러오기
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

// 서치박스 html 조절
function itemChange(value) {
    var price = ["이상", "이하"]
    var lessonDate = ["이후", "이전"]

    $("#searchCond2").empty();

    if (value == "lessonName" || value == "teacher") {
        $("#searchCond2").append("<option>none</option>");
        document.getElementById("searchCond2").style.display = "none";
        document.getElementById("searchText").style.display = "inline";
        document.getElementById("searchDate").style.display = "none";
    } else if (value == "price") {
        document.getElementById("searchCond2").style.display = "inline";
        for (var count = 0; count < price.length; count++) {
            $("#searchCond2").append("<option>" + price[count] + "</option>");
        }
        document.getElementById("searchText").style.display = "inline";
        document.getElementById("searchDate").style.display = "none";
    } else if (value == "lessonStartDate" || value == "lessonEndDate") {
        document.getElementById("searchCond2").style.display = "inline";
        for (var count = 0; count < lessonDate.length; count++) {
            $("#searchCond2").append("<option>" + lessonDate[count] + "</option>");
        }
        document.getElementById("searchText").style.display = "none";
        document.getElementById("searchDate").style.display = "inline";
    }
}


// 수강신청/결제 내역 불러오기
function getPaymentList(page) {
var lessonId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
console.log(lessonId);
$.ajax({
    url: `/api/admin/apply/list/lessonId/${lessonId}?page=${page}`,
    dataType:"json"
}).done(res=>{
    console.log("Admin apply 리스트 조회 성공",res);
    $('#applyTableData').empty();
    console.log(res.data.content.length);
    if (res.data.content.length == 0) {
        lessonTableData = `
            <tr>
               <td colspan="6">조회된 자료가 없습니다.</td>
            </tr>
        `
        $('#applyTableData').append(lessonTableData);
    } else {
    res.data.content.forEach((dto) => {
        lessonTableData = `
            <tr onclick="location.href = '/admin/apply/${dto.applyId}'">
              <td>${dto.applyId}</td>
              <td>${dto.userName}</td>
              <td>${dto.applyStatus}</td>
              <td>${dto.price}</td>
              <td>${dto.paymentCreateTime}</td>
              <td>${dto.cancelTime}</td>
            </tr>
        `
        $('#applyTableData').append(lessonTableData);
        pagination(res.data, 'payment');
    });
    }
}).fail(error=>{
    console.log("Admin apply 리스트 조회 실패",error);
});
}


var lessonId = window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
function getLessonReviewList(page) {
$.ajax({
    url: `/api/admin/lessonReview/list/lessonId/${lessonId}?page=${page}`,
    dataType:"json"
}).done(res=>{
    console.log("Admin lessonReview 리스트 조회 성공",res);
    $('#lessonReviewTableData').empty();
    console.log(res.data.content.length);
    if (res.data.content.length == 0) {
        lessonReviewTableData = `
            <tr>
               <td colspan="5">조회된 자료가 없습니다.</td>
            </tr>
        `
        $('#lessonReviewTableData').append(lessonReviewTableData);
    } else {
    var count = 0;
    var sumScore = 0;
    res.data.content.forEach((dto) => {
        lessonReviewTableData = `
            <tr>
              <td>${dto.id}</td>
              <td>${dto.studentName}</td>
              <td>${dto.score}</td>
              <td>${dto.content}</td>
              <td>${dto.createTime}</td>
            </tr>
        `
        if (dto.score != null) {
            count++;
            sumScore += dto.score;
        }
        $('#lessonReviewTableData').append(lessonReviewTableData);
        pagination(res.data, 'lessonReview');
    });
    $('#lessonReviewAverageContainer').append(`
        <p style="background-color: #a7b6c2; width: fit-content; margin: auto; margin-bottom: 10px;">평가횟수 ${count}회 / 평균점수: ${sumScore/count}점</p>
    `)
    }
}).fail(error=>{
    console.log("Admin lessonReview 리스트 조회 실패",error);
});
}

//레슨 삭제
$('#lessonDelete').click(function() {
    $.ajax({
        type: 'delete',
        url:`/api/teacher/lesson/${lessonId}`,
        dataType:"json"
    }).done(res=>{
        console.log("레슨 삭제 성공",res);
    }).fail(error=>{
        console.log("레슨 삭제 실패",error);
    });
})

