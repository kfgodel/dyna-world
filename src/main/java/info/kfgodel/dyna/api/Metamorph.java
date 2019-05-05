package info.kfgodel.dyna.api;

import info.kfgodel.dyna.api.environment.EnvironmentDependent;

/**
 * This type represents an object that can change its type to a different one (keeping the same state)
 * Date: 05/05/19 - 19:10
 */
public interface Metamorph extends DynaObject, EnvironmentDependent {

  /**
   * Returns an instance of the given type that represents this same object but with different type
   * The new object will be linked to this instance and any transformation on that object state will affect this one, and vice versa
   * @param otherClass The class that indicates the type of expected instance (interface or abstract class)
   * @param <T> The type of created instance
   * @return The instance linked to this one
   */
  default <T> T become(Class<T> otherClass){
    return environment().creator().create(otherClass, getInternalState());
  }
}
