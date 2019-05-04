package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.EnvironmentDependent;
import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.HashMap;
import java.util.Map;

/**
 * This type defines the object creator following the same rules as the rest of the creatable objects
 *
 * Date: 04/05/19 - 13:45
 */
public interface DynaObjectCreator extends ObjectCreator, EnvironmentDependent {

  @Override
  default <T> T create(Class<T> expectedType) throws DynaWorldException{
    DynaTypeInstantiator instantiator = getEnvironment().provide(DynaTypeInstantiator.class);
    Map<String, Object> initialState = createInitialStateFor(expectedType);
    return instantiator.instantiate(expectedType, initialState);
  }

  default <T> Map<String, Object> createInitialStateFor(Class<T> expectedType){
    HashMap<String, Object> map = new HashMap<>();
    if(EnvironmentDependent.class.isAssignableFrom(expectedType)){
      // Is a subtype. Depends on  the environment
      map.put("environment", this.getEnvironment());
    }
    return map;
  }
}
