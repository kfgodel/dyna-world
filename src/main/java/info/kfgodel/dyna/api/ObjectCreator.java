package info.kfgodel.dyna.api;

import info.kfgodel.dyna.api.exceptions.DynaWorldException;

/**
 * This type creates instances of other objects, and it's the only way to have new objects on an environment
 * Date: 04/05/19 - 13:13
 */
public interface ObjectCreator {

  /**
   * Creates an instance of the given type.<br>
   *   The created instance is also a subtype of {@link DynaObject}
   *
   * @param expectedType The class that indicates the type of expected instance
   * @param <T> The type of returned instance
   * @return The created object
   * @throws DynaWorldException If there's an error on the instantiation
   */
  <T> T create(Class<T> expectedType) throws DynaWorldException;
}
