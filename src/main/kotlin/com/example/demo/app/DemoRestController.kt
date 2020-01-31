package com.example.demo.app

import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import kotlin.reflect.full.memberProperties

@RestController
@RequestMapping("/configuration")
class DemoRestController(
    private val demoConfigurationProperties: DemoMutableConfigurationProperties,
    private val demoImmutableConfigurationProperties: DemoImmutableConfigurationProperties,
    private val configurationRepository: ConfigurationRepository
) {
    @GetMapping
    fun get(): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()
        DemoMutableConfigurationProperties::class.memberProperties.forEach {
            result["demo.mutable.${it.name}"] = it.get(demoConfigurationProperties)
        }
        DemoImmutableConfigurationProperties::class.memberProperties.forEach {
            result["demo.immutable.${it.name}"] = it.get(demoImmutableConfigurationProperties)
        }
        return result.toMap()
    }

    @PostMapping
    @Transactional
    fun set(@RequestParam key: String, @RequestParam value: String) {
        val existingValues = configurationRepository.findByApplicationAndProfileAndLabelAndKey(
            APPLICATION,
            PROFILE,
            LABEL,
            key
        )
        if (existingValues.size > 1) {
            throw IllegalStateException("Too many values found")
        }
        val configurationEntity: ConfigurationEntity
        configurationEntity = if (existingValues.isEmpty()) {
            ConfigurationEntity(
                application = APPLICATION,
                profile = PROFILE,
                label = LABEL,
                key = key,
                value = value
            )
        } else {
            existingValues.first().copy(
                value = value
            )
        }
        configurationRepository.save(configurationEntity)
    }

    companion object {
        const val APPLICATION = "demo"
        const val PROFILE = "default"
        const val LABEL = "master"
    }
}