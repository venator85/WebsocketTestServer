package eu.alessiobianchi.websockettestserver.logic;

import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service("RelayService")
public class RelayService {
    private final Set<Session> listenerSessions = new CopyOnWriteArraySet<>();

    public void removeSession(Session session) {
        listenerSessions.remove(session);
    }

    public void addSession(Session session) {
        listenerSessions.add(session);
    }

    @PostConstruct
    private void keepAlive() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30_000);
                    String message = "{}";
                    listenerSessions.stream()
                            .filter(Session::isOpen)
                            .forEach(session -> send(session, message));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void sendToAll(String message) {
        listenerSessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> send(session, message));
    }

    private void send(Session session, String message) {
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
