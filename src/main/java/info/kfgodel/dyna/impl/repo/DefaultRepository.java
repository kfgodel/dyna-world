package info.kfgodel.dyna.impl.repo;

import info.kfgodel.dyna.api.repo.ObjectRepository;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * This type implements the repository with a weak hash map
 * Date: 06/05/19 - 22:27
 */
public class DefaultRepository implements ObjectRepository {

  private WeakHashMap<Map<String, Object>, Object> states;

  @Override
  public Stream<Map<String, Object>> getStates() {
    return states.keySet().stream();
  }

  public static DefaultRepository create() {
    DefaultRepository repository = new DefaultRepository();
    repository.states = new WeakHashMap<>();
    return repository;
  }

}
