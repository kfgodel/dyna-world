package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.environment.EnvironmentDependent
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * This type is an example of dyna object that has private methods (not exposed to other objects)
 * and has an external dependency on a clock, provided by the environment.<br></br>
 * It intends to be a marquee that has the wall clock time as text
 * Date: 03/05/19 - 23:23
 */
abstract class TextMarquee : EnvironmentDependent {
  /**
   * Returns the current wall clock time as text
   */
  val displayText: String
    get() {
      val providedClock = environment().provide(Clock::class.java)
      val currentDatetime = convertToDateTime(providedClock.millis())
      return currentDatetime.toString()
    }

  private fun convertToDateTime(millis: Long): ZonedDateTime {
    return Instant.ofEpochMilli(millis)
      .atZone(ZoneOffset.UTC.normalized())
  }
}
