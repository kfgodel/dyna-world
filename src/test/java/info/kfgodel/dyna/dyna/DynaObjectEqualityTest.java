package info.kfgodel.dyna.dyna;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import info.kfgodel.dyna.WorldTestContext;
import info.kfgodel.dyna.impl.DefaultEnvironment;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JavaSpecRunner.class)
public class DynaObjectEqualityTest extends JavaSpec<WorldTestContext> {

  @Override
  public void define() {
    describe("a dyna object", () -> {
      test().simpleObject(() -> test().environment().creator().create(SimpleTestObject.class, test().initialState()));
      test().initialState(() -> Maps.newHashMap(
        ImmutableMap.<String, Object>builder()
          .put("value", "Hello")
          .build()
      ));
      test().environment(DefaultEnvironment::create);


      describe("when compared to other object", () -> {
        test().otherObject(() -> test().environment().creator().create(SimpleTestObject.class, test().otherInitialState()));

        describe("by default", () -> {

          describe("if the other object has the same state", () -> {
            test().otherInitialState(()-> test().initialState());

            it("is equal to the other object", () -> {
              assertThat(test().simpleObject()).isEqualTo(test().otherObject());
            });

            it("has the same hashcode as the other object", () -> {
              assertThat(test().simpleObject().hashCode()).isEqualTo(test().otherObject().hashCode());
            });
          });

          describe("if the other object has its own state (whether equal or not)", () -> {
            test().otherInitialState(()-> Maps.newHashMap(
              ImmutableMap.<String, Object>builder()
                .put("value", "Hello")
                .build()
            ));

            it("is not equal to the other object", () -> {
              assertThat(test().simpleObject()).isNotEqualTo(test().otherObject());
            });

            it("may have different hashcode ", () -> {
              // Because we use system hashcode we are pretty sure they are different, but this test may fail
              // randomly if two state instances happen to have the same system hashcode (possible but very rare)
              assertThat(test().simpleObject().hashCode()).isNotEqualTo(test().otherObject().hashCode());
            });

          });

        });

        describe("when equals and hashcode are redefined", () -> {
          context().initialState(() -> Maps.newHashMap(
            ImmutableMap.<String, Object>builder()
              .put("equals", (Function) (other) -> true)
              .put("hashCode", (Supplier) () -> 3)
              .build()
          ));

          it("can be equal to anything", () -> {
            assertThat(test().simpleObject()).isEqualTo("an object");
          });

          it("can have an arbitrary hashcode",()->{
            assertThat(test().simpleObject().hashCode()).isEqualTo(3);
          });
        });

      });
    });
  }
}
