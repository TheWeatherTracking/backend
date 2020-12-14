package edu.escw.server

import edu.escw.server.model.Device
import org.eclipse.paho.client.mqttv3.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class MqttPublisher {

    fun loadCurrent(device: Device) {
        val clientId: String = UUID.randomUUID().toString()
        val client: IMqttClient = MqttClient("tcp://" + device.ip + ":1883", clientId)

        val mqttConnectOptions: MqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true;
        mqttConnectOptions.isCleanSession = true;
        mqttConnectOptions.connectionTimeout = 10;

        val topic: MqttTopic = client.getTopic("/devices/" + device.signature)
        val message: MqttMessage = MqttMessage("7".toByteArray())
        message.qos = 1
        message.isRetained = true

        client.connect(mqttConnectOptions);
        topic.publish(message)
    }

}