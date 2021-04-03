package info.kfgodel.dyna

import info.kfgodel.dyna.api.exceptions.DynaWorldException
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.objects.TextMarquee
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.function.Supplier

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class TextMarqueeExampleTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }
      describe("a text marquee from an environment") {
        val marquee by let {
          environment().creator().create(TextMarquee::class.java)
        }
        describe("when a clock is available in the environment") {
          beforeEach {
            environment()
              .define<Clock>(
                Clock::class.java,
                Supplier { createFixedClock(2015, 10, 21, 20, 5) })
          }
          it("generates a text with the current time") {
            assertThat(marquee().displayText).isEqualTo("2015-10-21T20:05Z")
          }
        }
        itThrows(
          DynaWorldException::class.java, "when no clock is available in the environment",
          { marquee().displayText }
        ) { e: DynaWorldException? ->
          assertThat(e).hasMessage("There's no definition of class java.time.Clock in the environment")
        }

      }
    }


  }

  fun createFixedClock(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int): Clock {
    return Clock.fixed(
      LocalDateTime.of(year, month, dayOfMonth, hour, minute).toInstant(ZoneOffset.UTC),
      ZoneOffset.UTC.normalized()
    )
  }

}
