
if (window.location.pathname == '/admin/apply/list') {
    getApplyList();
}

// 레슨신청·결제 리스트 불러오기

function getApplyList(page) {
    console.log("page", page);
    var adminApplySearchCond = getSearchCond();

    $.ajax({
        url: `/api/admin/apply/list?page=${page}`,
        data: adminApplySearchCond,
        dataType: "json"
    }).done(res => {
        console.log("Admin 레슨신청·결제 리스트 조회 성공", res);
        $('#applyTableData').empty();
        res.data.content.forEach((dto) => {
            applyTableData = `
                <tr onclick="location.href = '/admin/apply/${dto.applyId}'">
                  <td>${dto.applyId}</td>
                  <td>${dto.studentName}</td>
                  <td>${dto.lessonName}</td>
                  <td>${dto.teacherName}</td>
                  <td>${dto.paymentCreateTime}</td>
                  <td>${dto.price}</td>
                  <td>${dto.applyStatus}</td>
                </tr>
            `
            $('#applyTableData').append(applyTableData);
        });

        pagination(res.data, 'apply');

    }).fail(error => {
        console.log("Admin 레슨신청·결제 리스트 조회 실패", error);
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
    var date = ["이후", "이전"]

    $("#searchCond2").empty();

    if (value == "none" || value == "lessonName" || value == "student" || value == "teacher" || value == "applyStatus" || value == "paymentStatus") {
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
    } else if (value == "paymentCreateTime") {
        document.getElementById("searchCond2").style.display = "inline";
        for (var count = 0; count < date.length; count++) {
            $("#searchCond2").append("<option>" + date[count] + "</option>");
        }
        document.getElementById("searchText").style.display = "none";
        document.getElementById("searchDate").style.display = "inline";
    }
}



//레슨신청·결제 삭제
$('#applyDelete').click(function() {
    $.ajax({
        type: 'delete',
        url:`/api/teacher/apply/${applyId}`,
        dataType:"json"
    }).done(res=>{
        console.log("레슨신청·결제 삭제 성공",res);
    }).fail(error=>{
        console.log("레슨신청·결제 삭제 실패",error);
    });
})

