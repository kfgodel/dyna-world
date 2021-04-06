package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.environment.Environment;

import java.util.Map;


/**
 * This class is necessary to solve the paradox of who creates the creator.<br>
 * A proto creator is used to create the creator, and then it's discarded. In that way all objects, including the creator
 * itself are created following the same principles.<br>
 * The only object that is not created out of a creator is the environment which is a pre-requisite for the rest.<br>
 * <br>
 * Because we want the proto-creator and the real creator instance to behave as close as possible both share the hierarchy
 *
 * Date: 04/05/19 - 13:28
 */
public class ProtoCreator implements DynaObjectCreator {

  private Environment environment;

  @Override
  public Environment environment() {
    // Because the creator doesn't exists yet, an environment can't be provided
    return this.environment;
  }

    public static ProtoCreator from(Environment environment) {
    ProtoCreator creator = new ProtoCreator();
    creator.environment = environment;
    return creator;
  }

  @Override
  public String getObjectId() {
    throw new IllegalStateException("The proto creator doesn't have an id because it's not a real dyna object. It should be used as temporary creator");
  }

  @Override
  public Map<String, Object> getInternalState() {
    throw new IllegalStateException("The proto creator doesn't have an internal state because it's not a real dyna object. It should be used as temporary creator");
  }
}
