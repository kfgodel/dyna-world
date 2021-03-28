package info.kfgodel.dyna.dependency;

import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.InterdependentTypeA;
import info.kfgodel.dyna.testobjects.InterdependentTypeB;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static info.kfgodel.function.MemoizedSupplier.memoized;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies that 2 objects can have cyclic dependencies between them and still work
 * Date: 04/05/19 - 20:06
 */
@RunWith(JavaSpecRunner.class)
public class CyclicDependencyTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("two objects that depend on each other", () -> {
      beforeEach(()->{
        test().environment().define(InterdependentTypeA.class, memoized(()->
          test().environment().creator().create(InterdependentTypeA.class)
        ));
        test().environment().define(InterdependentTypeB.class, memoized(()->
          test().environment().creator().create(InterdependentTypeB.class)
        ));
      });

      describe("given a default environment", () -> {
        test().environment(DefaultEnvironment::create);

        it("allows creating both objects",()->{
          InterdependentTypeA objectA = test().environment().provide(InterdependentTypeA.class);
          assertThat(objectA).isNotNull();

          InterdependentTypeB objectB = test().environment().provide(InterdependentTypeB.class);
          assertThat(objectB).isNotNull();
        });

        it("allows both objects to depend on each other ",()->{
          InterdependentTypeA objectA = test().environment().provide(InterdependentTypeA.class);
          InterdependentTypeB objectB = test().environment().provide(InterdependentTypeB.class);
          assertThat(objectA.getOther()).isSameAs(objectB);
          assertThat(objectB.getOther()).isSameAs(objectA);
        });
      });

    });

  }
}
