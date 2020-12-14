package edu.escw.server.controller

import edu.escw.server.model.Telemetry
import org.springframework.hateoas.EntityModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("telemetries")
class TelemetryController {

    @GetMapping()
    fun loadCurrent(): EntityModel<Telemetry> {
        return EntityModel.of(Telemetry())
    }

}