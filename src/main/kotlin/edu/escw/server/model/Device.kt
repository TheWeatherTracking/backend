package edu.escw.server.model

import javax.persistence.*

@Entity
@Table(name = "devices")
class Device {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    var id: Long = 0;

    @Column(name = "signature")
    var signature: String = "";

    @Column(name = "ip")
    var ip: String = "";

    @Column(name = "props")
    var properties: String = "";

}