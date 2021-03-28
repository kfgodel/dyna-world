package info.kfgodel.dyna.state

import info.kfgodel.dyna.api.exceptions.DynaWorldException
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.testobjects.ClarkKent
import info.kfgodel.dyna.testobjects.GlassCarrier
import info.kfgodel.dyna.testobjects.GlassesWearer
import info.kfgodel.dyna.testobjects.Superman
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.assertj.core.util.Maps
import org.junit.runner.RunWith
import java.util.function.Predicate
import java.util.stream.Stream

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class TypePrismTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }
      describe("a type prism") {
        val prism by let { environment().prism() }

        itThrows(
          DynaWorldException::class.java, "if an undefined type is asked",
          {
            prism().getInstancesOf(Superman::class.java)
          }
        ) { e: DynaWorldException? ->
          Assertions.assertThat(e)
            .hasMessage("Type[interface info.kfgodel.dyna.testobjects.Superman] has no predicate to define which objects are included")
        }

        describe("when a type is defined") {
          beforeEach {
            prism().defineAs(
              ClarkKent::class.java,
              Predicate { state: Map<String?, Any?>? ->
                hasGlasses(
                  state!!
                )
              })
          }

          it("returns an empty stream if no object matches the definition") {
            val instances: Stream<ClarkKent> = prism().getInstancesOf(ClarkKent::class.java)
            Assertions.assertThat(instances).isEmpty()
          }

          describe("and a matching object exists") {
            val clark by let {
              environment().creator().create(
                ClarkKent::class.java,
                Maps.newHashMap<String, Any>(
                  GlassesWearer.HAS_GLASSES_PROPERTY,
                  true
                )
              )
            }
            beforeEach {
              //We force the creation of clark
              clark()
            }

            it("returns a stream with the object") {
              val instances: Stream<ClarkKent> = prism().getInstancesOf(ClarkKent::class.java)
              Assertions.assertThat(instances).hasSize(1)
            }
            it("doesn't return the object if it mutates to non matching") {
              clark().takeGlassesOff()
              val instances: Stream<ClarkKent> = prism().getInstancesOf(ClarkKent::class.java)
              Assertions.assertThat(instances).isEmpty()
            }
            it("allows having arbitrary type interpretations for a single object") {
              prism().defineAs(
                GlassCarrier::class.java,
                Predicate { state: Map<String?, Any?>? ->
                  couldHaveGlasses(state!!)
                })
              val instances: Stream<GlassCarrier> = prism().getInstancesOf(GlassCarrier::class.java)
              Assertions.assertThat(instances).hasSize(1)
            }
            it("allows an object to change its type by changing its state") {
              prism().defineAs(
                Superman::class.java,
                Predicate { state: Map<String?, Any?>? ->
                  hasNoGlasses(state!!)
                })
              Assertions.assertThat<Superman>(prism().getInstancesOf(Superman::class.java)).isEmpty()
              clark().takeGlassesOff()
              Assertions.assertThat<Superman>(prism().getInstancesOf(Superman::class.java)).hasSize(1)
            }
          }
        }

      }
    }
  }

  private fun hasNoGlasses(state: Map<String?, Any?>): Boolean {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY) && !(state[GlassesWearer.HAS_GLASSES_PROPERTY] as Boolean)
  }

  private fun couldHaveGlasses(state: Map<String?, Any?>): Boolean {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY)
  }

  fun hasGlasses(state: Map<String?, Any?>): Boolean {
    return state.containsKey(GlassesWearer.HAS_GLASSES_PROPERTY) && (state[GlassesWearer.HAS_GLASSES_PROPERTY] as Boolean?)!!
  }

}
