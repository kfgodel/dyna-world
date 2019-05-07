package info.kfgodel.dyna.impl.repo;

import info.kfgodel.dyna.api.DynaObject;
import info.kfgodel.dyna.api.repo.ObjectRepository;

import java.util.HashSet;
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
    return new HashSet<>(states.keySet()).stream();
  }

  @Override
  public void register(Object instantiated) {
    if(instantiated instanceof DynaObject){
      DynaObject dyna = (DynaObject) instantiated;
      states.put(dyna.getInternalState(), dyna);
    }else{
      throw new IllegalArgumentException("Only dyna objects are supported on this repository");
    }
  }

  public static DefaultRepository create() {
    DefaultRepository repository = new DefaultRepository();
    repository.states = new WeakHashMap<>();
    return repository;
  }

}
