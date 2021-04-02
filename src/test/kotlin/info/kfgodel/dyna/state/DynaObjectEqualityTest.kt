package info.kfgodel.dyna.state

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.testobjects.SimpleTestObject
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith
import java.util.function.Function
import java.util.function.Supplier

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class DynaObjectEqualityTest : KotlinSpec() {
  override fun define() {
    describe("a dyna object") {
      val environment by let { DefaultEnvironment.create() }
      val initialState by let {
        Maps.newHashMap(
          ImmutableMap.builder<String, Any>()
            .put("value", "Hello")
            .build()
        )
      }
      val simpleObject by let {
        environment().creator().create(
          SimpleTestObject::class.java, initialState()
        )
      }
      describe("when compared to other object") {
        val otherInitialState by let<Map<String,Any>>()
        val otherObject by let {
          environment().creator().create(
            SimpleTestObject::class.java, otherInitialState()
          )
        }
        describe("by default") {
          describe("if the other object has the same state") {
            otherInitialState { initialState() }
            it("is equal to the other object") {
              Assertions.assertThat<SimpleTestObject>(simpleObject()).isEqualTo(otherObject())
            }
            it(
              "has the same hashcode as the other object"
            ) {
              Assertions.assertThat(simpleObject().hashCode()).isEqualTo(otherObject().hashCode())
            }
          }
          describe("if the other object has its own state (whether equal or not)") {
            otherInitialState {
              Maps.newHashMap(
                ImmutableMap.builder<String, Any>()
                  .put("value", "Hello")
                  .build()
              )
            }
            it("is not equal to the other object") {
              Assertions.assertThat<SimpleTestObject>(simpleObject()).isNotEqualTo(otherObject())
            }
            it("may have different hashcode ") {
              // Because we use system hashcode we are pretty sure they are different, but this test may fail
              // randomly if two state instances happen to have the same system hashcode (possible but very rare)
              Assertions.assertThat(simpleObject().hashCode()).isNotEqualTo(otherObject().hashCode())
            }
          }
        }
        describe("when equals and hashcode are redefined") {
          initialState {
            Maps.newHashMap(
              ImmutableMap.builder<String, Any>()
                .put(
                  "equals",
                  Function { other: Any? -> true } as Function<*, *>)
                .put("hashCode", Supplier { 3 } as Supplier<*>)
                .build()
            )
          }
          it("can be equal to anything") {
            Assertions.assertThat<SimpleTestObject>(simpleObject()).isEqualTo("an object")
          }
          it(
            "can have an arbitrary hashcode"
          ) { Assertions.assertThat(simpleObject().hashCode()).isEqualTo(3) }
        }
      }
    }

  }
}
