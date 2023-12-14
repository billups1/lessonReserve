function homeLessonList() {
console.log("homeLessonList")
var cond1 = document.getElementById('searchCond1');
var cond2 = document.getElementById('searchCond2');
var searchText = document.getElementById('searchText');
console.log(cond1, cond2, searchText);

    $.ajax({
        url: `/api/lesson/home?cond1=${cond1}&cond2=${cond2}&searchText=${searchText}`,
        dataType:"json"
    }).done(res=>{
        console.log("홈 레슨리스트 불러오기 성공",res);
        res.data.content.forEach((l)=>{
            let lesson = getHomeLesson(l);
            $("#lessonList").append(lesson)
        })
        let searchBox = getSearchBox();
        $("#searchBox").append(searchBox);


    }).fail(error=>{
        console.log("홈 레슨리스트 불러오기 실패",error);
    });

}

homeLessonList();

function getHomeLesson(lesson) {
    let l = `
        <tr>
            <td>${lesson.id}</td>
            <td>${lesson.name}</td>
            <td><a href="/teacher/${lesson.teacher.id}">${lesson.teacher.name}</a></td>
            <td><a href="/teacher/${lesson.teacher.id}"><img src="/image/${lesson.teacher.profileImageUrl}" width="50" height="50"/></a></td>
            <td>${lesson.lessonTime}</td>
            <td>${lesson.price}</td>
            <td>${lesson.lessonStartDate}</td>
            <td>${lesson.lessonEndDate}</td>
            <td>${lesson.applyEndDate}</td>
            <td>${lesson.applyStatus}</td>`

    if(lesson.userApplyStatus) {
                l +=  `<td><a>신청완료</a></td>`
            } else {
                l +=  `<td><a href="/lesson/${lesson.id}">신청하기</a></td>`
            }

    l +=   `</tr><br>`
    return l;
}

function getSearchBox() {
    s = `<div>
             <a>레슨검색</a>
             <select name="searchCond1" id="searchCond1" onchange="itemChange(this.value)">
                 <option value="none">=== 선택 ===</option>
                 <option value="lessonName">강의명</option>
                 <option value="teacher">강사</option>
                 <option value="price">가격</option>
                 <option value="lessonStartDate">레슨시작일</option>
                 <option value="lessonEndDate">레슨종료일</option>
             </select>

             <select name="searchCond2" id="searchCond2">
             </select>

             <input type="text" name="searchText" id="searchText" required="required"/>

             <button onclick="homeLessonList()">검색</button>

         </div>`
    return s;
}

function itemChange(value){
    var price = ["이상", "이하"]
    var lessonDate = ["이후", "이전"]

    $("#searchCond2").empty();

    if (value == "lessonName" || value == "teacher") {
        $("#searchCond2").append("<option>none</option>");
    } else if(value == "price") {
        for(var count = 0; count < price.length; count++) {
            $("#searchCond2").append("<option>" + price[count] + "</option>");
        }
    } else if(value == "lessonStartDate" || value == "lessonEndDate") {
        for(var count = 0; count < price.length; count++) {
            $("#searchCond2").append("<option>" + lessonDate[count] + "</option>");
        }
    }

}