package com.example.demo.app

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Repository
interface ConfigurationRepository : JpaRepository<ConfigurationEntity, UUID> {
    fun findByApplicationAndProfileAndLabelAndKey(
        application: String,
        profile: String,
        label: String,
        key: String?
    ): List<ConfigurationEntity>
}

@Entity
@Table(name = "PROPERTIES")
data class ConfigurationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val application: String,
    val profile: String,
    val label: String,
    val key: String,
    val value: String
) : Serializable