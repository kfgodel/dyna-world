package info.kfgodel.dyna.api.exceptions;

/**
 * This type represents an error on the dyan world interactions.<br>
 *   This is the common super type for all dyna world exceptions
 * Date: 03/05/19 - 23:53
 */
public class DynaWorldException extends RuntimeException {

  public DynaWorldException(String message) {
    super(message);
  }

  public DynaWorldException(String message, Throwable cause) {
    super(message, cause);
  }

  public DynaWorldException(Throwable cause) {
    super(cause);
  }
}
