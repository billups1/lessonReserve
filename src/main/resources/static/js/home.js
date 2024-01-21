function homeLessonList(page) {
    if (page == null) {
        page = 0;
    }

    $('#lessonList > tbody').empty();
    $.ajax({
        url: `/api/lesson/home?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("홈 레슨리스트 불러오기 성공", res);

        res.data.content.forEach((l) => {
            let lesson = getHomeLesson(l);
            $("#lessonList").append(lesson)
        })
        $('#pagination').empty();
        let pagination = getPagination(page, res.data.pageable.pageNumber, res.data.totalPages)
        $("#pagination").append(pagination);

    }).fail(error => {
        console.log("홈 레슨리스트 불러오기 실패", error);
    });
}

homeLessonList();


function homeLessonListCond(page) {
    console.log(page);
    if (page == null) {
        page = 0;
    }
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

    $('#lessonList > tbody').empty();

    $.ajax({
        url: `/api/lesson/home?page=${page}`,
        dataType: "json",
        data: data
    }).done(res => {
        console.log("홈 레슨리스트 불러오기 성공", res);
        res.data.content.forEach((l) => {
            let lesson = getHomeLesson(l);
            $("#lessonList").append(lesson)
        });

        $('#pagination').empty();
        let pagination = getPagination(page, res.data.pageable.pageNumber, res.data.totalPages)
        $("#pagination").append(pagination);

    }).fail(error => {
        console.log("홈 검색 레슨리스트 불러오기 실패", error);
    });

}

function getHomeLesson(lesson) {
    let l = `
    <tbody>
        <tr>
            <td>${lesson.name}</td>
            <td><a href="/teacher/${lesson.teacherId}"><img src="/image/${lesson.teacherProfileImageUrl}" width="60" height="60"/></a><br><a href="/teacher/${lesson.teacherId}">${lesson.teacherName}</a></td>
            <td>${lesson.lessonTime}</td>
            <td>${lesson.price}</td>
            <td>${lesson.lessonStartDate}<br>~<br>${lesson.lessonEndDate}</td>
            <td>${lesson.applyEndDate}</td>
            <td>${lesson.applyStatus}</td>`

    if (lesson.userApplyStatus) {
        l += `<td><a>신청완료</a></td>`
    } else {
        l += `<td><a href="/lesson/${lesson.id}">자세히<br>보기</a></td>`
    }

    l += `</tr></tbody><br>`
    return l;
}

function getPagination(page, pageNumber, totalPages) {
    p = `
    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">`

    var startPage = 0;
    var endPage = totalPages;
    var nowPage = pageNumber;

    var pageCount;
    for (var i = startPage; i < endPage; i++) {
        if (i != nowPage) {
            pageCount = i + 1;
            p += `<li class="page-item"><a class="page-link" onclick="homeLessonListCond(${i})">${pageCount}</a></li>`;
        } else {
            pageCount = i + 1;
            p += `<li class="page-item active" aria-current="page">
                    <a class="page-link">${pageCount}</a>
                  </li>`;
        }
    }

    p += `</ul>
    </nav>
    `
    return p;
}

getSearchBox();
function getSearchBox() {
    s = `<div id="searchLesson" style="text-align: center">
             <a>레슨검색</a>
             <select name="searchCond1" id="searchCond1" onchange="itemChange(this.value)">
                 <option value="none">=== 선택 ===</option>
                 <option value="lessonName">강의명</option>
                 <option value="teacher">강사</option>
                 <option value="price">가격</option>
                 <option value="lessonStartDate">레슨시작일</option>
                 <option value="lessonEndDate">레슨종료일</option>
             </select>

             <select name="searchCond2" id="searchCond2" style="display:none;">
             </select>

             <input type="text" name="searchText" id="searchText" style="display: inline;"/>
             <input type="date" name="searchDate" id="searchDate" style="display: none;"/>

             <button class="btn btn-primary" onclick="homeLessonListCond(0)">검색</button>

         </div>`

    $("#searchBox").append(s);
}

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
        for (var count = 0; count < price.length; count++) {
            $("#searchCond2").append("<option>" + lessonDate[count] + "</option>");
        }
        document.getElementById("searchText").style.display = "none";
        document.getElementById("searchDate").style.display = "inline";
    }
}