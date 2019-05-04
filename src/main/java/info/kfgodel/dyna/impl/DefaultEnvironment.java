package info.kfgodel.dyna.impl;

import info.kfgodel.dyna.api.Environment;
import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;

import java.util.function.Supplier;

/**
 * This class represents the default environment in which objects exists and colaborate between each other
 * inside the same VM
 *
 * Date: 04/05/19 - 00:03
 */
public class DefaultEnvironment implements Environment {


  private DefaultCreator creator;

  @Override
  public <T> T provide(Class<T> expectedType) throws DynaWorldException {
    return null;
  }

  @Override
  public <T> void providedWith(Supplier<? extends T> definition, Class<T> providedType) {

  }

  @Override
  public ObjectCreator creator() {
    return creator;
  }

  public static DefaultEnvironment create() {
    DefaultEnvironment environment = new DefaultEnvironment();
    environment.creator = DefaultCreator.create(environment);
    return environment;
  }

}
