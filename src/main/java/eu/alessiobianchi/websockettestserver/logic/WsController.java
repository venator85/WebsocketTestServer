package eu.alessiobianchi.websockettestserver.logic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ws")
public class WsController {

    @GetMapping("/ui")
    public String ui() {
        return "/ui.html";
    }

    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestParam("message") String message) {
        BeanUtil.getRelayService().sendToAll(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
