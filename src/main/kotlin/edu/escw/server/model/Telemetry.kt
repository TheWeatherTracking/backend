package edu.escw.server.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "telemetry")
class Telemetry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telemetry_id")
    var id: Long = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    var device: Device? = null;

    @Column(name = "temperature")
    var temperature: Long = 0;

    @Column(name = "pressure")
    var pressure: Long = 0;

    @Column(name = "moisture")
    var moisture: Long = 0;

    @Column(name = "luminosity")
    var luminosity: Long = 0;

    @Column(name = "tmstamp")
    var tmstamp: Timestamp? = null;

}
