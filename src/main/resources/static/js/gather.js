function gatherCard() {

    $.ajax({
        url: `/api/gather/list`,
        dataType: 'json'
    }).done(res=>{
        console.log("모임 리스트 불러오기 성공", res);



    }).fail(error=>{
        console.log("모임 리스트 불러오기 실패", error);
    })
}

gatherCard();

function getGatherCard(gather) {
    g = `
    <div class="col">
        <div class="card" style="width: 18rem;">
            <img src="..." class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of
                    the
                    card's content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
    </div>

    `

    return g;
}