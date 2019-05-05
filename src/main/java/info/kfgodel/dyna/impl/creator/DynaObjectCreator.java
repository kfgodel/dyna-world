package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.environment.EnvironmentDependent;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.HashMap;
import java.util.Map;

/**
 * This type defines the object creator following the same rules as the rest of the creatable objects
 *
 * Date: 04/05/19 - 13:45
 */
public abstract class DynaObjectCreator implements ObjectCreator, EnvironmentDependent {

  @Override
  public <T> T create(Class<T> expectedType) throws DynaWorldException{
    DynaTypeInstantiator instantiator = environment().provide(DynaTypeInstantiator.class);
    Map<String, Object> initialState = createInitialStateFor(expectedType);
    return instantiator.instantiate(expectedType, initialState);
  }

  private <T> Map<String, Object> createInitialStateFor(Class<T> expectedType){
    HashMap<String, Object> state = new HashMap<>();
    return state;
  }
}
