package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.Metamorph

/**
 * This is a test class for metamorph object test
 *
 *
 * Date: 05/05/19 - 19:24
 */
interface HumanLycanthrope : Metamorph {
  val name: String?
  val height: Int
  val dead: Boolean
  val jumpHeight: Int
    get() = (height * 0.5).toInt()
}
