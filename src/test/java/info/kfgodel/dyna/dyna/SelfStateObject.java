package info.kfgodel.dyna.dyna;

import info.kfgodel.dyna.api.DynaObject;

/**
 * This type serves as an example of an object accessing its own state
 * Date: 05/05/19 - 17:45
 */
public interface SelfStateObject extends DynaObject {

  String getName();
  void setName(String name);

  default int getPropertyCount(){
    return getInternalState().size();
  }
}
