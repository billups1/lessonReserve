function homeLessonList() {
console.log("homeLessonList")

    $.ajax({
        url: "/api/lesson/home",
        dataType:"json"
    }).done(res=>{
        console.log("홈 레슨리스트 불러오기 성공",res);
        res.data.content.forEach((l)=>{
            let lesson = getHomeLesson(l);
            $("#lessonList").append(lesson)
        })

        let totalPages = res.data.totalPages;
        let pageNumber = res.data.pageable.pageNumber;

//        let pagination = getPagination(p);
//        $("#lessonList").append(pagination)
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
            <td>${lesson.teacher.name}</td>
            <td><img src="/image/${lesson.teacher.profileImageUrl}" width="50" height="50"/></td>
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

    l +=   `</tr>`
    return l;
}

function getPagination(totalPages) {
    let p = `
        <nav aria-label="...">
          <ul class="pagination">
            <li class="page-item disabled">
              <a class="page-link">Previous</a>
            </li>

            <li class="page-item"><a class="page-link" href="#">1</a></li>

            <li class="page-item active" aria-current="page">
              <a class="page-link" href="#">2</a>
            </li>

            <li class="page-item"><a class="page-link" href="#">3</a></li>

            <li class="page-item">
              <a class="page-link" href="#">Next</a>
            </li>

          </ul>
        </nav>

    `

}