package info.kfgodel.dyna.impl.creator.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

/**
 * This class implements the method invocation to return the internal state of the object
 * Date: 05/05/19 - 17:32
 */
public class InternalStateMethodHandler implements DynaMethodInvocationHandler {

  public static final String GET_INTERNAL_STATE_METHOD_NAME = "getInternalState";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(GET_INTERNAL_STATE_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 0){
      return HandledResult.create(invocation.getDynaState());
    }
    return UnhandledResult.instance();
  }

  public static InternalStateMethodHandler create() {
    InternalStateMethodHandler handler = new InternalStateMethodHandler();
    return handler;
  }
}
