package info.kfgodel.dyna

import info.kfgodel.function.MemoizedSupplier
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicInteger

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class ReadmeExampleKotlinTest : KotlinSpec() {
  override fun define() {
    describe("a memoized supplier") {
      val memoized by let { MemoizedSupplier.create { AtomicInteger().incrementAndGet() } }

      it("returns the first supplied value when called") {
        val firstValue: Any = memoized().get()
        assertThat(firstValue).isEqualTo(1)
      }
      it("keeps returning the first value after the first call") {
        val firstValue: Any = memoized().get()
        val secondValue: Any = memoized().get()
        val thirdValue: Any = memoized().get()

        assertThat(secondValue).isSameAs(firstValue)
        assertThat(thirdValue).isSameAs(firstValue)
      }
    }
  }
}
