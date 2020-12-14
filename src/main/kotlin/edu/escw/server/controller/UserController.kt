package edu.escw.server.controller

import com.google.common.hash.Hashing
import edu.escw.server.model.User
import edu.escw.server.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("users")
class UserController(private val userService: UserService) {

    @PostMapping()
    fun createUser(
            @RequestParam(name = "login", defaultValue = "") login: String,
            @RequestParam(name = "password", defaultValue = "") password: String
            ): ResponseEntity<User> {
        println("$login $password")

        if (login.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().build()
        }

        if (userService.getByLogin(login).isPresent) {
            return ResponseEntity.status(409).build()
        }

        var user: User = User()
        user.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
        user.login = login

        user = userService.add(user)

        return ResponseEntity.status(201).body(user)
    }

}
