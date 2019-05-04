package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.dyna.api.Environment;
import info.kfgodel.dyna.api.ObjectCreator;

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


}
