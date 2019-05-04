package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.Environment;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

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
public class ProtoCreator implements DynaObjectCreator {

  private Environment environment;

  @Override
  public Environment getEnvironment() {
    // Because the cretor doesn't exists yet, an environment can't be provided
    return this.environment;
  }

  private void initializarDependencies() {
    // It's a dependency that this instance and the created will need
    getEnvironment().define(DynaTypeInstantiator.class, DynaTypeInstantiator::create);
  }

  public static ProtoCreator from(Environment environment) {
    ProtoCreator creator = new ProtoCreator();
    creator.environment = environment;
    creator.initializarDependencies();
    return creator;
  }
}
