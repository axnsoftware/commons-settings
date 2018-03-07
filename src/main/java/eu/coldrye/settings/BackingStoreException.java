package eu.coldrye.settings;

import java.io.IOException;

/**
 * TODO:DOCUMENT
 *
 * @since 1.0.0
 */
public class BackingStoreException extends IOException {

  public BackingStoreException(String message) {
    super(message);
  }

  public BackingStoreException(Throwable cause) {
    super(cause);
  }

  public BackingStoreException(String message, Throwable cause) {
    super(message, cause);
  }
}
