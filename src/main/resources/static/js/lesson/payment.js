//
//import pgData from "./paymentSecretKey.js";
//console.log(pgData.MerchantIdentificationCode)

$('#pgTab').click(function (e) {
  tabOpen(e.target.dataset.toggle)
})


function tabOpen(toggle) {
  $('.list-group-item-action').removeClass('active');
  if (toggle == 'kakaopay') {
    $('#kakaopay').addClass('active');
  } else if (toggle == 'danal') {
    $('#danal').addClass('active');
  } else if (toggle == 'kcp') {
    $('#kcp').addClass('active');
  }
}

$('#paymentBtn').click(function () {
  if (!$("#lessonPolicyChkBox").is(":checked") || !$("#pgPolicyChkBox").is(":checked")) {
    $('#policyAgreeComment').empty();
    $('#policyAgreeComment').append(`
        <a style="color: red;">약관에 동의해 주세요.</a>
    `)
    return;
  }
  requestPay();
})

function requestPay() {

  var pg;
  for (let i = 0; $('#pgTab').children('a').length; i++) {
    if ($('.list-group-item').eq(i).hasClass('active')) {
      var pgCompany = $('.list-group-item').eq(i).data('toggle');
      if (pgCompany == "kakaopay") {
        pg = "kakaopay.TC0ONETIME";
      } else if (pgCompany == "danal") {
        pg = "danal_tpay.9810030929"
      }
      break;
    }
  }

  IMP.init('imp56433423')
  IMP.request_pay({
    pg: pg, // 아임포트 테스트계정 결제 o : 다날, 카카오페이    결제 x : nhnkcp, ksnet(사업자번호 미등록)
    pay_method: "card",
    merchant_uid: Math.random(),   // 주문번호
    name: $('#lessonName').val(),
    amount: $('#lessonPrice').val(),                         // 숫자 타입
    buyer_email: $('#buyer_email').val(),
    buyer_name: $('#buyer_name').val(),
    buyer_tel: $('#buyer_tel').val(),
    buyer_addr: $('#buyer_addr').val(),
    buyer_postcode: $('#buyer_postcode').val()
  }, function (rsp) { // callback
    console.log(rsp);
    if (rsp.success) {
      // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
      // jQuery로 HTTP 요청
      var data = {
        "imp_uid": rsp.imp_uid,            // 포트원 결제 고유번호
        "merchant_uid": rsp.merchant_uid,   // 주문번호
        "totalPrice": rsp.paid_amount,      // 총 금액
        "lessonId": $('#lessonId').val(),
        "pay_method": rsp.pay_method,
        "pg_provider": rsp.pg_provider,
        "lessonPolicyAgree": $("#lessonPolicyChkBox").is(":checked"),
        "pgPolicyAgree": $("#pgPolicyChkBox").is(":checked")
      }
      $.ajax({
        url: "/api/lesson/apply/payment",
        method: "POST",
        dataType: "json",
        data: data
      }).done(function (res) {
        console.log("res", res.data);
        window.location.href = '/lesson/paymentComplete/' + res.data;
      }).fail(error => {
        console.log("결제 실패", error);
        cancelPay();
      });
    } else {
      console.log(rsp.status);
      alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
    }
  })
};

$('#cancelBtn').click(function () {
  if (!$("#refundPolicyChkBox").is(":checked") || !$("#pgPolicyChkBox").is(":checked")) {
    $('#policyAgreeComment').empty();
    $('#policyAgreeComment').append(`
        <a style="color: red;">약관에 동의해 주세요.</a>
    `)
    return;
  }
  cancelPay();
})

function cancelPay() {
  $.ajax({
    url: "/api/lesson/payment/cancel",  // 예: http://www.myservice.com/payments/cancel
    type: "POST",
    data: {
      paymentId: $('#paymentId').val(), // 예: ORD20180131-0000011
      cancel_request_amount: null,  // 환불금액
      refund_holder: null,  // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
      refund_bank: null,  // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(예: KG이니시스의 경우 신한은행은 88번)
      refund_account: null  // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
    },
    dataType: "json"
  }).done(res => {
    console.log("취소 성공", res);
    location.replace("/lesson/cancelComplete/" + $('#paymentId').val());
  }).fail(error => {
    console.log("취소 실패", error);
  });
  ;
}