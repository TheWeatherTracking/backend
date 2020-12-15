package edu.escw.server

import com.google.common.hash.Hashing
import edu.escw.server.service.UserService
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"])
class AuthFilter(private val userService: UserService) : HttpFilter() {;

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (request.requestURI == "/api/users" && request.method.toUpperCase() == "POST") {
                super.doFilter(request, response, chain)
                return;
            }

            if (request.getHeader("Authorization") != null) {
                val authHeader = request.getHeader("Authorization")
                val userCridentials: String = String(Base64.getDecoder().decode(authHeader.replace("Basic ", "")))

                val login: String = userCridentials.split(":")[0];
                val password: String = userCridentials.split(":")[1];

                val optUser = userService.getByLogin(login)

                if (optUser.isPresent) {
                    val hashedPass: String = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
                    if (hashedPass.toUpperCase() == optUser.get().password.toUpperCase()) {
                        super.doFilter(request, response, chain)
                        return;
                    }
                }
            }

            response.sendError(403);
        } catch (e: Exception) {
            response.sendError(403);
        }
    }
}
