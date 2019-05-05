package info.kfgodel.dyna.impl.creator.handlers;

import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.function.Supplier;

/**
 * This type implements the method to access the object environment
 *
 * Date: 05/05/19 - 18:12
 */
public class EnvironmentAccessHandler implements DynaMethodInvocationHandler {

  public static final String ENVIRONMENT_METHOD_NAME = "environment";

  private Supplier<Environment> environmentSupplier;

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(ENVIRONMENT_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 0){
      Environment environment = environmentSupplier.get();
      return HandledResult.create(environment);
    }
    return UnhandledResult.instance();
  }

  public static EnvironmentAccessHandler create(Supplier<Environment> environmentSupplier) {
    EnvironmentAccessHandler handler = new EnvironmentAccessHandler();
    handler.environmentSupplier = environmentSupplier;
    return handler;
  }

}
