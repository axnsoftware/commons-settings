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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * TODO:document
 *
 * Note: Currently unused, serves as an integration example for
 * java.util.prefs.Preferences
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class PreferencesBackingStoreWrapperImpl implements IBackingStore {

    private final String storagePath;
    private Preferences preferences;

    public PreferencesBackingStoreWrapperImpl(final String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public void deleteProperties() throws BackingStoreException {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        this.preferences.clear();
    }

    @Override
    public Object getProperties() {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        return this.preferences;
    }

    @Override
    public String getProperty(final String key) {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        return this.preferences.get(key, null);
    }

    @Override
    public Set<String> keySet() throws BackingStoreException {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        Set<String> result = new HashSet<>();
        result.addAll(Arrays.asList(this.preferences.keys()));
        return result;
    }

    @Override
    public void loadProperties() throws BackingStoreException {
        this.preferences = Preferences.userRoot().node(this.storagePath);
    }

    @Override
    public void setProperty(final String key, final String value) {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        this.preferences.put(key, value);
    }

    @Override
    public void storeProperties() throws BackingStoreException {
        if (null == this.preferences) {
            throw new IllegalStateException("TODO:preferences have not been loaded.");
        }
        this.preferences.sync();
    }
}
