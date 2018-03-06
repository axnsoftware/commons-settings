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

import java.util.prefs.BackingStoreException;

/**
 * The interface SettingsStore models a service for loading and storing
 * properties from and to an underlying backing store represented by an instance
 * of the {@link BackingStore} interface.
 *
 * @since 1.0.0
 */
public interface SettingsStore<T> {

  /**
   * Returns the underlying backing store.
   *
   * @return the underlying backing store
   * @throws IllegalStateException
   * @see BackingStore
   */
  BackingStore getBackingStore();

  /**
   * Returns the type representing the root {@link PropertyClass}, that was
   * registered with the store upon its creation.
   *
   * @return the type of the root {@code PropertyClass}
   */
  Class<T> getType();

  /**
   * Returns the settings loaded from the backing store.
   *
   * @return the settings
   * @throws BackingStoreException
   */
  Settings<T> loadSettings() throws BackingStoreException;

  /**
   * Deletes the settings from the underlying backing store.
   *
   * @throws BackingStoreException
   */
  void deleteSettings() throws BackingStoreException;

  /**
   * Stores the specified {@code settings} in the underlying backing store.
   *
   * @param settings
   * @throws BackingStoreException
   */
  void storeSettings(Settings<T> settings) throws BackingStoreException;
}
