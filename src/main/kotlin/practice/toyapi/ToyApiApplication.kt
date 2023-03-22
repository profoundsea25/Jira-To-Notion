package practice.toyapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToyApiApplication

fun main(args: Array<String>) {
    runApplication<ToyApiApplication>(*args)
}
