package info.kfgodel.dyna.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 05/05/19 - 17:44
 */
@RunWith(JavaSpecRunner.class)
public class AccessOwnStateTest extends JavaSpec<WorldTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      test().objectWithState(() -> DefaultEnvironment.create().creator().create(SelfStateObject.class));

      it("can introspect its own state map", () -> {
        assertThat(test().objectWithState().getPropertyCount()).isEqualTo(1);

        test().objectWithState().setName("Pepe");
        assertThat(test().objectWithState().getPropertyCount()).isEqualTo(2);
      });


      it("can change a method definition",()->{
        assertThat(test().objectWithState().getName()).isNull();

        test().objectWithState().changeGetNameDefinition();

        assertThat(test().objectWithState().getName()).isEqualTo("a hardcoded name");
      });
    });

  }
}