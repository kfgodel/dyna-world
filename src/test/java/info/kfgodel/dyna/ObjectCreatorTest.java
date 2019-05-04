package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.api.EnvironmentDependent;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import org.junit.runner.RunWith;

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

        it("allows object to depend on the environment",()->{
          EnvironmentDependent object = test().creator().create(EnvironmentDependent.class);
          assertThat(object.getEnvironment()).isSameAs(test().environment());
        });
      });

    });

  }
}