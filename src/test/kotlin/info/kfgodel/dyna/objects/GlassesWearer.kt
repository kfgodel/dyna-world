package info.kfgodel.dyna.objects

/**
 * This interface is created for testing superman / kent objects without repeating code
 * Date: 07/05/19 - 00:11
 */
interface GlassesWearer {
  var hasGlasses: Boolean

  companion object {
    const val HAS_GLASSES_PROPERTY = "hasGlasses"
  }
}
