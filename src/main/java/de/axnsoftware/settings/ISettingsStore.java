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

import java.util.prefs.BackingStoreException;

/**
 * The interface ISettingsStore models a service for loading and storing
 * properties from and to an underlying backing store represented by an instance
 * of the {@code IBackingStore} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface ISettingsStore
{

    /**
     * Returns a wrapper for the underlying backing store.
     *
     * This will throw an {@code IllegalStateException} if the settings have not
     * been loaded yet.
     *
     * @return the underlying backing store
     * @throws IllegalStateException
     * @see IBackingStore
     */
    IBackingStore getBackingStoreWrapper();

    /**
     * Returns the type representing the root {@link PropertyClass}, that was
     * registered with the store upon its creation.
     *
     * @return the type of the root {@code PropertyClass}
     */
    Class<?> getType();

    /**
     * Returns the settings loaded from the backing store.
     *
     * @return the settings
     * @throws BackingStoreException
     */
    ISettings loadSettings() throws BackingStoreException;

    /**
     * Deletes the settings from the underlying backing store.
     *
     * @throws BackingStoreException
     */
    void deleteSettings() throws BackingStoreException;

    /**
     * Stores the specified {@code settings} in the underlying backing store.
     *
     * @param settings
     * @throws BackingStoreException
     */
    void storeSettings(final ISettings settings) throws BackingStoreException;
}
