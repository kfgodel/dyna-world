package info.kfgodel.dyna.impl;

import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.api.repo.ObjectRepository;
import info.kfgodel.dyna.impl.creator.DynaObjectCreator;
import info.kfgodel.dyna.impl.creator.ProtoCreator;
import info.kfgodel.dyna.impl.repo.DefaultRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static info.kfgodel.function.MemoizedSupplier.memoized;

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
    Supplier recipe = recipes.get(expectedType);
    if(recipe == null){
      throw new DynaWorldException("There's no definition of " + expectedType + " in the environment");
    }
    return (T) recipe.get();
  }

  @Override
  public <T> void define(Class<T> providedType, Supplier<? extends T> definition) {
    recipes.put(providedType, definition);
  }

  private void initialize() {
    this.define(ObjectRepository.class, memoized(DefaultRepository::create));
    this.define(ObjectCreator.class, memoized(()->
      // Called only once
      ProtoCreator.from(this).create(DynaObjectCreator.class)
    ));
  }

  public static DefaultEnvironment create() {
    DefaultEnvironment environment = new DefaultEnvironment();
    environment.recipes = new HashMap<>();
    environment.initialize();
    return environment;
  }

}
