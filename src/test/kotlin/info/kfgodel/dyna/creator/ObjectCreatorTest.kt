package info.kfgodel.dyna.creator

import com.google.common.collect.ImmutableMap
import info.kfgodel.dyna.api.DynaObject
import info.kfgodel.dyna.api.environment.EnvironmentDependent
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.testobjects.SimpleTestObject
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class ObjectCreatorTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }

      describe("an environment object creator") {
        val creator by let { environment().creator() }

        it("creates a dyna type object from an interface") {
          val simpleObject: SimpleTestObject = creator().create(SimpleTestObject::class.java)
          Assertions.assertThat(simpleObject).isNotNull
          simpleObject.value = "23"
          Assertions.assertThat(simpleObject.value).isEqualTo("23")
        }
        it("makes all created objects implement DynaObject interface") {
          val created: SimpleTestObject = creator().create(SimpleTestObject::class.java)
          Assertions.assertThat(created).isInstanceOf(DynaObject::class.java)
        }
        it("gives dyna objects a unique id of 36 characters") {
          val created = creator().create(SimpleTestObject::class.java) as DynaObject
          Assertions.assertThat(created.objectId).hasSize(36)
        }
        it("keeps existing id if defined in the initial state") {
          val initialState: Map<String, Any> =
            ImmutableMap.of<String, Any>(DynaObject.OBJECT_ID_PROPERTY, "an id")
          val created =
            creator().create(SimpleTestObject::class.java, initialState) as DynaObject
          Assertions.assertThat(created.objectId).isEqualTo("an id")
        }
        it("gives dyna objects access to their internal state") {
          val created = creator().create(SimpleTestObject::class.java) as DynaObject
          Assertions.assertThat(created.internalState)
            .containsKeys(DynaObject.OBJECT_ID_PROPERTY)
        }
        it("allows created objects to depend on the environment") {
          val `object`: EnvironmentDependent =
            creator().create<EnvironmentDependent>(EnvironmentDependent::class.java)
          Assertions.assertThat(`object`.environment())
            .isSameAs(environment())
        }
      }
    }
  }
}
