package info.kfgodel.dyna.creator;

import com.google.common.collect.ImmutableMap;
import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.environment.EnvironmentDependent;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 04/05/19 - 13:12
 */
@RunWith(JavaSpecRunner.class)
public class ObjectCreatorTest extends JavaSpec<WorldTestContext> {

  @Override
  public void define() {
    describe("an environment object creator", () -> {
      test().creator(()-> test().environment().creator());

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        it("creates a dyna type object from an interface",()->{
          SimpleTestObject object = test().creator().create(SimpleTestObject.class);
          assertThat(object).isNotNull();

          object.setValue("23");
          assertThat(object.getValue()).isEqualTo("23");
        });

        it("makes all created objects implement DynaObject interface",()->{
          SimpleTestObject created = test().creator().create(SimpleTestObject.class);
          assertThat(created).isInstanceOf(DynaObject.class);
        });

        it("gives dyna objects a unique id of 36 characters",()->{
          DynaObject created = (DynaObject) test().creator().create(SimpleTestObject.class);
          assertThat(created.getObjectId()).hasSize(36);
        });

        it("keeps existing id if defined in the initial state",()->{
          Map<String, Object> initialState = ImmutableMap.of(DynaObject.OBJECT_ID_PROPERTY,"an id");
          DynaObject created = (DynaObject) test().creator().create(SimpleTestObject.class, initialState);
          assertThat(created.getObjectId()).isEqualTo("an id");
        });

        it("gives dyna objects access to their internal state",()->{
          DynaObject created = (DynaObject) test().creator().create(SimpleTestObject.class);
          assertThat(created.getInternalState()).containsKeys(DynaObject.OBJECT_ID_PROPERTY);
        });

        it("allows created objects to depend on the environment",()->{
          EnvironmentDependent object = test().creator().create(EnvironmentDependent.class);
          assertThat(object.environment()).isSameAs(test().environment());
        });

      });

    });

  }
}
