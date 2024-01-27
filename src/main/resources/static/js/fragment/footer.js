$.ajax({
    url: "/api/user",
    dataType: "json"
}).done(res => {
    $('#userId').val(res.data.userId);

    if ($('#userId').val()) {
        $('#logoutContainer').html('');
        $('#logoutContainer').append(`
            <a class="nav-link" href="/logout">로그아웃</a>
        `)

    }
}).fail(error => {
    console.log("유저 정보 불러오기 실패", error);
});