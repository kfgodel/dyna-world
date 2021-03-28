package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.Lycanthrope;
import info.kfgodel.dyna.testobjects.Wolf;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the type conversion of an object
 * Date: 05/05/19 - 19:08
 */
@RunWith(JavaSpecRunner.class)
public class MetaMorphConversionTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("a metamorph object", () -> {
      test().metamorph(() -> test().environment().creator().create(Lycanthrope.class, test().initialState()));
      test().initialState(() -> Maps.newHashMap(
        ImmutableMap.<String, Object>builder()
          .put("name", "Scott Howard")
          .put("height", 163)
          .build()
      ));
      test().environment(DefaultEnvironment::create);

      describe("when becoming other type", () -> {
        test().morphed(() -> test().metamorph().become(Wolf.class));

        it("changes its type", () -> {
          assertThat(test().morphed()).isNotNull();
          assertThat(test().morphed()).isNotInstanceOf(Lycanthrope.class);
        });

        it("shares its state with the morphed object", () -> {
          assertThat(test().morphed().getName()).isEqualTo("Scott Howard");
          assertThat(test().metamorph().getInternalState()).isSameAs(test().morphed().getInternalState());
        });

        it("is affected by morphed object state changes", () -> {
          test().morphed().receiveSilverBullet();
          assertThat(test().morphed().getDead()).isTrue();
          assertThat(test().metamorph().getDead()).isTrue();
        });

        it("allows different behavior for the same state", () -> {
          assertThat(test().metamorph().getJumpHeight()).isEqualTo(81);
          assertThat(test().morphed().getJumpHeight()).isEqualTo(326);
        });

        it("is equal and has same hashcode as morphed object",()->{
          assertThat(test().metamorph()).isEqualTo(test().morphed());
          assertThat(test().metamorph().hashCode()).isEqualTo(test().morphed().hashCode());
        });

      });


    });

  }
}