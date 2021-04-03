package info.kfgodel.dyna.objects

/**
 * This type represents one type view of an object for testing purposes
 * Date: 07/05/19 - 00:09
 */
interface ClarkKent : GlassesWearer {
  fun takeGlassesOff() {
    this.hasGlasses = false
  }
}
