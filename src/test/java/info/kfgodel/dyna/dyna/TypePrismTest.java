package info.kfgodel.dyna.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.ClarkKent;
import info.kfgodel.dyna.testobjects.GlassCarrier;
import info.kfgodel.dyna.testobjects.GlassesWearer;
import info.kfgodel.dyna.testobjects.Superman;
import org.assertj.core.util.Maps;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 09/05/19 - 23:48
 */
@RunWith(JavaSpecRunner.class)
public class TypePrismTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("a type prism", () -> {
      test().prism(() -> test().environment().prism());

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        describe("when a type is defined", () -> {
          beforeEach(() -> {
            test().prism().defineAs(ClarkKent.class, this::hasGlasses);
          });

          it("returns an empty stream if no object matches the definition", () -> {
            Stream<ClarkKent> instances = test().prism().getInstancesOf(ClarkKent.class);
            assertThat(instances).isEmpty();
          });

          itThrows(DynaWorldException.class, "if an undefined type is asked", () -> {
            test().prism().getInstancesOf(Superman.class);
          }, e -> {
            assertThat(e).hasMessage("Type[interface info.kfgodel.dyna.testobjects.Superman] has no predicate to define which objects are included");
          });

          describe("and a matching object exists", () -> {
            test().clark(() -> test().environment().creator().create(ClarkKent.class, Maps.newHashMap(GlassesWearer.HAS_GLASSES_PROPERTY, true)));
            beforeEach(() -> {
              //We force the creation of clark
              test().clark();
            });

            it("returns a stream with the object", () -> {
              Stream<ClarkKent> instances = test().prism().getInstancesOf(ClarkKent.class);
              assertThat(instances).hasSize(1);
            });

            it("doesn't return the object if it mutates to non matching", () -> {
              test().clark().takeGlassesOff();
              Stream<ClarkKent> instances = test().prism().getInstancesOf(ClarkKent.class);
              assertThat(instances).isEmpty();
            });

            it("allows having arbitrary type interpretations for a single object",()->{
              test().prism().defineAs(GlassCarrier.class, this::couldHaveGlasses);
              Stream<GlassCarrier> instances = test().prism().getInstancesOf(GlassCarrier.class);
              assertThat(instances).hasSize(1);
            });

            it("allows an object to change its type by changing its state",()->{
              test().prism().defineAs(Superman.class, this::hasNoGlasses);
              assertThat(test().prism().getInstancesOf(Superman.class)).isEmpty();

              test().clark().takeGlassesOff();
              assertThat(test().prism().getInstancesOf(Superman.class)).hasSize(1);
            });
          });

        });
      });
    });
  }

  private boolean hasNoGlasses(Map<String, Object> state) {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY) && !(Boolean) state.get(GlassesWearer.HAS_GLASSES_PROPERTY);
  }

  private boolean couldHaveGlasses(Map<String, Object> state) {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY);
  }

  public boolean hasGlasses(Map<String, Object> state) {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY) && (Boolean) state.get(GlassesWearer.HAS_GLASSES_PROPERTY);
  }
}