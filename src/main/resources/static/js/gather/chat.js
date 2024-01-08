

var stompClient = null;
var userId = null;
var gatherId = null;

$(document).ready(connect);

function connect(event) {

    var socket = new SocketJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe('/sub/chat/gather/' + gatherId, onMessageReceived);

    stompClient.send('/pub/api/chat/enterUser',
        {},
        JSON.stringify({
            "gatherId": gatherId,
            "userId": userId,
            type: "ENTER"
        })
    )
}

function onError() {
    alert("에러가 발생했습니다. 관리자에게 문의하세요.")
}

$('#gatherChattingSend').click(sendMessage);

function sendMessage(event) {
    if (messageContent && stompClient) {
        var chatMessage = {
            type: 'TALK'
            gatherId: gatherId,
            userId: userId,
            message: $('#messageInput').val()
        }

        stompClient.send('/pub/api/chat/sendMessage', {}, JSON.stringify(chatMessage));
        $('#messageInput').empty();
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    console.log("페이로드" + payload);
    var chat = JSON.parse(payload.body);

}

