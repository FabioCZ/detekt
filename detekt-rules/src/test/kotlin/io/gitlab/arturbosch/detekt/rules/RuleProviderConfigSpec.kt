package io.gitlab.arturbosch.detekt.rules

import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.internal.DefaultRuleSetProvider
import io.gitlab.arturbosch.detekt.test.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.reflections.Reflections

class RuleProviderConfigSpec {

    @Nested
    inner class `RuleProvider config test` {

        @Test
        fun `should test if the config has been passed to all rules`() {
            val config = TestConfig()
            val reflections = Reflections("io.gitlab.arturbosch.detekt.rules")
            val providers = reflections.getSubTypesOf(DefaultRuleSetProvider::class.java)

            providers.forEach {
                val provider = it.getDeclaredConstructor().newInstance()
                val ruleSet = provider.instance(config)
                ruleSet.rules.forEach { baseRule ->
                    val rule = baseRule as? Rule
                    if (rule != null) {
                        assertThat(rule.ruleSetConfig)
                            .withFailMessage("No config was passed to ${rule.javaClass.name}")
                            .isEqualTo(config)
                    }
                }
            }
        }
    }
}
