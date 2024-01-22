
if (window.location.pathname == '/admin/user/list') {
    getUserList();
} else if (window.location.pathname.startsWith('/admin/user/') && !isNaN(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)) ) {
    getLessonList();
    getLessonReviewList();
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


// 레슨 리스트 불러오기
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

