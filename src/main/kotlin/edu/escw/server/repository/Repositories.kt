package edu.escw.server.repository

import edu.escw.server.model.Device
import edu.escw.server.model.Telemetry
import edu.escw.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import java.util.*

@RepositoryRestResource
interface DeviceRepository : JpaRepository<Device, Long> {

}

@RepositoryRestResource
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    fun getByLogin(login: String): Optional<User>;
}

@RepositoryRestResource
interface TelemetryRepository : JpaRepository<Telemetry, Long> {}
