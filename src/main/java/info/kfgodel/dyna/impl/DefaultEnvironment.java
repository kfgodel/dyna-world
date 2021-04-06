package info.kfgodel.dyna.impl;

import com.google.common.base.Suppliers;
import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.api.repo.StateRepository;
import info.kfgodel.dyna.api.repo.TypePrism;
import info.kfgodel.dyna.impl.creator.DynaObjectCreator;
import info.kfgodel.dyna.impl.creator.ProtoCreator;
import info.kfgodel.dyna.impl.creator.handlers.EnvironmentAccessHandler;
import info.kfgodel.dyna.impl.creator.handlers.InternalStateMethodHandler;
import info.kfgodel.dyna.impl.creator.handlers.StateBasedEqualsMethodHandler;
import info.kfgodel.dyna.impl.creator.handlers.StateBasedHashcodeMethodHandler;
import info.kfgodel.dyna.impl.instantiator.DefaultConfiguration;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.impl.proxy.handlers.EqualsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.GetterPropertyHandler;
import info.kfgodel.dyna.impl.proxy.handlers.HashcodeMethodHandler;
import info.kfgodel.dyna.impl.repo.DefaultRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


/**
 * This class represents the default environment in which objects exists and collaborate between each other
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
    this.define(DynaTypeInstantiator.class, Suppliers.memoize(this::createInstantiator));
    this.define(ObjectCreator.class, Suppliers.memoize(() ->
      // Called only once
      ProtoCreator.from(this).create(DynaObjectCreator.class)
    ));
    this.define(StateRepository.class, Suppliers.memoize(DefaultRepository::create));
    this.define(TypePrism.class, Suppliers.memoize(()->
      // Create it as any normal object
      this.provide(ObjectCreator.class).create(TypePrism.class)
    ));
  }

  private DynaTypeInstantiator createInstantiator() {
    DefaultConfiguration configuration = DefaultConfiguration.create()
      .withInterface(DynaObject.class)
      .addBefore(GetterPropertyHandler.class, InternalStateMethodHandler.create())
      .addBefore(GetterPropertyHandler.class, EnvironmentAccessHandler.create(()-> this))
      .addInsteadOf(EqualsMethodHandler.class, StateBasedEqualsMethodHandler.create())
      .addInsteadOf(HashcodeMethodHandler.class, StateBasedHashcodeMethodHandler.create());
    return DynaTypeInstantiator.create(configuration);
  }


  public static DefaultEnvironment create() {
    DefaultEnvironment environment = new DefaultEnvironment();
    environment.recipes = new HashMap<>();
    environment.initialize();
    return environment;
  }

}
