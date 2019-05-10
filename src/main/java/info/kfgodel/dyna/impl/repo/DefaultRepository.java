package info.kfgodel.dyna.impl.repo;

import info.kfgodel.dyna.api.repo.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * This type implements the repository with a weak hash map
 * Date: 06/05/19 - 22:27
 */
public class DefaultRepository implements StateRepository {
  public static Logger LOG = LoggerFactory.getLogger(DefaultRepository.class);

  private WeakHashMap<Map<String, Object>, Object> states;

  @Override
  public Stream<Map<String, Object>> getStates() {
    return new HashSet<>(states.keySet()).stream();
  }

  @Override
  public void register(Map<String,Object> state) {
    Object previousState = states.put(state, null);
    if(previousState != state){
      LOG.warn("We have different state instances with equal content: {}", state);
    }
  }

  public static DefaultRepository create() {
    DefaultRepository repository = new DefaultRepository();
    repository.states = new WeakHashMap<>();
    return repository;
  }

}
