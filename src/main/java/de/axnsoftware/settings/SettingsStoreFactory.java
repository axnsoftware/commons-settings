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

import de.axnsoftware.settings.impl.SettingsStoreImpl;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import de.axnsoftware.settings.impl.PropertiesBackingStoreImpl;
import de.axnsoftware.settings.impl.accessor.RootAccessorBuilder;
import java.io.File;

/**
 * The final class SettingsStoreFactory models a factory for instances of the
 * {@link ISettingsStore} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SettingsStoreFactory
{

    /**
     * Private default constructor.
     */
    private SettingsStoreFactory()
    {
    }

    /**
     * Returns a new instance of this.
     *
     * @return the instance
     */
    public static SettingsStoreFactory newInstance()
    {
        return new SettingsStoreFactory();
    }

    /**
     * Returns a new instance of the {@link ISettingsStore} interface for the
     * specified {@code storagePath} and the specified {@code type}, which must
     * be annotated with the {@link PropertyClass} annotation.
     *
     * The backing store uses the file system and standard plain text format
     * Java property files.
     *
     * @param storagePath
     * @param type
     * @return the settings store
     */
    public ISettingsStore newFileStore(final File storagePath,
                                       final Class<?> type)
    {
        if (null == storagePath)
        {
            throw new IllegalArgumentException("storagePath must not be null.");
        }
        final IBackingStore backingStoreWrapper =
                            new PropertiesBackingStoreImpl(
                EFileFormat.PLAIN_TEXT, storagePath);
        return this.newStore(backingStoreWrapper, type);
    }

    /**
     * Returns a new instance of the {@link ISettingsStore} interface for the
     * specified {@code fileFormat}, the specified {@code storagePath}, and, the
     * specified {@code type}, which must be annotated with the
     * {@link PropertyClass} annotation.
     *
     * @param backingStore
     * @param type
     * @return the settings store
     */
    public ISettingsStore newStore(
            final IBackingStore backingStore, final Class<?> type)
    {
        if (null == backingStore)
        {
            throw new IllegalArgumentException("backingStore must not be null.");
        }
        if (null == type)
        {
            throw new IllegalArgumentException("type must not be null.");
        }
        IAccessor rootAccessor = RootAccessorBuilder.newInstance()
                .buildRootAccessor(type);
        return new SettingsStoreImpl(backingStore, rootAccessor, type);
    }

    /**
     * Returns a new instance of the {@link ISettingsStore} interface for the
     * specified {@code storagePath} and the specified {@code type}, which must
     * be annotated with the {@link PropertyClass} annotation.
     *
     * The backing store uses the file system and standard XML format Java
     * property files.
     *
     * @param storagePath
     * @param type
     * @return the settings store
     */
    public ISettingsStore newXMLFileStore(final File storagePath,
                                          final Class<?> type)
    {
        if (null == storagePath)
        {
            throw new IllegalArgumentException("storagePath must not be null.");
        }
        final IBackingStore backingStoreWrapper =
                            new PropertiesBackingStoreImpl(
                EFileFormat.XML, storagePath);
        return this.newStore(backingStoreWrapper, type);
    }
}
