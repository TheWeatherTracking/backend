package edu.escw.server.repository

import edu.escw.server.model.Device
import edu.escw.server.model.Telemetry
import edu.escw.server.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource
interface DeviceRepository : JpaRepository<Device, Long> {
    @Query("SELECT d FROM Device d WHERE d.signature = ?1")
    fun getBySignature(device_signature: String): Optional<Device>;
}

@RepositoryRestResource
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    fun getByLogin(login: String): Optional<User>;
}

@RepositoryRestResource
interface TelemetryRepository : JpaRepository<Telemetry, Long> {
    @Query("SELECT t FROM Telemetry t WHERE t.tmstamp IN (SELECT max(t1.tmstamp) FROM Telemetry t1 " +
            "WHERE t1.device.signature = ?1) AND t.device.signature = ?1")
    fun getCurrentByDevice(device_signature: String): Optional<Telemetry>

    @Query("SELECT t FROM Telemetry t WHERE t.tmstamp > TO_TIMESTAMP(?1, 'YYYY-MM-DD HH24:MI:SS')")
    fun getAfter(timestamp: String, p: Pageable): Page<Telemetry>;

    @Query(value = "SELECT t FROM Telemetry t WHERE t.tmstamp > TO_TIMESTAMP(?1, 'YYYY-MM-DD HH24:MI:SS') AND t.device.signature = ?2")
    fun getAfterByDevice(timestamp: String, device_signature: String, p: Pageable): Page<Telemetry>;
}

