package info.kfgodel.dyna.impl;

import info.kfgodel.dyna.api.Environment;
import info.kfgodel.dyna.api.EnvironmentDependent;
import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.HashMap;
import java.util.Map;

/**
 * This type implements the object creator using dyna type objects
 * Date: 04/05/19 - 13:28
 */
public class DefaultCreator implements ObjectCreator {

  private DynaTypeInstantiator instantiator;
  private Environment environment;

  @Override
  public <T> T create(Class<T> expectedType) throws DynaWorldException {
    Map<String, Object> initialState = createInitialStateFor(expectedType);
    return instantiator.instantiate(expectedType, initialState);
  }

  private <T> Map<String, Object> createInitialStateFor(Class<T> expectedType) {
    HashMap<String, Object> map = new HashMap<>();
    if(EnvironmentDependent.class.isAssignableFrom(expectedType)){
      // Is a subtype. Depends on  the environment
      map.put("environment", this.environment);
    }
    return map;
  }

  public static DefaultCreator create(Environment environment) {
    DefaultCreator creator = new DefaultCreator();
    creator.instantiator = DynaTypeInstantiator.create();
    creator.environment = environment;
    return creator;
  }

}
