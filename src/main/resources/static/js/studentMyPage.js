function cancelApply(applyId) {
  console.log(applyId)

  $.ajax({
    type: "put",
    url: `/api/student/lesson/${applyId}`,
    dataType: "json"
  }).done(res => {
    console.log("수강취소 성공", res);
    alert("수강취소가 완료 되었습니다.");
    location.reload();
  }).fail(error => {
    console.log("수강취소 실패", error);
  });

}

function writeLessonReview(dto) {
  console.log(dto);

}

function openLessonReviewModal(lesson, teacher) {
  let modal = `<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                       <div class="modal-dialog">
                         <div class="modal-content">
                           <div class="modal-header">
                             <h1 class="modal-title fs-5" id="exampleModalLabel">New message</h1>
                             <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                           </div>
                           <div class="modal-body">
                             <form>

                               점수

                               <div class="mb-3">
                                 <label for="message-text" class="col-form-label">수강평:</label>
                                 <textarea class="form-control" id="message-text"></textarea>
                               </div>
                             </form>
                           </div>
                           <div class="modal-footer">
                             <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                             <button type="button" class="btn btn-primary">Send message</button>
                           </div>
                         </div>
                       </div>
                     </div>`
}


