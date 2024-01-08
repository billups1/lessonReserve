package hs.lessonReserve.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                //웹소켓 통신이 /ws-stomp 로 도착할때 해당 통신이 웹 소켓 통신 중에서 stomp 통신인 것을 확인하고, 이를 연결한다는 의미
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // 메세지를 구독하는 요청 url

        registry.setApplicationDestinationPrefixes("/pub"); // 메세지를 발행하는 요청 url
    }
}
