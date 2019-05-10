package info.kfgodel.dyna.api.repo;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.environment.EnvironmentDependent;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This type represents the prism used to project types on existing objects.<br>
 * The metaphor of prism is used to filter existing objects from the environment and project a certain type over them
 * (assume them as instances of that type).<br>
 *  By using a predicate on the state to define an object set and linking it to a type, the prism makes it easy
 *  to mix state and behavior dynamically.<br>
 *
 * Date: 09/05/19 - 23:49
 */
public interface TypePrism extends EnvironmentDependent, DynaObject {

  /**
   * Defines a set of typed objects with a predicate.<br>
   *   All the object states that matches the predicate will be available as the given type
   *
   * @param definedType The type that represents the set of all the objects
   * @param definingPredicate The restrictions the state must match to be considered valid instances of  the given type
   * @return This instance for method chaining
   */
  default TypePrism defineAs(Class<?> definedType, Predicate<Map<String,Object>> definingPredicate){
    Map<Class<?>, Predicate<Map<String,Object>>> definitions = getTypeDefinitions();
    definitions.put(definedType, definingPredicate);
    return this;
  }

  default Map<Class<?>, Predicate<Map<String, Object>>> getTypeDefinitions(){
    return (Map) this.getInternalState().computeIfAbsent("typeDefinitions", (property) -> new HashMap<>());
  }

  /**
   * Returns all the existing objects in the environment that match the definition of the given type.<br>
   *   An error will occur if no previous definition exists for the given type.<br>
   * @param expectedType The class that indicates the type of objects that are queried
   * @param <T> The type of returned objects
   * @return The stream of environment objects that match the type predicate
   */
  default <T> Stream<T> getInstancesOf(Class<T> expectedType){
    Predicate<Map<String, Object>> isAcceptableForType = getTypeDefinitions().get(expectedType);
    if(isAcceptableForType == null){
      throw new DynaWorldException("Type["+expectedType+"] has no predicate to define which objects are included");
    }
    return environment().repository().getStates()
      .filter(isAcceptableForType)
      .map(state -> environment().creator().create(expectedType, state));
  }
}
