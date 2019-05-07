package info.kfgodel.dyna.api.creator;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.exceptions.DynaWorldException;

import java.util.HashMap;
import java.util.Map;

/**
 * This type creates instances of other objects, and it's the only way to have new objects on an environment
 * Date: 04/05/19 - 13:13
 */
public interface ObjectCreator extends DynaObject {

  /**
   * Creates an instance of the given type.<br>
   *   The created instance is also a subtype of {@link DynaObject}
   *
   * @param expectedType The class that indicates the type of expected instance
   * @param <T> The type of returned instance
   * @return The created object
   * @throws DynaWorldException If there's an error on the instantiation
   */
  default <T> T create(Class<T> expectedType) throws DynaWorldException{
    return create(expectedType, new HashMap<>());
  }

  /**
   * Creates an instance of the given type using the given state.<br>
   *   Sharing the state between different objects will make them the same dyna object (even though completely different types).<br>
   *   The created instance is also a subtype of {@link DynaObject}
   * @param expectedType The type of expected instance to create (interface or abstract class)
   * @param initialState The map to use internally as the holder of state
   * @param <T> The type of returned instance
   * @return The created object
   * @throws DynaWorldException If there's an error on the instantiation
   */
  <T> T create(Class<T> expectedType, Map<String, Object> initialState) throws DynaWorldException;
}
