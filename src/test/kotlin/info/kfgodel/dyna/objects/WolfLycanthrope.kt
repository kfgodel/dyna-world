package info.kfgodel.dyna.objects

import info.kfgodel.dyna.api.DynaObject

/**
 * This is a test class for metamorph object test
 *
 * Date: 05/05/19 - 19:28
 */
interface WolfLycanthrope : DynaObject {
  val name: String?
  val height: Int

  fun receiveSilverBullet() {
    this.internalState["dead"] = true
  }

  val jumpHeight: Int
    get() = height * 2
  val dead: Boolean
}
