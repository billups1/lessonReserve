    function cancelApply(lessonId) {
    console.log(lessonId)

    $.ajax({
        type:"put",
        url:`/api/student/lesson/${lessonId}`,
        dataType:"json"
    }).done(res=>{
        console.log("수강취소 성공", res);
        alert("수강취소가 완료 되었습니다.");
        location.reload();
    }).fail(error=>{
        console.log("수강취소 실패", error);
    });

    }