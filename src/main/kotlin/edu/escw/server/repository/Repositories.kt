package edu.escw.server.repository

import edu.escw.server.model.Device
import edu.escw.server.model.Telemetry
import edu.escw.server.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.sql.Timestamp
import java.util.*
import javax.persistence.NamedNativeQuery

@RepositoryRestResource
interface DeviceRepository : JpaRepository<Device, Long> {

}

@RepositoryRestResource
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    fun getByLogin(login: String): Optional<User>;
}

@RepositoryRestResource
interface TelemetryRepository : JpaRepository<Telemetry, Long> {
    @Query("SELECT t FROM Telemetry t WHERE t.tmstamp > TO_TIMESTAMP(?1, 'YYYY-MM-DD HH24:MI:SS')")
    fun getAfter(timestamp: String, p: Pageable): Page<Telemetry>;

    @Query(value = "SELECT t FROM Telemetry t WHERE t.tmstamp > TO_TIMESTAMP(?1, 'YYYY-MM-DD HH24:MI:SS') AND t.device.id = ?2")
    fun getAfterByDevice(timestamp: String, id: Long, p: Pageable): Page<Telemetry>;
}

