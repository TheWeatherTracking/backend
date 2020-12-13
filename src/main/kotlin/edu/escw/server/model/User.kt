package edu.escw.server.model

import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0;

    @Column(name = "login")
    var login: String = "";

    @Column(name = "password")
    var password: String = "";
}