package info.kfgodel.dyna.dependency

import com.google.common.base.Suppliers
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.testobjects.InterdependentTypeA
import info.kfgodel.dyna.testobjects.InterdependentTypeB
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class CyclicDependencyTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }
      describe("two objects that depend on each other") {
        beforeEach {
          environment().define(
            InterdependentTypeA::class.java, Suppliers.memoize(
              com.google.common.base.Supplier<InterdependentTypeA> {
                environment().creator().create(InterdependentTypeA::class.java)
              }
            ))
          environment().define(
            InterdependentTypeB::class.java, Suppliers.memoize(
              com.google.common.base.Supplier<InterdependentTypeB> {
                environment().creator().create(InterdependentTypeB::class.java)
              }
            ))
        }
        it("allows creating both objects") {
          val objectA: InterdependentTypeA = environment().provide(InterdependentTypeA::class.java)
          Assertions.assertThat(objectA).isNotNull
          val objectB: InterdependentTypeB = environment().provide(InterdependentTypeB::class.java)
          Assertions.assertThat(objectB).isNotNull()
        }
        it("allows both objects to depend on each other ") {
          val objectA: InterdependentTypeA = environment().provide(InterdependentTypeA::class.java)
          val objectB: InterdependentTypeB = environment().provide(InterdependentTypeB::class.java)
          Assertions.assertThat(objectA.other).isSameAs(objectB)
          Assertions.assertThat(objectB.other).isSameAs(objectA)
        }
      }
    }
  }
}
