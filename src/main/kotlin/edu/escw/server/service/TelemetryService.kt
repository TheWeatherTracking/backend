package edu.escw.server.service

import edu.escw.server.model.Device
import edu.escw.server.model.Telemetry
import edu.escw.server.repository.TelemetryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TelemetryService(private val telemetryRepository: TelemetryRepository) {

    fun getAll(): Iterable<Telemetry> = telemetryRepository.findAll();

    fun getById(id: Long): Optional<Telemetry> = telemetryRepository.findById(id);

    fun add(obj: Telemetry): Telemetry = telemetryRepository.save(obj)

    fun update(id: Long, obj: Telemetry): Telemetry = telemetryRepository.save(obj)

    fun remove(id: Long) = telemetryRepository.deleteById(id)

}