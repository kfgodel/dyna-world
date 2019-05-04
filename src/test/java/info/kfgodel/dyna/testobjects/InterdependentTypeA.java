package info.kfgodel.dyna.testobjects;

import info.kfgodel.dyna.api.EnvironmentDependent;

/**
 *
 * This type has a cyclic dependency with the {@link InterdependentTypeB} object to show how is that solved
 * Date: 04/05/19 - 20:04
 */
public interface InterdependentTypeA extends EnvironmentDependent {

  default String getName(){
    return "A";
  }

  default InterdependentTypeB getOther(){
    return environment().provide(InterdependentTypeB.class);
  }


}
