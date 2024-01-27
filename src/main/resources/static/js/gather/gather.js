function gatherCard() {

    $.ajax({
        url: `/api/gather/list`,
        dataType: 'json'
    }).done(res => {
        console.log("모임 리스트 불러오기 성공", res);
        res.data.forEach(gather => {
            var gatherCard = getGatherCard(gather);
            $('#gatherCardContainer').append(gatherCard);
        })
    }).fail(error => {
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
                </p>`
    if (gather.userGatherState == null) {
        g += `<button type="button" class="btn btn-primary" data-bs-toggle="modal" id="gatherModalBtn" data-gatherId="${gather.id}">
          가입 신청하기
        </button>`
    } else {
        g += `<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-gatherId="${gather.id}">
          신청완료
        </button>`
    }

    g += `</div>
        </div>
    </div>`
    return g;
}

$(document).on('click', '#gatherModalBtn', function (e) {
    console.log($('#userId').val())
    if (!$('#userId').val()) {
        alert('로그인이 필요합니다.')
        return;
    }

    $('.black-bg').addClass("show-modal");
    $('#gatherId').val(this.getAttribute('data-gatherId'));
});

$('#close').click(function () {
    $('.black-bg').removeClass("show-modal");
})



$('#sidoSelect').on("input", function () {
    var sido = $('#sidoSelect').val();
    let data = {
        sido: sido
    }
    $.ajax({
        url: "/api/address/SigunGu",
        data: data,
        dataType: 'json'
    }).done(res => {
        console.log("시군구 리스트 불러오기 성공", res);
        $('#SigunGuSelect').empty();
        $('#SigunGuSelect').append(`
            <option selected>선택안함</option>
        `)
        res.data.forEach(s => {
            $('#SigunGuSelect').append(`
                <option>${s}</option>
            `)
        })

    }).fail(error => {
        console.log("시군구 리스트 불러오기 실패", error);
    });

})

$('#SigunGuSelect').on("input", function () {
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
    }).done(res => {
        console.log("읍면동 리스트 불러오기 성공", res);
        $('#eupMeonDongSelect').empty();
        $('#eupMeonDongSelect').append(`
            <option selected>선택안함</option>
        `)
        res.data.forEach(e => {
            $('#eupMeonDongSelect').append(`
                <option>${e}</option>
            `)
        })

    }).fail(error => {
        console.log("읍면동 리스트 불러오기 실패", error);
    });
})

