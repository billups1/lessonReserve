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


$('#sidoSelect').on("input", function() {
    var sido = $('#sidoSelect').val();
    let data = {
        sido: sido
    }
    $.ajax({
        url: "/api/address/SigunGu",
        data: data,
        dataType: 'json'
    }).done(res=>{
        console.log("시군구 리스트 불러오기 성공",res);
        $('#SigunGuSelect').empty();
        $('#SigunGuSelect').append(`
            <option selected>선택안함</option>
        `)
        res.data.forEach(s=>{
            $('#SigunGuSelect').append(`
                <option>${s}</option>
            `)
        })

    }).fail(error=>{
        console.log("시군구 리스트 불러오기 실패",error);
    });

})

$('#SigunGuSelect').on("input", function() {
    var sido = $('#sidoSelect').val();
    var SigunGu = $('#SigunGuSelect').val();
    let data = {
        sido: sido,
        SigunGu: SigunGu
    }
    $.ajax({
        url: "/api/address/eupMeonDong",
        data: data,
        dataType: 'json'
    }).done(res=>{
        console.log("읍면동 리스트 불러오기 성공",res);
        $('#eupMeonDongSelect').empty();
        $('#eupMeonDongSelect').append(`
            <option selected>선택안함</option>
        `)
        res.data.forEach(e=>{
            $('#eupMeonDongSelect').append(`
                <option>${e}</option>
            `)
        })

    }).fail(error=>{
        console.log("읍면동 리스트 불러오기 실패",error);
    });

})