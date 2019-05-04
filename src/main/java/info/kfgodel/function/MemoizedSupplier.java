package info.kfgodel.function;

import java.util.function.Supplier;

/**
 * This type represents a supplier that keeps the first value it produces and returns that value on subsequent calls.<br>
 *   This class allows reusing created objects, while keeping a simple interface for creating them
 * Date: 04/05/19 - 18:21
 */
public class MemoizedSupplier<T> implements Supplier<T> {

  private Supplier<T> delegate;
  private T memoizedValue;

  @Override
  public T get() {
    if(delegate != null){
      this.memoizedValue = delegate.get();
      this.delegate = null; // Discard the original supplier after use
    }
    return memoizedValue ;
  }

  public static <T>  MemoizedSupplier<T> create(Supplier<T> onlyInstanceSupplier) {
    MemoizedSupplier memoizedSupplier = new MemoizedSupplier();
    memoizedSupplier.delegate = onlyInstanceSupplier;
    return memoizedSupplier;
  }

  /**
   * Friendly alias of {@link #create(Supplier)} to be imported in other classes.<br>
   *   It creates a supplier that will hold a reference to the first value produced by the
   *   given supplier and keep returning that when called
   * @param other The supplier whose first value is to be memoized
   * @param <T> The type of returned value
   * @return The memorized supplier
   */
  public static <T> Supplier<T> memoized(Supplier<T> other){
    return create(other);
  }

}
