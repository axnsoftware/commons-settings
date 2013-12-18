/*
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
package de.axnsoftware.settings.impl;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ISettings;
import de.axnsoftware.settings.ISettingsStore;
import de.axnsoftware.settings.fixtures.CustomTypeSettingsRoot;
import de.axnsoftware.settings.fixtures.SettingsRootWithNonDefaultConstructor;
import de.axnsoftware.settings.fixtures.SettingsRootWithPrivateDefaultConstructor;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class SettingsStoreImplTest
{

    @Test(expected = IllegalArgumentException.class)
    public void constructorMustFailOnNullBackingStore()
    {
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        new SettingsStoreImpl(null, rootAccessorMock,
                              CustomTypeSettingsRoot.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorMustFailOnNullRootAccessor()
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new SettingsStoreImpl(backingStoreMock, null,
                              CustomTypeSettingsRoot.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorMustFailOnNullType()
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        new SettingsStoreImpl(backingStoreMock, rootAccessorMock, null);
    }

    @Test
    public void deleteSettingsMustDelegateToBackingStore() throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        new SettingsStoreImpl(backingStoreMock, rootAccessorMock,
                              CustomTypeSettingsRoot.class).deleteSettings();
        Mockito.verify(backingStoreMock).deleteProperties();
    }

    @Test
    public void getBackingStoreMustReturnExpectedValue() throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        Assert.assertSame(backingStoreMock, new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock, CustomTypeSettingsRoot.class)
                .getBackingStore());
    }

    @Test
    public void getTypeMustReturnExpectedValue() throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        Assert.assertSame(CustomTypeSettingsRoot.class, new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock, CustomTypeSettingsRoot.class)
                .getType());
    }

    @Test
    public void loadSettingsMustReturnProperlyConfiguredSettings() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        ISettingsStore store = new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock,
                CustomTypeSettingsRoot.class);
        ISettings settings = store.loadSettings();
        Mockito.verify(backingStoreMock).loadProperties();
        Mockito.verify(rootAccessorMock).readFromBackingStore(
                Mockito.<IBackingStore>eq(backingStoreMock), Mockito
                .<CustomTypeSettingsRoot>any(CustomTypeSettingsRoot.class));
        Assert.assertSame(store, settings.getStore());
        Assert.assertSame(CustomTypeSettingsRoot.class, settings.getType());
    }

    @Test(expected = RuntimeException.class)
    public void loadSettingsMustFailOnSettingsRootWithprivateDefaultConstructor()
            throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        ISettingsStore store = new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock,
                SettingsRootWithPrivateDefaultConstructor.class);
        ISettings settings = store.loadSettings();
    }

    @Test(expected = RuntimeException.class)
    public void loadSettingsMustFailOnSettingsRootWithNonDefaultConstructor()
            throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        ISettingsStore store = new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock,
                SettingsRootWithNonDefaultConstructor.class);
        ISettings settings = store.loadSettings();
    }

    @Test
    public void storeSettingsMustDelegateAsExpected() throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        IAccessor rootAccessorMock = Mockito.mock(IAccessor.class);
        ISettings settingsMock = Mockito.mock(ISettings.class);
        ISettingsStore store = new SettingsStoreImpl(
                backingStoreMock, rootAccessorMock,
                CustomTypeSettingsRoot.class);
        Properties properties = new Properties();
        Mockito.when(settingsMock.getProperties()).thenReturn(properties);
        store.storeSettings(settingsMock);
        Mockito.verify(settingsMock).getProperties();
        Mockito.verify(rootAccessorMock).writeToBackingStore(
                Mockito.<IBackingStore>eq(backingStoreMock), Mockito
                .<CustomTypeSettingsRoot>any(CustomTypeSettingsRoot.class));
        Mockito.verify(backingStoreMock).storeProperties();
    }
}
