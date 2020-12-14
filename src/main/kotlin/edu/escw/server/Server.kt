package edu.escw.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class Server

fun main(args: Array<String>) {
	runApplication<Server>(*args)
}
