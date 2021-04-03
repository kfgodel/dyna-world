package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.environment.EnvironmentDependent

/**
 *
 * This type has a cyclic dependency with the [InterdependentTypeB] object to show how is that solved
 * Date: 04/05/19 - 20:04
 */
interface InterdependentTypeA : EnvironmentDependent {
  val name: String?
    get() = "A"
  val other: InterdependentTypeB?
    get() = environment().provide(InterdependentTypeB::class.java)
}
