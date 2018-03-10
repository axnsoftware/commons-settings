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
import eu.coldrye.settings.util.ReflectionUtils;

import java.util.Objects;

/**
 * The class DefaultSettingsStore models a concrete implementation of the
 * {@link SettingsStore} interface.
 *
 * @since 1.0.0
 */
class DefaultSettingsStore<T> implements SettingsStore<T> {

  private BackingStore backingStore;

  private Accessor rootAccessor;

  private Class<T> type;

  DefaultSettingsStore(BackingStore backingStore, Accessor rootAccessor, Class<T> type) {

    if (Objects.isNull(backingStore)) {
      throw new IllegalArgumentException("backingStore must not be null.");
    }
    if (Objects.isNull(rootAccessor)) {
      throw new IllegalArgumentException("rootAccessor must not be null.");
    }
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("type must not be null.");
    }
    this.backingStore = backingStore;
    this.rootAccessor = rootAccessor;
    this.type = type;
  }

  @Override
  public void deleteSettings() throws BackingStoreException {

    backingStore.deleteProperties();
  }

  @Override
  public BackingStore getBackingStore() {

    return backingStore;
  }

  @Override
  public Class<T> getType() {

    return type;
  }

  @Override
  public Settings<T> loadSettings() throws BackingStoreException {

    T settingsRoot = ReflectionUtils.newInstance(type);
    backingStore.loadProperties();
    rootAccessor.readFromBackingStore(backingStore, settingsRoot);
    return new DefaultSettings<>(settingsRoot, rootAccessor, this);
  }

  @Override
  public void storeSettings(Settings<T> settings) throws BackingStoreException {

    rootAccessor.writeToBackingStore(backingStore, settings.getProperties());
    backingStore.storeProperties();
  }
}
