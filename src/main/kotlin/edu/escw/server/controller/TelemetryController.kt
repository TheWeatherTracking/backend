package edu.escw.server.controller

import edu.escw.server.MqttPublisher
import edu.escw.server.model.Telemetry
import edu.escw.server.service.DeviceService
import edu.escw.server.service.TelemetryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("telemetries")
class TelemetryController(private val deviceService: DeviceService, private val mqttPublisher: MqttPublisher, private val telemetryService: TelemetryService) {

    @GetMapping("/loadCurrent")
    fun loadCurrentByDevice(@RequestParam(name = "device_signature", defaultValue = "") deviceSignature: String): ResponseEntity<Telemetry> {
        // find device
        val device = deviceService.getBySignature(deviceSignature)
        if (device.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        // send command
        mqttPublisher.loadCurrent(device.get())

        // get telemetries
        val optTelemetry = telemetryService.getCurrentByDevice(deviceSignature)

        if (optTelemetry.isEmpty) {
            return ResponseEntity.status(500).build()
        }

        // return
        return ResponseEntity.ok(optTelemetry.get())
    }

}