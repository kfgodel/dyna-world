package info.kfgodel.dyna.testobjects;

/**
 * This interface is created for testing superman / kent objects without repeating code
 * Date: 07/05/19 - 00:11
 */
public interface GlassesWearer {

  String HAS_GLASSES_PROPERTY= "hasGlasses";

  boolean getHasGlasses();
  void setHasGlasses(boolean newState);
}
