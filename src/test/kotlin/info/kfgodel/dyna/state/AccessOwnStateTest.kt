package info.kfgodel.dyna.state

import info.kfgodel.dyna.dyna.SelfStateObject
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class AccessOwnStateTest : KotlinSpec() {
  override fun define() {
    describe("a dyna object") {
      val objectWithState by let {DefaultEnvironment.create().creator().create(
        SelfStateObject::class.java
      )}

      it("can introspect its own state map") {
        Assertions.assertThat(objectWithState().getPropertyCount()).isEqualTo(1)
        objectWithState().name = "Pepe"
        Assertions.assertThat(objectWithState().getPropertyCount()).isEqualTo(2)
      }
      it("can change a method definition") {
        Assertions.assertThat(objectWithState().name).isNull()
        objectWithState().changeGetNameDefinition()
        Assertions.assertThat(objectWithState().name).isEqualTo("a hardcoded name")
      }
    }

  }
}
