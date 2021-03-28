package info.kfgodel.dyna.dyna;

import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 06/05/19 - 21:52
 */
@RunWith(JavaSpecRunner.class)
public class StateRepositoryTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("a state repository", () -> {
      test().repository(() -> test().environment().repository());

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        it("starts without any state", () -> {
          assertThat(test().repository().getStates()).isEmpty();
        });

        it("collects the creator state as the first state when it's accessed for the first time",()->{
          ObjectCreator creator = test().environment().creator();// Forces the creation of the creator
          assertThat(test().repository().getStates()).hasSize(1);
          assertThat(test().repository().getStates()).containsOnlyOnce(creator.getInternalState());
        });

        it("collects the state of newly created objects",()->{
          SimpleTestObject first = test().environment().creator().create(SimpleTestObject.class);
          first.setValue("1");
          SimpleTestObject second = test().environment().creator().create(SimpleTestObject.class);
          second.setValue("2");

          assertThat(test().repository().getStates()).hasSize(3);
          assertThat(test().repository().getStates()).containsOnlyOnce(first.getInternalState());
          assertThat(test().repository().getStates()).containsOnlyOnce(second.getInternalState());
        });

        it("doesn't duplicate the state when shared between objects",()->{
          DynaObject first = test().environment().creator().create(DynaObject.class);
          DynaObject secondWithShared = test().environment().creator().create(DynaObject.class, first.getInternalState());

          assertThat(test().repository().getStates()).hasSize(2);
          assertThat(test().repository().getStates()).containsOnlyOnce(secondWithShared.getInternalState());
        });

      });

    });

  }

}
