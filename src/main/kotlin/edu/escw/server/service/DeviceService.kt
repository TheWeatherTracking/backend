package edu.escw.server.service

import edu.escw.server.model.Device
import edu.escw.server.repository.DeviceRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeviceService (private val deviceRepository: DeviceRepository) {

    fun getAll(): Iterable<Device> = deviceRepository.findAll();

    fun getById(id: Long): Optional<Device> = deviceRepository.findById(id);

    fun add(product: Device): Device = deviceRepository.save(product)

    fun update(id: Long, device: Device): Device = deviceRepository.save(device)

    fun remove(id: Long) = deviceRepository.deleteById(id)

}
