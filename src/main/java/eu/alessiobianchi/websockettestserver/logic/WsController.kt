package eu.alessiobianchi.websockettestserver.logic

import eu.alessiobianchi.websockettestserver.logic.BeanUtil.Companion.relayService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/ws")
class WsController {

    @GetMapping("/ui")
    fun ui(): String {
        return "/ui.html"
    }

    @PostMapping("/post")
    fun post(@RequestParam("message") message: String): ResponseEntity<*> {
        relayService.sendToAll(message)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

}
