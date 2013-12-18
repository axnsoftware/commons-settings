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
package de.axnsoftware.settings;

import de.axnsoftware.settings.fixtures.CustomTypeSettingsRoot;
import de.axnsoftware.settings.impl.PropertiesBackingStoreImpl;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class SetttingsStoreFactoryTest
{

    @Test
    public void newInstanceMustReturnNewInstance()
    {
        SettingsStoreFactory factory1 = SettingsStoreFactory.newInstance();
        SettingsStoreFactory factory2 = SettingsStoreFactory.newInstance();
        Assert.assertNotSame(factory1, factory2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newFileStoreMustFailOnNullStoragePath()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newFileStore(null, CustomTypeSettingsRoot.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newFileStoreMustFailOnNullType()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newFileStore(new File("/tmp/unused"), null);
    }

    @Test
    public void newFileStoreMustReturnProperlyConfiguredSettingsStore()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        ISettingsStore store = factory.newFileStore(
                new File("/tmp/unused"), CustomTypeSettingsRoot.class);
        Assert.assertNotNull(store);
        Assert.assertTrue(
                store.getBackingStore() instanceof PropertiesBackingStoreImpl);
        Assert.assertSame(CustomTypeSettingsRoot.class, store.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void newStoreMustFailOnNullBackingStore()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newStore(null, CustomTypeSettingsRoot.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newStoreMustFailOnNullType()
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newStore(backingStoreMock, null);
    }

    @Test
    public void newStoreMustReturnProperlyConfiguredSettingsStore()
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        ISettingsStore store = factory.newStore(backingStoreMock,
                                                CustomTypeSettingsRoot.class);
        Assert.assertNotNull(store);
        Assert.assertSame(backingStoreMock, store.getBackingStore());
        Assert.assertSame(CustomTypeSettingsRoot.class, store.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void newXMLFileStoreMustFailOnNullStoragePath()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newXMLFileStore(null, CustomTypeSettingsRoot.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newXMLFileStoreMustFailOnNullType()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        factory.newXMLFileStore(new File("/tmp/unused"), null);
    }

    @Test
    public void newXMLFileStoreMustReturnProperlyConfiguredSettingsStore()
    {
        SettingsStoreFactory factory = SettingsStoreFactory.newInstance();
        ISettingsStore store = factory.newXMLFileStore(
                new File("/tmp/unused"), CustomTypeSettingsRoot.class);
        Assert.assertNotNull(store);
        Assert.assertTrue(
                store.getBackingStore() instanceof PropertiesBackingStoreImpl);
        Assert.assertSame(CustomTypeSettingsRoot.class, store.getType());
    }
}
