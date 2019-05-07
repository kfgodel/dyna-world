package info.kfgodel.dyna.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 06/05/19 - 21:52
 */
@RunWith(JavaSpecRunner.class)
public class ObjectRepositoryTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("an object repository", () -> {
      test().repository(() -> test().environment().repository());

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        it("starts with only the creator state", () -> {
          assertThat(test().repository().getStates()).hasSize(1);
        });

        it("collects the state of newly created objects",()->{
          SimpleTestObject first = test().environment().creator().create(SimpleTestObject.class);
          first.setValue("1");
          SimpleTestObject second = test().environment().creator().create(SimpleTestObject.class);
          second.setValue("2");

          assertThat(test().repository().getStates()).hasSize(3);
          assertThat(test().repository().getStates()).element(1).hasFieldOrPropertyWithValue("value","1");
          assertThat(test().repository().getStates()).element(2).hasFieldOrPropertyWithValue("value","2");
        });

        it("doesn't duplicate the state when shared between objects",()->{
          DynaObject first = test().environment().creator().create(DynaObject.class);
          DynaObject sharedStateSecond = test().environment().creator().create(DynaObject.class, first.getInternalState());

          assertThat(test().repository().getStates()).hasSize(2);
        });

      });

    });

  }
}