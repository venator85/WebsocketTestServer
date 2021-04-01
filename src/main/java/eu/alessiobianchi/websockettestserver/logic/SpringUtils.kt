package eu.alessiobianchi.websockettestserver.logic

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.eclipse.jetty.websocket.servlet.WebSocketServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun socketServlet(): ServletRegistrationBean<*> {
        return ServletRegistrationBean(WsServiceServlet(), "/ws/service")
    }

    @Bean
    fun beanUtil() = BeanUtil()
}

class WsServiceServlet : WebSocketServlet() {
    override fun configure(webSocketServletFactory: WebSocketServletFactory) {
        webSocketServletFactory.register(WsServiceSocket::class.java)
    }
}

@WebSocket
class WsServiceSocket {
    @OnWebSocketConnect
    fun onConnect(session: Session) {
        BeanUtil.relayService.addSession(session)
    }

    @OnWebSocketClose
    fun onClose(session: Session, _closeCode: Int, _closeReason: String?) {
        BeanUtil.relayService.removeSession(session)
    }
}

class BeanUtil : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        appCxt = applicationContext
    }

    companion object {
        private lateinit var appCxt: ApplicationContext

        @JvmStatic
        val relayService: RelayService
            get() = appCxt.autowireCapableBeanFactory.getBean("RelayService") as RelayService
    }
}
