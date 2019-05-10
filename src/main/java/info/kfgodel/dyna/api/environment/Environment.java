package info.kfgodel.dyna.api.environment;

import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;
import info.kfgodel.dyna.api.repo.StateRepository;
import info.kfgodel.dyna.api.repo.TypePrism;

import java.util.function.Supplier;

/**
 * This type represents an isolated ecosystem of objects that are closely coupled together so they are interdependent.<br>
 *   An environment is usually a functional module (not a logic separation, but a working unit) that can also be integrated
 *   into other environments
 *
 * Date: 03/05/19 - 23:22
 */
public interface Environment {

  /**
   * Finds an instance of the asked type in this enviroment.<br>
   *   A previous definition to must be declared in this enviroment for the instance to exist
   * @param expectedType The type of expected object
   * @param <T> The type of returned object
   * @return An instance of the asked type taken from this environment
   * @throws DynaWorldException If the asked type doesn't have a definition on this environment
   */
  <T> T provide(Class<T> expectedType) throws DynaWorldException;

  /**
   * Registers on this environment a definition to provide instances of the given type.<br>
   *   Every time an instance of the given type is asked the supplier lambda will be called
   * @param <T> The type of provided instances
   * @param providedType The type for with the supplier will be registered and used
   * @param definition The definition to create or get instances of teh asked type
   */
  <T> void define(Class<T> providedType, Supplier<? extends T> definition);

  /**
   * Facility method to easily access the provided creator on this environment
   * @return The object used to create other objects
   */
  default ObjectCreator creator(){
    return this.provide(ObjectCreator.class);
  }

  /**
   * Facility method to access the object repository.<br>
   *   The repository allows access to all created objects and allows transformations to their types
   * @return The repository of this environment
   */
  default StateRepository repository(){
    return this.provide(StateRepository.class);
  }

  /**
   * Facility method to access the type prism existing in this environment.<br>
   *   The prism allows access to objects with matching type.<br>
   * @return The type prism to get instances of a type
   */
  default TypePrism prism(){
    return this.provide(TypePrism.class);
  }
}
