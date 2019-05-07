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
   * Property under which the object id is stored as state
   */
  String OBJECT_ID_PROPERTY = "objectId";

  /**
   * Returns the internal object id that uniquely identifies this instance accross all types.<br>
   * Two objects are considered teh same if they have the equal object id, even if they are not the same instance.<br>
   * @return The generated ID that is shared between instances that represents the same object
   */
  String getObjectId();

  /**
   * Returns the internal state of this object as a map of properties that can be modified.<br>
   * @return Tha map that represents this object
   */
  Map<String, Object> getInternalState();
}
