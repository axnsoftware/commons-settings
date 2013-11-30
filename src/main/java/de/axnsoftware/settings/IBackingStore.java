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

import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 * The interface IBackingStore models a wrapper for a backing store which
 * is responsible for both loading and storing configuration data.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IBackingStore {

    /**
     * Returns a copy of the loaded properties.
     *
     * This will throw an {@code IllegalStateException} if the properties have
     * not been loaded.
     *
     * @return the properties
     * @throws IllegalStateException
     */
    public Object getProperties();

    /**
     * Deletes the properties. Depending on the implementation this could mean
     * that the file will be removed from the file system, or that the entries
     * in a registry will be removed.
     *
     * @throws BackingStoreException
     */
    void deleteProperties() throws BackingStoreException;

    /**
     * Gets the value of the property identified by the specified {@code key}.
     *
     * @param key
     * @return the value or null
     */
    String getProperty(final String key);

    /**
     * Returns all available keys from the underlying properties object.
     *
     * @return all available keys in no specific order
     * @throws BackingStoreException
     */
    Set<String> keySet() throws BackingStoreException;

    /**
     * Loads the properties into memory.
     *
     * @throws BackingStoreException
     */
    void loadProperties() throws BackingStoreException;

    /**
     * Sets the value of the property identified by the specified {@code key}.
     *
     * @param key
     * @param value
     */
    void setProperty(final String key, final String value);

    /**
     * Stored the properties and makes them permanent.
     *
     * @throws BackingStoreException
     */
    void storeProperties() throws BackingStoreException;
}
