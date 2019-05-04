package info.kfgodel.dyna.impl;

import info.kfgodel.dyna.api.Environment;
import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.creator.DynaObjectCreator;
import info.kfgodel.dyna.impl.creator.ProtoCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This class represents the default environment in which objects exists and colaborate between each other
 * inside the same VM
 *
 * Date: 04/05/19 - 00:03
 */
public class DefaultEnvironment implements Environment {


  private Map<Class, Supplier> recipes;

  @Override
  public <T> T provide(Class<T> expectedType) throws DynaWorldException {
    Supplier supplier = recipes.get(expectedType);
    if(supplier == null){
      throw new DynaWorldException("There's no definition of " + expectedType + " in the environment");
    }
    return (T) supplier.get();
  }

  @Override
  public <T> void define(Class<T> providedType, Supplier<? extends T> definition) {
    recipes.put(providedType, definition);
  }

  @Override
  public ObjectCreator creator() {
    return this.provide(ObjectCreator.class);
  }

  public static DefaultEnvironment create() {
    DefaultEnvironment environment = new DefaultEnvironment();
    environment.recipes = new HashMap<>();
    environment.initialize();
    return environment;
  }

  private void initialize() {
    this.define(ObjectCreator.class, ()->
      ProtoCreator.from(this).create(DynaObjectCreator.class)
    );
  }

}
