package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.dyna.api.ObjectCreator;
import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.dyna.SelfStateObject;
import info.kfgodel.dyna.testobjects.InterdependentTypeA;
import info.kfgodel.dyna.testobjects.InterdependentTypeB;
import info.kfgodel.dyna.testobjects.Lycanthrope;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import info.kfgodel.dyna.testobjects.Wolf;

import java.util.Map;
import java.util.function.Supplier;

/**
 * This serves as definition for test variables
 * Date: 01/05/19 - 22:56
 */
public interface WorldTestContext extends TestContext {

  TextMarquee marquee();
  void marquee(Supplier<TextMarquee> definition);

  Environment environment();
  void environment(Supplier<Environment> definition);

  ObjectCreator creator();
  void creator(Supplier<ObjectCreator> definition);

  InterdependentTypeA objectA();
  void objectA(Supplier<InterdependentTypeA> definition);

  InterdependentTypeB objectB();
  void objectB(Supplier<InterdependentTypeB> definition);

  SelfStateObject objectWithState();
  void objectWithState(Supplier<SelfStateObject> definition);

  SimpleTestObject simpleObject();
  void simpleObject(Supplier<SimpleTestObject> definition);

  SimpleTestObject otherObject();
  void otherObject(Supplier<SimpleTestObject> definition);


  Map<String, Object> initialState();
  void initialState(Supplier<Map<String, Object>> definition);

  Map<String, Object> otherInitialState();
  void otherInitialState(Supplier<Map<String, Object>> definition);

  Lycanthrope metamorph();
  void metamorph(Supplier<Lycanthrope> definition);

  Wolf morphed();
  void morphed(Supplier<Wolf> definition);


}
