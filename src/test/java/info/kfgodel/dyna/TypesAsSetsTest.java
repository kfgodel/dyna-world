package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.ClarkKent;
import info.kfgodel.dyna.testobjects.GlassWearer;
import info.kfgodel.dyna.testobjects.Superman;
import org.assertj.core.util.Maps;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies that sets of objects can be defined with a type interpretation
 * Date: 07/05/19 - 00:01
 */
@RunWith(JavaSpecRunner.class)
public class TypesAsSetsTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("an environment", () -> {
      test().environment(DefaultEnvironment::create);

      describe("with a repo", () -> {
        test().repository(()-> test().environment().repository());

        describe("containing at least an object state", () -> {
          test().clark(()-> test().environment().creator().create(ClarkKent.class, Maps.newHashMap(GlassWearer.HAS_GLASSES_PROPERTY, true)));

          it("allows projecting a type as a subset",()->{
            Optional<Superman> firstResultForSupes = findSupermanInEnvironment();
            assertThat(firstResultForSupes).isEmpty();

            test().clark().takeGlassesOff();

            Optional<Superman> secondResultForSupes = findSupermanInEnvironment();
            assertThat(secondResultForSupes).isNotEmpty();
          });

        });
      });
    });
  }

  public Optional<Superman> findSupermanInEnvironment() {
    Predicate<Map<String,Object>> conditionForBeingSuperman = (state) -> {
      Object value = state.get(GlassWearer.HAS_GLASSES_PROPERTY);
      return value != null && !(Boolean) value;
    };

    return test().repository().getStates()
      .filter(conditionForBeingSuperman)
      .map(state -> test().environment().creator().create(Superman.class, state))
      .findFirst();
  }
}