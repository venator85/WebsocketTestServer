package eu.alessiobianchi.websockettestserver.logic

import org.eclipse.jetty.websocket.api.Session
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet
import javax.annotation.PostConstruct

@Service("RelayService")
class RelayService {
    private val listenerSessions: MutableSet<Session> = CopyOnWriteArraySet()

    fun removeSession(session: Session) {
        listenerSessions.remove(session)
    }

    fun addSession(session: Session) {
        listenerSessions.add(session)
    }

    @PostConstruct
    private fun keepAlive() {
        val thread = Thread {
            while (true) {
                try {
                    Thread.sleep(30000)
                    val message = "{}"
                    listenerSessions.stream()
                        .filter { obj -> obj.isOpen }
                        .forEach { session -> send(session, message) }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }

    fun sendToAll(message: String) {
        listenerSessions.stream()
            .filter { obj -> obj.isOpen }
            .forEach { session -> send(session, message) }
    }

    private fun send(session: Session, message: String) {
        try {
            session.remote.sendString(message)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}