package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.impl.creator.handlers.InternalStateMethodHandler;
import info.kfgodel.dyna.impl.instantiator.DefaultConfiguration;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import static info.kfgodel.function.MemoizedSupplier.memoized;

/**
 * This class is necessary to solve the paradox of who creates the creator.<br>
 * A proto creator is used to create the creator, and then it's discarded. In that way all objects, including the creator
 * itself are created following the same principles.<br>
 * The only object that is not created out of a creator is the environment which is a pre-requisite for the rest.<br>
 * <br>
 * Because we want the proto-creator and the real instance to behave as close as possible both share the hierarchy
 *
 * Date: 04/05/19 - 13:28
 */
public class ProtoCreator extends DynaObjectCreator {

  private Environment environment;

  @Override
  public Environment environment() {
    // Because the creator doesn't exists yet, an environment can't be provided
    return this.environment;
  }

  private void initializeDependencies() {
    // We want only one instantiator available for this and created instance
    environment().define(DynaTypeInstantiator.class, memoized(this::createInstantiator));
  }

  private DynaTypeInstantiator createInstantiator() {
    DefaultConfiguration configuration = DefaultConfiguration.create()
      .withInterface(DynaObject.class);
    // Before getter handlers
    configuration.getChainOfHandlers().add(0,InternalStateMethodHandler.create());
    return DynaTypeInstantiator.create(configuration);
  }

  public static ProtoCreator from(Environment environment) {
    ProtoCreator creator = new ProtoCreator();
    creator.environment = environment;
    creator.initializeDependencies();
    return creator;
  }
}
