function gatherCard() {
    $.ajax({
        url: `/api/gather/list/mypage`,
        dataType: 'json'
    }).done(res=>{
        console.log("모임 리스트 불러오기 성공", res);
        res.data.forEach(gather => {
            var gatherCard = getGatherCard(gather);
            $('#gatherCardContainer').append(gatherCard);
        })
    }).fail(error=>{
        console.log("모임 리스트 불러오기 실패", error);
    })
}

gatherCard();

function getGatherCard(gather) {
    g = `
    <div class="col">
        <div class="card" style="width: 18rem;">
            <img src="/image/${gather.representativeImageUrl}" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">${gather.name}</h5>
                <p class="card-text">
                    ${gather.content}
                </p>
                <p class="card-text">
                    ${gather.address}
                </p>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" onclick="location.href='/gather/chatting/${gather.id}'">
                  모임 채팅방
                </button>
                `
    if (gather.leaderState) {
        g +=`<br>
        <button type="button" class="btn btn-danger" data-bs-toggle="modal" id="gatherMemberBtn">
          모임 멤버 명단
        </button><br>
        <button type="button" class="btn btn-danger" data-bs-toggle="modal" id="gatherDeleteBtn">
          모임 없애기
        </button>`
    }

    g +=    `</div>
        </div>
    </div>`
    return g;
}