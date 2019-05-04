package info.kfgodel.function;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * Context for function tests
 * Date: 04/05/19 - 18:30
 */
public interface FunctionTestContext extends TestContext {

  MemoizedSupplier memoized();
  void memoized(Supplier<MemoizedSupplier> definition);

}
