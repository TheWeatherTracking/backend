package edu.escw.server

import edu.escw.server.model.Device
import edu.escw.server.model.Telemetry
import edu.escw.server.service.DeviceService
import edu.escw.server.service.TelemetryService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.*
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Component
@Order(0)
class MqttSubscriber(private val deviceService: DeviceService, private val telemetryService: TelemetryService) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(p0: ApplicationReadyEvent) {

        // create root coroutine to handle devices addition
        GlobalScope.launch {
            val handledDevicesIds: HashSet<Long> = HashSet()
            while (true) {
                for (device in deviceService.getAll()) {
                    if (device.id !in handledDevicesIds) {
                        handledDevicesIds.add(device.id)

                        // create coroutine to handle given devise subscription
                        GlobalScope.launch {
                            val clientId: String = UUID.randomUUID().toString()
                            val client: IMqttClient = MqttClient("tcp://" + device.ip + ":1883", clientId)

                            val mqttConnectOptions: MqttConnectOptions = MqttConnectOptions()
                            mqttConnectOptions.isAutomaticReconnect = true;
                            mqttConnectOptions.isCleanSession = true;
                            mqttConnectOptions.connectionTimeout = 10;

                            client.setCallback(DeviceMqttCallback(telemetryService, device))
                            client.connect(mqttConnectOptions);

                            client.subscribe("/devices/" + device.signature)
                        }
                    }
                }

                // check new devices every N seconds
                delay(10_000L)
            }
        }
    }
}

class DeviceMqttCallback(private val telemetryService: TelemetryService, private val device: Device) : MqttCallback {

    override fun messageArrived(p0: String?, p1: MqttMessage?) {
        try {
            val telemetry = Telemetry()
            telemetry.tmstamp = Timestamp.valueOf(LocalDateTime.now())

            val values = p1.toString().split(";")
            for (pair in values) {
                val key = pair.split("=")[0]
                val value = pair.split("=")[1]

                if ("t".equals(key)) {
                    telemetry.temperature = value.replace(".", "").toLong()
                }

                if ("p".equals(key)) {
                    telemetry.pressure = value.replace(".", "").toLong()
                }

                if ("m".equals(key)) {
                    telemetry.moisture = value.replace(".", "").toLong()
                }

                if ("l".equals(key)) {
                    telemetry.luminosity = value.replace(".", "").toLong()
                }
            }

            telemetry.device = device

            println(telemetry)
            telemetryService.add(telemetry)
        } catch (e: Exception) {
            ;
        }
    }

    override fun connectionLost(p0: Throwable?) {
        println("connectionLost $p0")
    }

    override fun deliveryComplete(p0: IMqttDeliveryToken?) {
        println("deliveryComplete $p0")
    }

}