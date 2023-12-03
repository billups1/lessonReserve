    function cancelLesson(lessonId) {
    console.log(lessonId)

    $.ajax({
        type:"delete",
        url:`/api/teacher/lesson/${lessonId}`,
        dataType:"json"
    }).done(res=>{
        console.log("강의삭제 성공", res);
        alert("수강삭제가 완료 되었습니다.");
        location.reload();
    }).fail(error=>{
        console.log("강의삭제 실패", error);
    });

    }