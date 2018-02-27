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
import eu.coldrye.settings.impl.PropertiesBackingStoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

/**
 *
 *
 */
public class SetttingsStoreFactoryTest {

  @Test
  public void newInstanceMustReturnNewInstance() {

    SettingsStoreFactory factory1 = SettingsStoreFactory.newInstance();
    SettingsStoreFactory factory2 = SettingsStoreFactory.newInstance();
    Assertions.assertNotSame(factory1, factory2);
  }

  @Test
  public void newFileStoreMustFailOnNullStoragePath() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newFileStore(null, CustomTypeSettingsRoot.class);
    });
  }

  @Test
  public void newFileStoreMustFailOnNullType() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newFileStore(new File("/tmp/unused"), null);
    });
  }

  @Test
  public void newFileStoreMustReturnProperlyConfiguredSettingsStore() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    SettingsStore store = factory.newFileStore(new File("/tmp/unused"), CustomTypeSettingsRoot.class);
    Assertions.assertNotNull(store);
    Assertions.assertTrue(
      store.getBackingStore() instanceof PropertiesBackingStoreImpl);
    Assertions.assertSame(CustomTypeSettingsRoot.class, store.getType());
  }

  @Test
  public void newStoreMustFailOnNullBackingStore() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newStore(null, CustomTypeSettingsRoot.class);
    });
  }

  @Test
  public void newStoreMustFailOnNullType() {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newStore(backingStoreMock, null);
    });
  }

  @Test
  public void newStoreMustReturnProperlyConfiguredSettingsStore() {

    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    SettingsStore store = factory.newStore(backingStoreMock, CustomTypeSettingsRoot.class);
    Assertions.assertNotNull(store);
    Assertions.assertSame(backingStoreMock, store.getBackingStore());
    Assertions.assertSame(CustomTypeSettingsRoot.class, store.getType());
  }

  @Test
  public void newXMLFileStoreMustFailOnNullStoragePath() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newXMLFileStore(null, CustomTypeSettingsRoot.class);
    });
  }

  @Test
  public void newXMLFileStoreMustFailOnNullType() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      factory.newXMLFileStore(new File("/tmp/unused"), null);
    });
  }

  @Test
  public void newXMLFileStoreMustReturnProperlyConfiguredSettingsStore() {

    SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
    SettingsStore store = factory.newXMLFileStore(new File("/tmp/unused"), CustomTypeSettingsRoot.class);
    Assertions.assertAll(
      () -> Assertions.assertNotNull(store),
      () -> Assertions.assertTrue(store.getBackingStore() instanceof PropertiesBackingStoreImpl),
      () -> Assertions.assertSame(CustomTypeSettingsRoot.class, store.getType())
    );
  }
}
