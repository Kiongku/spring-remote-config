package com.example.demo.app

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("demo.mutable")
data class DemoMutableConfigurationProperties(
    var value: String?
)
