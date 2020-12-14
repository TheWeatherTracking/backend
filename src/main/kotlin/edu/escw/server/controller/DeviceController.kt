package edu.escw.server.controller

import edu.escw.server.service.DeviceService
import org.springframework.hateoas.EntityModel
import org.springframework.web.bind.annotation.RestController

@RestController("devices")
class DeviceController(private val deviceService: DeviceService) {

}