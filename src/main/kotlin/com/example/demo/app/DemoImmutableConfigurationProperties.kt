package com.example.demo.app

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("demo.immutable")
@ConstructorBinding
data class DemoImmutableConfigurationProperties(
    val value: String?
)
