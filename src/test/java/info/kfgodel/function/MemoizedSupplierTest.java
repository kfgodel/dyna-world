package info.kfgodel.function;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This class verifies that the memorize supplier behaves as expected
 * Date: 04/05/19 - 18:30
 */
@RunWith(JavaSpecRunner.class)
public class MemoizedSupplierTest extends JavaSpec<FunctionTestContext> {
  @Override
  public void define() {
    describe("a memoized supplier", () -> {
      test().memoized(() -> MemoizedSupplier.create(() -> new AtomicInteger().incrementAndGet()));

      it("returns the first supplied value when called", () -> {
        Object firstValue = test().memoized().get();
        assertThat(firstValue).isEqualTo(1);
      });

      it("keeps returning the first value after the first call",()->{
        Object firstValue = test().memoized().get();
        Object secondValue = test().memoized().get();
        Object thirdValue = test().memoized().get();
        assertThat(secondValue).isEqualTo(1);
        assertThat(thirdValue).isEqualTo(1);
      });
    });

  }
}