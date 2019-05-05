package info.kfgodel.dyna.testobjects;

import info.kfgodel.dyna.api.Metamorph;

/**
 * This is a test class for metamorph object test
 * <p>
 * Date: 05/05/19 - 19:24
 */
public interface Lycanthrope extends Metamorph {

  String getName();
  int getHeight();

  boolean getDead();

  default int getJumpHeight() {
    return (int) (getHeight() * 0.5);
  }


}
