

var stompClient = null;
var userId = $('#userId').val();
var userName = $('#userName').val();
var gatherId = $('#gatherId').val();
let today = new Date();
var createTime = today.toLocaleString();

$(document).ready(connect);

function connect(event) {
    console.log("connect")
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

}

function onConnected() {
    console.log("onConnected")
    stompClient.subscribe('/sub/chat/gather/' + gatherId, onMessageReceived);

    stompClient.send('/pub/api/chat/enterUser',
        {},
        JSON.stringify({
            gatherId: gatherId,
            userId: userId,
            userName, userName,
            createTime: null,
            type: "ENTER"
        })
    )
}

function onError() {
    console.log("오류가 발생했습니다.")
}

$('#gatherChattingSend').click(sendMessage);
$('#gatherChattingSend').on('keyup', function(key) {
    if (key.keyCode == 13) {
        sendMessage();
    }
})

function sendMessage(event) {
    var messageContent = $('#messageInput').val();
    if (messageContent && stompClient) {
        var chatMessage = {
            type: 'TALK',
            gatherId: gatherId,
            userId: userId,
            userName: userName,
            createTime: null,
            message: messageContent
        }

        stompClient.send('/pub/api/chat/sendMessage', {}, JSON.stringify(chatMessage));
    }
    event.preventDefault();
    $('#messageInput').html('');
}

function onMessageReceived(payload) {
    var chat = JSON.parse(payload.body);
    console.log(chat);

    if (chat.type == 'TALK') {

        var chatRoomElement = document.createElement('div');

        var userNameElement = document.createElement('p');
        var userNameText = document.createTextNode(chat.userName);
        userNameElement.appendChild(userNameText);
        chatRoomElement.appendChild(userNameElement);

        var messageElement = document.createElement('p');
        var messageText = document.createTextNode(chat.message);
        messageElement.appendChild(messageText);
        chatRoomElement.appendChild(messageElement);

        var createTimeElement = document.createElement('p');
        var createTimeText = document.createTextNode(chat.createTime);
        createTimeElement.appendChild(createTimeText);
        chatRoomElement.appendChild(createTimeElement);

        if (chat.userId == userId) {
            chatRoomElement.classList.add('myMessage')
        } else {
            chatRoomElement.classList.add('otherMessage')
        }
    }

    $('#gatherChattingContainer').append(chatRoomElement);
    $('#gatherChattingContainer').scrollTop($('#gatherChattingContainer')[0].scrollHeight);

}

