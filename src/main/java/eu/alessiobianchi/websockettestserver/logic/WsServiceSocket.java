package eu.alessiobianchi.websockettestserver.logic;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class WsServiceSocket {
    @OnWebSocketConnect
    public void onConnect(Session session) {
        BeanUtil.getRelayService().addSession(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int _closeCode, String _closeReason) {
        BeanUtil.getRelayService().removeSession(session);
    }
}
