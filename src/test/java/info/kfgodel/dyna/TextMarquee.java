package info.kfgodel.dyna;

import info.kfgodel.dyna.api.EnvironmentDependent;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * This type represents a text marquee used for tests that returns the clock time as string
 * Date: 03/05/19 - 23:23
 */
public interface TextMarquee extends EnvironmentDependent {

  /**
   * Currently display text with the wall clock time
   */
  default String getDisplayText(){
    Clock providedClock = environment().provide(Clock.class);
    ZonedDateTime currentDatetime = convertToDateTime(providedClock.millis());
    return currentDatetime.toString();
  }

  default ZonedDateTime convertToDateTime(long millis) {
    return Instant.ofEpochMilli(millis)
      .atZone(ZoneOffset.UTC.normalized());
  }
}
