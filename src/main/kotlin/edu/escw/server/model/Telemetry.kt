package edu.escw.server.model

import java.sql.Timestamp
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Telemetry

        if (id != other.id) return false
        if (device != other.device) return false
        if (temperature != other.temperature) return false
        if (pressure != other.pressure) return false
        if (moisture != other.moisture) return false
        if (luminosity != other.luminosity) return false
        if (tmstamp != other.tmstamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (device?.hashCode() ?: 0)
        result = 31 * result + temperature.hashCode()
        result = 31 * result + pressure.hashCode()
        result = 31 * result + moisture.hashCode()
        result = 31 * result + luminosity.hashCode()
        result = 31 * result + (tmstamp?.hashCode() ?: 0)
        return result
    }


}