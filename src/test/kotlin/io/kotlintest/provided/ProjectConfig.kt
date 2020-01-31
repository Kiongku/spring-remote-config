package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.kotlintest.extensions.ProjectLevelExtension
import io.kotlintest.extensions.TestListener
import io.kotlintest.spring.SpringAutowireConstructorExtension
import io.kotlintest.spring.SpringListener

class ProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<ProjectLevelExtension> =
        listOf(SpringAutowireConstructorExtension)

    override fun listeners(): List<TestListener> =
        listOf(SpringListener)
}