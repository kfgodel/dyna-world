package info.kfgodel.dyna.testobjects;

/**
 * This type represents one type view of an object for testing purposes
 * Date: 07/05/19 - 00:14
 */
public interface Superman extends GlassesWearer {

  default void putGlassesOn(){
    this.setHasGlasses(true);
  }
}
