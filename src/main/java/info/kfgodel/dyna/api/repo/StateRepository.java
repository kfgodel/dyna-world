package info.kfgodel.dyna.api.repo;

import java.util.Map;
import java.util.stream.Stream;

/**
 * This type represents the object repository where all the created objects are registered and from which is possible
 * access to the different sets
 *
 * Date: 06/05/19 - 21:53
 */
public interface StateRepository {


  /**
   * Returns all the states creates and still referenced by existing objects.<br>
   *   No longer referenced object are garbage collected and not returned by this method.<br>
   *  Due to objects being able to take any type this method only returns the state. Only one state is returned
   *  for multiple objects sharing the same
   *
   * @return The streams of each unique state
   */
  Stream<Map<String,Object>> getStates();

  /**
   * Adds the given instance to this repository for registering its state.<br>
   *   Only the environment creator should call this method
   * @param instantiated The newly created instance
   */
  void register(Object instantiated);
}
