package info.kfgodel.dyna.testobjects;

import info.kfgodel.dyna.api.environment.EnvironmentDependent;

/**
 *
 * This type has a cyclic dependency with the {@link InterdependentTypeA} object to show how is that solved
 * Date: 04/05/19 - 20:04
 */
public interface InterdependentTypeB extends EnvironmentDependent {

  default String getName(){
    return "B";
  }

  default InterdependentTypeA getOther(){
    return environment().provide(InterdependentTypeA.class);
  }


}
