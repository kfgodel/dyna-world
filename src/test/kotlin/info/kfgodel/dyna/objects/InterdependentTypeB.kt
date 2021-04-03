package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.environment.EnvironmentDependent

/**
 *
 * This type has a cyclic dependency with the [InterdependentTypeA] object to show how is that solved
 * Date: 04/05/19 - 20:04
 */
interface InterdependentTypeB : EnvironmentDependent {
  val name: String?
    get() = "B"
  val other: InterdependentTypeA?
    get() = environment().provide(InterdependentTypeA::class.java)
}
