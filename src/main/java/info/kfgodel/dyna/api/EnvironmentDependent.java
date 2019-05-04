package info.kfgodel.dyna.api;

/**
 * This interface is for objects that have dependencies on their environment to fulfill their purpose.<br>
 *   The environment is the object that hosts the objects and their dependencies so they can be provided as needed
 *
 * Date: 03/05/19 - 23:20
 */
public interface EnvironmentDependent {

  /**
   * Returns the environment. The object that represents the entry point to rest of the system (as seen from this instance).
   * From the environment this instance can access its dependencies
   * @return The environment from which this object is part
   */
  Environment environment();
}
