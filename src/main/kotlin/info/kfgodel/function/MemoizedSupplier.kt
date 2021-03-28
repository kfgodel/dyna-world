package info.kfgodel.function

import java.util.function.Supplier

/**
 * This type represents a supplier that keeps the first value it produces and returns that value on subsequent calls.<br></br>
 * This class allows reusing created objects, while keeping a simple interface for creating them
 * Date: 04/05/19 - 18:21
 */
class MemoizedSupplier<T> : Supplier<T> {
  private var delegate: Supplier<T>? = null
  private var memoizedValue: T? = null
  override fun get(): T {
    if (delegate != null) {
      memoizedValue = delegate!!.get()
      delegate = null // Discard the original supplier after use
    }
    return memoizedValue!!
  }

  companion object {
    fun <T> create(onlyInstanceSupplier: Supplier<T>?): MemoizedSupplier<T> {
      val memoizedSupplier: MemoizedSupplier<T> = MemoizedSupplier<T>()
      memoizedSupplier.delegate = onlyInstanceSupplier
      return memoizedSupplier
    }

    /**
     * Friendly alias of [.create] to be imported in other classes.<br></br>
     * It creates a supplier that will hold a reference to the first value produced by the
     * given supplier and keep returning that when called
     * @param other The supplier whose first value is to be memoized
     * @param <T> The type of returned value
     * @return The memorized supplier
    </T> */
    fun <T> memoized(other: Supplier<T>?): Supplier<T> {
      return create(other)
    }
  }
}
