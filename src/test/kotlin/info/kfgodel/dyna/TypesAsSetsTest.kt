package info.kfgodel.dyna

import info.kfgodel.dyna.api.environment.Environment
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.testobjects.ClarkKent
import info.kfgodel.dyna.testobjects.GlassesWearer
import info.kfgodel.dyna.testobjects.Superman
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.util.Maps
import org.junit.runner.RunWith
import java.util.Optional
import java.util.function.Function
import java.util.function.Predicate

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class TypesAsSetsTest : KotlinSpec() {
  override fun define() {
    describe("an environment") {
      val environment by let { DefaultEnvironment.create() }

      describe("with a repo") {
        val repository by let { environment().repository() }

        describe("containing at least an object state") {
          val clark by let {
            environment().creator().create(
              ClarkKent::class.java,
              Maps.newHashMap<String, Any>(GlassesWearer.HAS_GLASSES_PROPERTY, true)
            )
          }

          it("allows projecting a type as a subset") {
            val firstResultForSupes: Optional<Superman> = findSupermanInEnvironment(environment())
            assertThat(firstResultForSupes).isEmpty
            clark().takeGlassesOff()
            val secondResultForSupes: Optional<Superman> = findSupermanInEnvironment(environment())
            assertThat(secondResultForSupes).isNotEmpty()
          }
        }
      }
    }
  }

  fun findSupermanInEnvironment(environment: Environment): Optional<Superman> {
    val conditionForBeingSuperman =
      Predicate { state: Map<String?, Any?> ->
        val value = state[GlassesWearer.HAS_GLASSES_PROPERTY]
        value != null && !(value as Boolean)
      }
    return environment.repository().getStates()
      .filter(conditionForBeingSuperman)
      .map(Function<Map<String?, Any?>, Superman> { state: Map<String?, Any?>? ->
        environment.creator().create(
          Superman::class.java, state
        )
      })
      .findFirst()
  }
}
