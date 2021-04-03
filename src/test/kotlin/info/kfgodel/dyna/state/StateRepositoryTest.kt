package info.kfgodel.dyna.state

import info.kfgodel.dyna.api.DynaObject
import info.kfgodel.dyna.api.creator.ObjectCreator
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.objects.SimpleTestObject
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class StateRepositoryTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }

      describe("a state repository") {
        val repository by let { environment().repository() }

        it("starts without any state") {
          Assertions.assertThat<Map<String, Any>>(repository().getStates()).isEmpty()
        }
        it("collects the creator state as the first state when it's accessed for the first time") {
          val creator: ObjectCreator =
            environment().creator() // Forces the creation of the creator
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .hasSize(1)
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .containsOnlyOnce(creator.internalState)
        }
        it("collects the state of newly created objects") {
          val first: SimpleTestObject = environment().creator().create(SimpleTestObject::class.java)
          first.value = "1"
          val second: SimpleTestObject = environment().creator().create(SimpleTestObject::class.java)
          second.value = "2"
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .hasSize(3)
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .containsOnlyOnce(first.internalState)
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .containsOnlyOnce(second.internalState)
        }
        it("doesn't duplicate the state when shared between objects") {
          val first: DynaObject = environment().creator().create<DynaObject>(DynaObject::class.java)
          val secondWithShared: DynaObject =
            environment().creator().create<DynaObject>(DynaObject::class.java, first.internalState)
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .hasSize(2)
          Assertions.assertThat<Map<String, Any>>(repository().getStates())
            .containsOnlyOnce(secondWithShared.internalState)
        }

      }
    }
  }
}
