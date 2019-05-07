package info.kfgodel.dyna.impl.creator;

import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.environment.EnvironmentDependent;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.api.repo.ObjectRepository;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.Map;

/**
 * This type defines the object creator following the same rules as the rest of the creatable objects
 *
 * Date: 04/05/19 - 13:45
 */
public interface DynaObjectCreator extends ObjectCreator, EnvironmentDependent {

  @Override
  default <T> T create(Class<T> expectedType, Map<String, Object> initialState) throws DynaWorldException{
    DynaTypeInstantiator instantiator = environment().provide(DynaTypeInstantiator.class);
    T instantiated = instantiator.instantiate(expectedType, initialState);
    // Record the instance on the repository
    environment().provide(ObjectRepository.class).register(instantiated);
    return instantiated;
  }
}
