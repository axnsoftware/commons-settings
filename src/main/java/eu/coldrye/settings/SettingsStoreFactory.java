/*
 * Copyright 2018 coldrye.eu, Carsten Klein
 * Copyright 2013 axn software UG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.coldrye.settings;

import eu.coldrye.settings.accessors.Accessor;
import eu.coldrye.settings.accessors.RootAccessorBuilder;

import java.io.File;

/**
 * The class SettingsStoreFactory models a factory for instances of the
 * {@link SettingsStore} interface.
 *
 * @since 1.0.0
 */
public class SettingsStoreFactory {

  /**
   * Private default constructor.
   */
  private SettingsStoreFactory() {

  }

  /**
   * Returns a new instance of this.
   *
   * @return the instance
   */
  public static SettingsStoreFactory newInstance() {

    return new SettingsStoreFactory();
  }

  /**
   * Returns a new instance of the {@link SettingsStore} interface for the
   * specified {@code storagePath} and the specified {@code type}, which must
   * be annotated with the {@link PropertyClass} annotation.
   * <p>
   * The backing store uses the file system and standard plain text format
   * Java property files.
   *
   * @param storagePath
   * @param type
   * @return the settings store
   */
  public <T> SettingsStore<T> newFileStore(File storagePath, Class<T> type) {

    if (null == storagePath) {
      throw new IllegalArgumentException("storagePath must not be null.");
    }
    BackingStore backingStoreWrapper = new DefaultPropertiesBackingStore(FileFormat.PLAIN_TEXT, storagePath);
    return this.newStore(backingStoreWrapper, type);
  }

  /**
   * Returns a new instance of the {@link SettingsStore} interface for the
   * specified {@code fileFormat}, the specified {@code storagePath}, and, the
   * specified {@code type}, which must be annotated with the
   * {@link PropertyClass} annotation.
   *
   * @param backingStore
   * @param type
   * @return the settings store
   */
  public <T> SettingsStore<T> newStore(BackingStore backingStore, Class<T> type) {

    if (null == backingStore) {
      throw new IllegalArgumentException("backingStore must not be null.");
    }
    if (null == type) {
      throw new IllegalArgumentException("type must not be null.");
    }
    Accessor rootAccessor = new RootAccessorBuilder().buildRootAccessor(type);
    return new DefaultSettingsStore<>(backingStore, rootAccessor, type);
  }

  /**
   * Returns a new instance of the {@link SettingsStore} interface for the
   * specified {@code storagePath} and the specified {@code type}, which must
   * be annotated with the {@link PropertyClass} annotation.
   * <p>
   * The backing store uses the file system and standard XML format Java
   * property files.
   *
   * @param storagePath
   * @param type
   * @return the settings store
   */
  public <T> SettingsStore<T> newXMLFileStore(File storagePath, Class<T> type) {

    if (null == storagePath) {
      throw new IllegalArgumentException("storagePath must not be null.");
    }
    BackingStore backingStoreWrapper = new DefaultPropertiesBackingStore(FileFormat.XML, storagePath);
    return this.newStore(backingStoreWrapper, type);
  }
}
