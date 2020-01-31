package com.example.demo.app

import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConfigurationRefreshTest(
    private val testRestTemplate: TestRestTemplate
) : FreeSpec() {

    @LocalServerPort
    private var port = 0

    init {
        "ConfigurationProperties should update on context refresh" - {
            val url = "http://localhost:$port"
            val initialConfig = testRestTemplate.getForObject("$url/configuration", Map::class.java)
            "Mutable initial configuration should be 1" {
                initialConfig["demo.mutable.value"] shouldBe "1"
            }
            "Immutable initial configuration should be 1" {
                initialConfig["demo.immutable.value"] shouldBe "1"
            }
            testRestTemplate.postForLocation(
                "$url/configuration?key={key}&value={value}",
                null,
                mapOf("key" to "demo.mutable.value", "value" to "10")
            )
            testRestTemplate.postForLocation(
                "$url/configuration?key={key}&value={value}",
                null,
                mapOf("key" to "demo.immutable.value", "value" to "10")
            )
            val refreshedKeys =
                testRestTemplate.postForObject("$url/actuator/refresh", null, Array<String>::class.java)
            "Trigger context refresh with actuator and changed properties should be demo.mutable.value and demo.immutable.value" {
                refreshedKeys shouldContainExactlyInAnyOrder arrayOf("demo.mutable.value", "demo.immutable.value")
            }
            val refreshedConfig = testRestTemplate.getForObject("$url/configuration", Map::class.java)
            "Mutable refreshed configuration should be 10" {
                refreshedConfig["demo.mutable.value"] shouldBe "10"
            }
            "Immutable refreshed configuration should be 10" {
                refreshedConfig["demo.immutable.value"] shouldBe "10"
            }
        }
    }
}