package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.DynaObject
import java.util.function.Supplier

/**
 * This type serves as an example of an object accessing its own state
 * Date: 05/05/19 - 17:45
 */
interface SelfStateObject : DynaObject {
  var name: String?

  fun getPropertyCount(): Int {
    return internalState.size
  }

  fun changeGetNameDefinition() {
    internalState["getName"] = Supplier { "a hardcoded name" } as Supplier<*>
  }
}
