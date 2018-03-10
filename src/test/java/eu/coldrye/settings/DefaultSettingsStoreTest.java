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

import eu.coldrye.settings.fixtures.CustomTypeSettingsRoot;
import eu.coldrye.settings.fixtures.SettingsRootWithNonDefaultConstructor;
import eu.coldrye.settings.fixtures.SettingsRootWithPrivateDefaultConstructor;
import eu.coldrye.settings.accessors.Accessor;

import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 *
 */
public class DefaultSettingsStoreTest {

  @Test
  public void constructorMustFailOnNullBackingStore() {

    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new DefaultSettingsStore(null, rootAccessorMock, CustomTypeSettingsRoot.class);
    });
  }

  @Test
  public void constructorMustFailOnNullRootAccessor() {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new DefaultSettingsStore(backingStoreMock, null, CustomTypeSettingsRoot.class);
    });
  }

  @Test
  public void constructorMustFailOnNullType() {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new DefaultSettingsStore(backingStoreMock, rootAccessorMock, null);
    });
  }

  @Test
  public void deleteSettingsMustDelegateToBackingStore() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    new DefaultSettingsStore(backingStoreMock, rootAccessorMock, CustomTypeSettingsRoot.class).deleteSettings();
    Mockito.verify(backingStoreMock).deleteProperties();
  }

  @Test
  public void getBackingStoreMustReturnExpectedValue() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    Assertions.assertSame(backingStoreMock, new DefaultSettingsStore(backingStoreMock, rootAccessorMock,
      CustomTypeSettingsRoot.class).getBackingStore());
  }

  @Test
  public void getTypeMustReturnExpectedValue() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    Assertions.assertSame(CustomTypeSettingsRoot.class, new DefaultSettingsStore(backingStoreMock, rootAccessorMock,
      CustomTypeSettingsRoot.class).getType());
  }

  @Test
  public void loadSettingsMustReturnProperlyConfiguredSettings() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    SettingsStore store = new DefaultSettingsStore(backingStoreMock, rootAccessorMock, CustomTypeSettingsRoot.class);
    Settings settings = store.loadSettings();
    Mockito.verify(backingStoreMock).loadProperties();
    Mockito.verify(rootAccessorMock).readFromBackingStore(Mockito.eq(backingStoreMock),
      Mockito.any(CustomTypeSettingsRoot.class));
    Assertions.assertSame(store, settings.getStore());
    Assertions.assertSame(CustomTypeSettingsRoot.class, settings.getType());
  }

  @Test
  public void loadSettingsMustFailOnSettingsRootWithprivateDefaultConstructor() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    SettingsStore store = new DefaultSettingsStore(backingStoreMock, rootAccessorMock,
      SettingsRootWithPrivateDefaultConstructor.class);
    Assertions.assertThrows(RuntimeException.class, () -> {
      Settings settings = store.loadSettings();
    });
  }

  @Test
  public void loadSettingsMustFailOnSettingsRootWithNonDefaultConstructor() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    SettingsStore store = new DefaultSettingsStore(backingStoreMock, rootAccessorMock,
      SettingsRootWithNonDefaultConstructor.class);
    Assertions.assertThrows(RuntimeException.class, () -> {
      Settings settings = store.loadSettings();
    });
  }

  @Test
  public void storeSettingsMustDelegateAsExpected() throws Exception {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    Accessor rootAccessorMock = Mockito.mock(Accessor.class);
    Settings settingsMock = Mockito.mock(Settings.class);
    SettingsStore store = new DefaultSettingsStore(backingStoreMock, rootAccessorMock, CustomTypeSettingsRoot.class);
    Properties properties = new Properties();
    Mockito.when(settingsMock.getProperties()).thenReturn(properties);
    store.storeSettings(settingsMock);
    Mockito.verify(settingsMock).getProperties();
    Mockito.verify(rootAccessorMock).writeToBackingStore(Mockito.eq(backingStoreMock),
      Mockito.any(CustomTypeSettingsRoot.class));
    Mockito.verify(backingStoreMock).storeProperties();
  }
}
