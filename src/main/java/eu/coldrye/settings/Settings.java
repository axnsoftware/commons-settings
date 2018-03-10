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

/**
 * The interface Settings models a service by which one can access properties
 * loaded from the underlying {@link SettingsStore}. It provides a simple
 * workflow for maintaining pending changes, discarding pending changes and
 * finalizing any pending changes before actually persisting them to the
 * underlying storage.
 *
 * @since 1.0.0
 */
public interface Settings<T> {

  /**
   * Returns the type registered with the underlying {@code SettingsStore}.
   *
   * @return the registered type
   */
  Class<?> getType();

  /**
   * Discards all pending changes.
   */
  void discardChanges();

  /**
   * Finalizes all pending changes but does not store them permanently. In
   * order for the settings to be permanently stored, one must call
   * {@code SettingsStore#storeProperties()}.
   */
  void finalizeChanges();

  /**
   * Returns whether there are any pending changes which either need to be finalized or discarded.
   *
   * @return true whether there are any pending changes
   */
  Boolean getHasPendingChanges();

  /**
   * Returns an instance of the registered type.
   *
   * @return the properties
   */
  T getProperties();

  /**
   * Gets the underlying store.
   *
   * @return the underlying store
   */
  SettingsStore<T> getStore();

  /**
   * @return
   */
  ProblemReporter validate();

  /**
   * Sets the properties but does not finalize them. {@code properties} must
   * be an instance of the registered type.
   *
   * @param properties
   */
  void setProperties(T properties);
}
