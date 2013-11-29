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

import de.axnsoftware.settings.IBackingStoreWrapper;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public interface IMutableBackingStoreWrapper extends IBackingStoreWrapper {

    /**
     * TODO:document
     *
     * @throws BackingStoreException
     */
    public void deleteProperties() throws BackingStoreException;

    /**
     * TODO:document
     *
     * @throws BackingStoreException
     */
    public void loadProperties() throws BackingStoreException;

    /**
     * TODO:document
     *
     * @param key
     * @return
     */
    public String getProperty(final String key);

    /**
     * Returns all available keys from the underlying properties object.
     *
     * @return all available keys in no specific order
     * @throws BackingStoreException
     */
    public Set<String> keySet() throws BackingStoreException;

    /**
     * TODO:document
     *
     * @param key
     * @param value
     */
    public void setProperty(final String key, final String value);

    /**
     * TODO:document
     *
     * @throws BackingStoreException
     */
    public void storeProperties() throws BackingStoreException;
}
