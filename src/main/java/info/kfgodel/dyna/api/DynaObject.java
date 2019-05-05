package info.kfgodel.dyna.api;

import java.util.Map;

/**
 * This interface defines the minimum contract a created dyna object has.<br>
 *   All created instances inherit this interface that allows them to interact with their state
 *
 * Date: 05/05/19 - 15:19
 */
public interface DynaObject {

  /**
   * Returns the internal state of this object as a map of properties that can be modified.<br>
   * @return Tha map that represents this object
   */
  Map<String, Object> getInternalState();
}
