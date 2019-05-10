package info.kfgodel.dyna.testobjects;

/**
 * This type represents one type view of an object for testing purposes
 * Date: 07/05/19 - 00:09
 */
public interface ClarkKent extends GlassesWearer {

  default void takeGlassesOff(){
    this.setHasGlasses(false);
  }
}
