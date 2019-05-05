package info.kfgodel.dyna.impl.creator.handlers;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.impl.proxy.handlers.HashcodeMethodHandlerSupport;

import java.util.Map;

/**
 * This class implements the hashcode method using the system hashcode of the object state
 * Date: 05/05/19 - 18:59
 */
public class StateBasedHashcodeMethodHandler extends HashcodeMethodHandlerSupport {

  @Override
  protected int calculateHashCodeFor(Object invokedProxy) {
    if (!(invokedProxy instanceof DynaObject)) {
      return super.calculateHashCodeFor(invokedProxy);
    }

    DynaObject objectDyna = (DynaObject) invokedProxy;
    Map<String, Object> objectState = objectDyna.getInternalState();
    return System.identityHashCode(objectState);
  }

  public static StateBasedHashcodeMethodHandler create() {
    StateBasedHashcodeMethodHandler handler = new StateBasedHashcodeMethodHandler();
    return handler;
  }

}
