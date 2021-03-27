package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.TextMarquee;
import org.junit.runner.RunWith;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 03/05/19 - 23:55
 */
@RunWith(JavaSpecRunner.class)
public class TextMarqueeExampleTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("a text marquee from an environment", () -> {
      test().marquee(() -> test().environment().creator().create(TextMarquee.class));

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        describe("when a clock is available in the environment", () -> {
          beforeEach(() -> {
            test().environment()
              .define(Clock.class, () -> createFixedClock(2015, 10, 21, 20, 5));
          });

          it("generates a text with the current time", () -> {
            assertThat(test().marquee().getDisplayText()).isEqualTo("2015-10-21T20:05Z");
          });
        });

        itThrows(DynaWorldException.class, "when no clock is available in the environment", () -> {
          test().marquee().getDisplayText();
        }, e -> {
          assertThat(e).hasMessage("There's no definition of class java.time.Clock in the environment");
        });
      });

    });

  }

  public Clock createFixedClock(int year, int month, int dayOfMonth, int hour, int minute) {
    return Clock.fixed(
      LocalDateTime.of(year, month, dayOfMonth, hour, minute).toInstant(ZoneOffset.UTC),
      ZoneOffset.UTC.normalized()
    );
  }
}