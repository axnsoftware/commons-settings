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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

/**
 * The final class SettingsStoreImpl models a concrete implementation for the
 * {@code ISettingsStore} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SettingsStoreImpl implements ISettingsStore {

    private final Class<?> type;
    private IBackingStore backingStoreWrapper;
    private final IAccessor rootAccessor;

    public SettingsStoreImpl(final IBackingStore backingStoreWrapper, final IAccessor rootAccessor, final Class<?> type) {
        this.backingStoreWrapper = backingStoreWrapper;
        this.rootAccessor = rootAccessor;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISettings loadSettings() throws BackingStoreException {
        ISettings result = null;
        try {
            Object settingsRoot = this.type.newInstance();
            this.backingStoreWrapper.loadProperties();
            this.rootAccessor.readFromProperties(this.backingStoreWrapper, settingsRoot);
            result = new SettingsImpl(settingsRoot, this.rootAccessor, this);
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger(SettingsStoreImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSettings() throws BackingStoreException {
        if (null == this.backingStoreWrapper) {
            throw new IllegalStateException("TODO:backing store has not been loaded.");
        }
        this.backingStoreWrapper.deleteProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeSettings(final ISettings settings) throws BackingStoreException {
        if (null == this.backingStoreWrapper) {
            throw new IllegalStateException("TODO:backing store has not been loaded.");
        }
        this.rootAccessor.writeToProperties(this.backingStoreWrapper, settings.getProperties());
        this.backingStoreWrapper.storeProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBackingStore getBackingStoreWrapper() {
        if (null == this.backingStoreWrapper) {
            throw new IllegalStateException("TODO:backing store has not been loaded.");
        }
        return this.backingStoreWrapper;
    }
}
