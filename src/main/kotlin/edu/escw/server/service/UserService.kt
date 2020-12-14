package edu.escw.server.service

import edu.escw.server.model.User
import edu.escw.server.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAll(): Iterable<User> = userRepository.findAll();

    fun getByLogin(login: String): Optional<User> = userRepository.getByLogin(login);

    fun add(user: User): User = userRepository.save(user)
}