package toyproject.jiratonotion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JiraToNotionApplication

fun main(args: Array<String>) {
    runApplication<JiraToNotionApplication>(*args)
}
