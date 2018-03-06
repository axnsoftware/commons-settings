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

package eu.coldrye.settings.impl;

import eu.coldrye.settings.ProblemReporter;
import eu.coldrye.settings.Settings;
import eu.coldrye.settings.SettingsStore;
import eu.coldrye.settings.impl.accessor.Accessor;
import eu.coldrye.settings.util.ReflectionUtils;

/**
 * The class SettingsImpl models a concrete implementation of the
 * {@code Settings} interface.
 *
 * @since 1.0.0
 */
public class SettingsImpl<T> implements Settings<T> {

  private T oldProperties;

  private T properties;

  private Accessor rootAccessor;

  private SettingsStore<T> settingsStore;

  public SettingsImpl(T properties, Accessor rootAccessor, SettingsStore<T> settingsStore) {

    if (null == properties) {
      throw new IllegalArgumentException("properties must not be null.");
    }
    if (null == rootAccessor) {
      throw new IllegalArgumentException(
        "rootAccessor must not be null.");
    }
    if (null == settingsStore) {
      throw new IllegalArgumentException(
        "settingsStore must not be null.");
    }
    this.properties = properties;
    this.rootAccessor = rootAccessor;
    this.settingsStore = settingsStore;
  }

  @Override
  public void discardChanges() {

    if (getHasPendingChanges()) {
      properties = oldProperties;
      oldProperties = null;
    }
  }

  @Override
  public void finalizeChanges() {

    if (getHasPendingChanges()) {
      oldProperties = null;
    }
  }

  @Override
  public Boolean getHasPendingChanges() {

    return oldProperties != null;
  }

  @Override
  public T getProperties() {

    return copyProperties(properties);
  }

  @Override
  public SettingsStore<T> getStore() {

    return settingsStore;
  }

  @Override
  public Class<T> getType() {

    return getStore().getType();
  }

  @Override
  public void setProperties(T properties) {

    oldProperties = properties;
    this.properties = copyProperties(properties);
  }

  /**
   * Creates a copy of the specified {@code source}.
   *
   * @param source
   * @return the copied object
   */
  private T copyProperties(T source) {

    T result = ReflectionUtils.newInstance(getType());
    rootAccessor.copyValue(source, result);
    return result;
  }

  public ProblemReporter validate() {

    ProblemReporter problemReporter = new ProblemReporterImpl(this);
    rootAccessor.validate(problemReporter);
    return problemReporter;
  }
}
