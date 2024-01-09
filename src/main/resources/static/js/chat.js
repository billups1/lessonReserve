
var stompClient = null;
var userId = $('#userId').val();
var userName = $('#userName').val();
var gatherId = $('#gatherId').val();
let today = new Date();
var createTime = today.toLocaleString();

$(document).ready(function() {
    connect();

    $.ajax({
        url: `/api/chat/list/${gatherId}`,
        dataType: "json"
    }).done(res=>{
        console.log("채팅리스트 불러오기 성공", res);
        res.data.forEach(chat => {
            var className = chat.userId == userId ? "myMessage" : "otherMessage";
            var chatElement = `
            <div class="${className}">
              <p>${chat.name}</p>
              <p>${chat.message}</p>
              <p>${chat.createTime}</p>
            </div>
            `
            $('#gatherChattingContainer').append(chatElement);
        });
    }).fail(error=>{
        console.log("채팅리스트 불러오기 실패", error);
    });
    $.ajax({
        url: `/api/chat/memberList/${gatherId}`,
        dataType: "json"
    }).done(res=>{
    console.log("모임 멤버 리스트 불러오기 성공", res);
    res.data.forEach(member => {
        var memberElement = `
        <div style="font-size: large; padding: 6px; border: 1pt solid gray">
            ${member.name}
        </div>
        `
        $('#gatherMemberContainer').append(memberElement);
        });
    }).fail(error=>{
        console.log("모임 멤버 리스트 불러오기 실패", error);
    });

});

$('#gatherChattingContainer').scrollTop($('#gatherChattingContainer')[0].scrollHeight);

function connect(event) {
    console.log("connect")
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

}

function onConnected() {
    console.log("onConnected")
    stompClient.subscribe('/sub/chat/gather/' + gatherId, onMessageReceived);

//  입장 메세지
//    stompClient.send('/pub/api/chat/enterUser',
//        {},
//        JSON.stringify({
//            gatherId: gatherId,
//            userId: userId,
//            userName, userName,
//            createTime: null,
//            type: "ENTER"
//        })
//    )
}

function onError() {
    console.log("오류가 발생했습니다.")
}

$('#gatherChattingSend').click(sendMessage);
$('#gatherChattingSend').on('keyup', function(key) {
    if (key.keyCode == 13) {
        sendMessage();
    }
});

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
    $('#messageInput').val('');
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

