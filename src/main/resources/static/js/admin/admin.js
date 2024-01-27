
// 페이지네이션

function pagination(data, entityType) {
    var paginationHtml = `
    <nav aria-label="...">
            <ul class="pagination pagination-sm">
`
    var nowPageFirstNumber = data.pageable.pageNumber - (data.pageable.pageNumber % 10) + 1;
    var nowPageLastNumber = data.totalPages < nowPageFirstNumber + 9 ? data.totalPages : nowPageFirstNumber + 9;

    //    1페이지면 없음
    if (data.pageable.pageNumber > 9) {
        paginationHtml += `<li class="page-item disabled">`
        if (entityType == 'lesson') {
            paginationHtml += `<a class="page-link" onclick="getLessonList(${nowPageFirstNumber - 11})">Previous</a>`
        } else if (entityType == 'payment') {
            paginationHtml += `<a class="page-link" onclick="getPaymentList(${nowPageFirstNumber - 11})">Previous</a>`
        } else if (entityType == 'lessonReview') {
            paginationHtml += `<a class="page-link" onclick="getLessonReviewList(${nowPageFirstNumber - 11})">Previous</a>`
        } else if (entityType == 'apply') {
            paginationHtml += `<a class="page-link" onclick="getApplyList(${nowPageFirstNumber - 11})">Previous</a>`
        } else if (entityType == 'gather') {
            paginationHtml += `<a class="page-link" onclick="getGatherList(${nowPageFirstNumber - 11})">Previous</a>`
        } else if (entityType == 'user') {
            paginationHtml += `<a class="page-link" onclick="getUserList(${nowPageFirstNumber - 11})">Previous</a>`
        }
        paginationHtml += `</li>`
    }

    //    현재 클릭한 페이지 active 주기
    //    마지막인지 체크해서 마지막이면 해당 숫자까지만 보이게
    //    10페이지씩 보이게
    for (var i = nowPageFirstNumber; i <= nowPageLastNumber; i++) {
        if (data.pageable.pageNumber + 1 == i) {
            paginationHtml += `
            <li class="page-item active" aria-current="page">
              <a class="page-link" href="#">${i}</a>
            </li>
        `
        } else {
            if (entityType == 'lesson') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getLessonList(${i - 1})">${i}</a></li>`
            } else if (entityType == 'payment') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getPaymentList(${i - 1})">${i}</a></li>`
            } else if (entityType == 'lessonReview') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getLessonReviewList(${i - 1})">${i}</a></li>`
            } else if (entityType == 'apply') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getApplyList(${i - 1})">${i}</a></li>`
            } else if (entityType == 'gather') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getGatherList(${i - 1})">${i}</a></li>`
            } else if (entityType == 'user') {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" onclick="getUserList(${i - 1})">${i}</a></li>`
            }
        }
    }

    // 현재페이지 마지막숫자보다 토탈페이지수가 높으면 Next 버튼 출력
    if (nowPageLastNumber < data.totalPages) {
        paginationHtml += `<li class="page-item">`
        if (entityType == 'lesson') {
            paginationHtml += `<a class="page-link" href="#" onclick="getLessonList(${nowPageLastNumber})">Next</a>`
        } else if (entityType == 'payment') {
            paginationHtml += `<a class="page-link" href="#" onclick="getPaymentList(${nowPageLastNumber})">Next</a>`
        } else if (entityType == 'lessonReview') {
            paginationHtml += `<a class="page-link" href="#" onclick="getLessonReviewList(${nowPageLastNumber})">Next</a>`
        } else if (entityType == 'apply') {
            paginationHtml += `<a class="page-link" href="#" onclick="getApplyList(${nowPageLastNumber})">Next</a>`
        } else if (entityType == 'gather') {
            paginationHtml += `<a class="page-link" href="#" onclick="getGatherList(${nowPageLastNumber})">Next</a>`
        } else if (entityType == 'user') {
            paginationHtml += `<a class="page-link" href="#" onclick="getUserList(${nowPageLastNumber})">Next</a>`
        }
        paginationHtml += `</li>`
    }

    paginationHtml += `</ul>
                </nav>`;

    if (entityType == 'user') {
        $('#pagination').empty();
        $('#pagination').append(paginationHtml);
    } else if (entityType == 'payment') {
        $('#paymentListPagination').empty();
        $('#paymentListPagination').append(paginationHtml);
    } else if (entityType == 'lessonReview') {
        $('#lessonReviewListPagination').empty();
        $('#lessonReviewListPagination').append(paginationHtml);
    } else if (entityType == 'gather') {
        $('#gatherListPagination').empty();
        $('#gatherListPagination').append(paginationHtml);
    } else if (entityType == 'apply') {
        $('#applyPagination').empty();
        $('#applyPagination').append(paginationHtml);
    } else if (entityType == 'lesson') {
        $('#lessonPagination').empty();
        $('#lessonPagination').append(paginationHtml);
    }


}












