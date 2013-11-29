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
 * TODO:document
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface ISettingsStore {

    /**
     * Returns a wrapper for the underlying backing store.
     *
     * This will throw an {@code IllegalStateException} if the settings have not
     * been loaded yet.
     *
     * @return
     * @throws IllegalStateException
     * @see IBackingStoreWrapper
     */
    public IBackingStoreWrapper getBackingStoreWrapper();

    /**
     * Returns the {@code type} representing the root {@link PropertyClass},
     * that was registered with the store upon its creation.
     *
     * @return the {@code type} of the root {@code PropertyClass}
     */
    public Class<?> getType();

    /**
     * TODO:document
     *
     * @return
     * @throws BackingStoreException
     */
    public ISettings loadSettings() throws BackingStoreException;

    /**
     * TODO:document
     *
     * @throws BackingStoreException
     */
    public void deleteSettings() throws BackingStoreException;

    /**
     * TODO:document
     *
     * @param settings
     * @throws BackingStoreException
     */
    public void storeSettings(final ISettings settings) throws BackingStoreException;
}
