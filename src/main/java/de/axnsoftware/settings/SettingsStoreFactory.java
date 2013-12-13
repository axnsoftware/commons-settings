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
import de.axnsoftware.settings.impl.accessor.RootAccessorFactory;
import java.io.File;

/**
 * The final class SettingsStoreFactory models a factory for instances of type
 * {@code ISettingsStore}.
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
     * Returns a new instance of the {@code ISettingsStore} interface for the
     * specified {@code storagePath} and the specified {@code type}, which must
     * be annotated with the {@code PropertyClass} annotation.
     *
     * The backing store will use the file system and standard plain text format
     * Java property files.
     *
     * @param storagePath
     * @param type
     * @return the settings store
     */
    public ISettingsStore newFileStore(final File storagePath,
                                       final Class<?> type)
    {
        final IBackingStore backingStoreWrapper =
                            new PropertiesBackingStoreImpl(
                EFileFormat.FILE_FORMAT_PLAIN_TEXT, storagePath);
        return this.createNewStore(backingStoreWrapper, type);
    }

    /**
     * Returns a new instance of the {@code ISettingsStore} interface for the
     * specified {@code storagePath} and the specified {@code type}, which must
     * be annotated with the {@code PropertyClass} annotation.
     *
     * The backing store will use the file system and standard XML format Java
     * property files.
     *
     * @param storagePath
     * @param type
     * @return the settings store
     */
    public ISettingsStore newXMLFileStore(final File storagePath,
                                          final Class<?> type)
    {
        final IBackingStore backingStoreWrapper =
                            new PropertiesBackingStoreImpl(
                EFileFormat.FILE_FORMAT_XML, storagePath);
        return this.createNewStore(backingStoreWrapper, type);
    }

    /**
     * Returns a new instance of the {@code ISettingsStore} interface for the
     * specified {@code fileFormat}, the specified {@code storagePath}, and, the
     * specified {@code type}, which must be annotated with the
     * {@code PropertyClass} annotation.
     *
     * @param backingStoreWrapper
     * @param type
     * @return the settings store
     */
    public ISettingsStore createNewStore(
            final IBackingStore backingStoreWrapper,
            final Class<?> type)
    {
        IAccessor rootAccessor = RootAccessorFactory.newInstance()
                .buildRootAccessor(type);
        return new SettingsStoreImpl(backingStoreWrapper, rootAccessor, type);
    }
}
