package info.kfgodel.dyna.testobjects;

import info.kfgodel.dyna.api.DynaObject;

/**
 * Date: 04/05/19 - 13:18
 */
public interface SimpleTestObject extends DynaObject {

  String getValue();
  void setValue(String value);
}
