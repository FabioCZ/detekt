package io.gitlab.arturbosch.detekt.core.reporting.console

import io.gitlab.arturbosch.detekt.api.internal.SimpleNotification
import io.gitlab.arturbosch.detekt.core.NL
import io.gitlab.arturbosch.detekt.test.TestDetektion
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NotificationReportSpec {

    private val subject = NotificationReport()

    @Nested
    inner class `notification report` {

        @Test
        fun `reports two notifications`() {
            val detektion = object : TestDetektion() {
                override val notifications = listOf(SimpleNotification("test"), SimpleNotification("test"))
            }
            assertThat(subject.render(detektion)).isEqualTo("test${NL}test")
        }

        @Test
        fun `reports no findings`() {
            val detektion = TestDetektion()
            assertThat(subject.render(detektion)).isNull()
        }
    }
}
