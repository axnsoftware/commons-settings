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

import de.axnsoftware.settings.impl.accessor.IAccessor;
import de.axnsoftware.settings.ISettings;
import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ISettingsStore;
import java.util.prefs.BackingStoreException;

/**
 * The final class SettingsStoreImpl models a concrete implementation for the
 * {@code ISettingsStore} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SettingsStoreImpl
        implements ISettingsStore
{

    private final Class<?> type;
    private IBackingStore backingStore;
    private final IAccessor rootAccessor;

    public SettingsStoreImpl(final IBackingStore backingStore,
                             final IAccessor rootAccessor, final Class<?> type)
    {
        if (null == backingStore)
        {
            throw new IllegalArgumentException("backingStore must not be null.");
        }
        if (null == rootAccessor)
        {
            throw new IllegalArgumentException("rootAccessor must not be null.");
        }
        if (null == type)
        {
            throw new IllegalArgumentException("type must not be null.");
        }
        this.backingStore = backingStore;
        this.rootAccessor = rootAccessor;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getType()
    {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISettings loadSettings() throws BackingStoreException
    {
        ISettings result = null;
        try
        {
            Object settingsRoot = this.type.newInstance();
            this.backingStore.loadProperties();
            this.rootAccessor.readFromBackingStore(this.backingStore,
                                                   settingsRoot);
            result = new SettingsImpl(settingsRoot, this.rootAccessor, this);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new BackingStoreException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSettings() throws BackingStoreException
    {
        this.backingStore.deleteProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeSettings(final ISettings settings) throws
            BackingStoreException
    {
        this.rootAccessor.writeToBackingStore(this.backingStore, settings
                .getProperties());
        this.backingStore.storeProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBackingStore getBackingStore()
    {
        return this.backingStore;
    }
}
