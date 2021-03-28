package info.kfgodel.dyna;

import info.kfgodel.dyna.api.creator.ObjectCreator;
import info.kfgodel.dyna.api.environment.Environment;
import info.kfgodel.dyna.api.repo.StateRepository;
import info.kfgodel.dyna.api.repo.TypePrism;
import info.kfgodel.dyna.dyna.SelfStateObject;
import info.kfgodel.dyna.testobjects.ClarkKent;
import info.kfgodel.dyna.testobjects.InterdependentTypeA;
import info.kfgodel.dyna.testobjects.InterdependentTypeB;
import info.kfgodel.dyna.testobjects.Lycanthrope;
import info.kfgodel.dyna.testobjects.SimpleTestObject;
import info.kfgodel.dyna.testobjects.TextMarquee;
import info.kfgodel.dyna.testobjects.Wolf;
import info.kfgodel.jspek.api.contexts.TestContext;

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

  StateRepository repository();
  void repository(Supplier<StateRepository> definition);

  ClarkKent clark();
  void clark(Supplier<ClarkKent> definition);

  TypePrism prism();
  void prism(Supplier<TypePrism> definition);


}
