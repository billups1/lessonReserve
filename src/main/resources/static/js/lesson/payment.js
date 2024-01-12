

$('#lessonInfo').append(`

`)


$('#paymentBtn').click(function () {
  requestPay();
})

function requestPay() {
  IMP.init('imp56433423')
  IMP.request_pay({
    pg: "kakaopay.TC0ONETIME", //보안 필요 // 아임포트 테스트계정 결제 o : 다날, 카카오페이    결제 x : nhnkcp, ksnet(사업자번호 미등록)
    pay_method: "card",
    merchant_uid: Math.random(),   // 주문번호
    name: $('#lessonName'),
    amount: $('#lessonPrice'),                         // 숫자 타입
    buyer_email: $('#buyer_email'),
    buyer_name: $('#buyer_name'),
    buyer_tel: $('#buyer_tel'),
    buyer_addr: $('#buyer_addr'),
    buyer_postcode: $('#buyer_postcode')
  }, function (rsp) { // callback
    if (rsp.success) {
      // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
      // jQuery로 HTTP 요청
      var data = {
        "imp_uid": rsp.imp_uid,            // 결제 고유번호
        "merchant_uid": rsp.merchant_uid   // 주문번호
      }
      $.ajax({
        url: "/api/lesson/apply/payment",
        method: "POST",
        dataType: "json",
        data: data
      }).done(function (res) {
        console.log("res", res.data)
        if (100 == res.data) {
          data = {
            "orderNum": rsp.merchant_uid,
            "productId": null,
            "userId": null,
            "totalPrice": null,
            "imp_uid": rsp.imp_uid
          };

          $.ajax({
            type: "post",
            url: "/api/lesson/apply/paymentComplete",
            dataType: "json",
            data: data
          }).done(res => {
            console.log("주문정보 저장 성공", res);
          }).fail(error => {
            console.log("주문정보 저장 실패", error);
          });
        } else {
          alert("결제 실패")
        }
      })
    } else {
      alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
    }
  })
};