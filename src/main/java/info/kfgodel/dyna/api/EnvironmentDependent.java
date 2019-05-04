package info.kfgodel.dyna.api;

/**
 * This interface is for objects that have dependencies on their environment to fulfill their purpose.<br>
 *   The environment is the object that hosts the objects and their dependencies so the can be provided as needed
 *
 * Date: 03/05/19 - 23:20
 */
public interface EnvironmentDependent {

  /**
   * Returns the object that represents the rest of the system as seen from this instance and from which
   * it can access other objects as dependencies
   * @return The environment from which this object is part
   */
  Environment environment();
}
