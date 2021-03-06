package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.environment.EnvironmentDependent;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.api.repo.StateRepository;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.Map;
import java.util.UUID;

/**
 * This type defines the object creator following the same rules as the rest of the creatable objects
 *
 * Date: 04/05/19 - 13:45
 */
public interface DynaObjectCreator extends ObjectCreator, EnvironmentDependent {

  @Override
  default <T> T create(Class<T> expectedType, Map<String, Object> initialState) throws DynaWorldException{
    if(!initialState.containsKey(DynaObject.OBJECT_ID_PROPERTY)){
      // Ensure every created object has a unique id
      initialState.put(DynaObject.OBJECT_ID_PROPERTY, UUID.randomUUID().toString());
    }
    DynaTypeInstantiator instantiator = environment().provide(DynaTypeInstantiator.class);
    T instantiated = instantiator.instantiate(expectedType, initialState);
    // Record the state used for the new object on the repo
    environment().provide(StateRepository.class).register(initialState);
    return instantiated;
  }
}
