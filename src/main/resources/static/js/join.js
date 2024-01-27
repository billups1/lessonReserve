$('#passwordRecheck').on('input', function () {
    if ($('#password').val() != $('#passwordRecheck').val()) {
        $('#passwordRecheckComment').empty();
        $('#passwordRecheckComment').append(`<a style="color: red;">비밀번호 란과 비밀번호 확인 란의 값을 동일하게 작성해 주세요.</a>`)
    } else {
        $('#passwordRecheckComment').empty();
    }
})

function verificationEmailSend() {
    var email = document.getElementById("email").value;
    console.log(email);
    let data = {
        email: email
    }
    $.ajax({
        url: "/api/mail/verificationEmailSend",
        data: data,
        dataType: "json"
    }).done(res => {
        console.log("인증메일 발송 성공");
        document.getElementById("verificationCode").style.display = "inline";
        $("#emailVerificationComment").empty();
        document.getElementById("emailVerificationComment").style.color = "black";
        let comment = "<a>인증메일이 발송되었습니다. 발송된 코드는 5분간 유효합니다.</a>"
        $("#emailVerificationComment").append(comment);
    }).fail(error => {
        console.log("인증메일 발송 실패", error);
        $("#emailVerificationComment").empty();
        let comment = "<a>인증메일 발송에 실패했습니다. 관리자에게 문의하세요.</a>"
        $("#emailVerificationComment").append(comment);
    });
}

function emailVerification() {
    var email = document.getElementById("email").value;
    var code = document.getElementById("verificationCodeInput").value;
    let data = {
        email: email,
        code: code
    }

    $.ajax({
        url: "/api/mail/emailVerification",
        data: data,
        dataType: "json"
    }).done(res => {
        console.log("이메일 인증 성공", res);
        $("#emailVerificationComment").empty();
        let comment = "<a>이메일 인증에 성공했습니다.</a>"
        $("#emailVerificationComment").append(comment);
        document.getElementById("emailVerificationComment").style.color = "blue";
    }).fail(error => {
        console.log("이메일 인증 실패", error);
        $("#emailVerificationComment").empty();
        let comment = "<a>이메일 인증에 실패했습니다.</a>"
        $("#emailVerificationComment").append(comment);
        document.getElementById("emailVerificationComment").style.color = "red";
    });

}