package info.kfgodel.dyna.impl.creator.handlers;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.impl.proxy.handlers.EqualiltyMethodHandlerSupport;

import java.util.Map;

/**
 * This class implements the equals method based on the identity of the object state
 * Date: 05/05/19 - 18:43
 */
public class StateBasedEqualsMethodHandler extends EqualiltyMethodHandlerSupport {

  @Override
  protected boolean calculateEqualityBetween(Object object, Object other) {
    if(super.calculateEqualityBetween(object, other)){
      // Is the same object
      return true;
    }
    if(!(object instanceof DynaObject) || !(other instanceof DynaObject)){
      // We have no equlity definition for objects that are not DynaObject
      return false;
    }
    DynaObject thisDyna = (DynaObject) object;
    DynaObject otherDyna = (DynaObject) other;
    Map<String, Object> thisState = thisDyna.getInternalState();
    Map<String, Object> otherState = otherDyna.getInternalState();
    // Must be same state to be considered equals
    return thisState == otherState;
  }

  public static StateBasedEqualsMethodHandler create() {
    StateBasedEqualsMethodHandler handler = new StateBasedEqualsMethodHandler();
    return handler;
  }

}
