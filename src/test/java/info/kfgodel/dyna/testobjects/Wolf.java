package info.kfgodel.dyna.testobjects;

import info.kfgodel.dyna.api.DynaObject;

/**
 * This is a test class for metamorph object test
 *
 * Date: 05/05/19 - 19:28
 */
public interface Wolf extends DynaObject {

  String getName();
  int getHeight();

  default void receiveSilverBullet(){
    this.getInternalState().put("dead", true);
  }

  default int getJumpHeight() {
    return getHeight() * 2;
  }

}
