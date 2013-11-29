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
import de.axnsoftware.settings.ISettingsStore;

/**
 * The final class SettingsImpl models a concrete implementation of the
 * {@code ISettings} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SettingsImpl implements ISettings {

    private Object dirtyProperties;
    private Object properties;
    private final IAccessor rootAccessor;
    private final ISettingsStore settingsStore;

    public SettingsImpl(final Object properties, final IAccessor rootAccessor, final ISettingsStore settingsStore) {
        this.properties = properties;
        this.rootAccessor = rootAccessor;
        this.settingsStore = settingsStore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discardChanges() {
        this.dirtyProperties = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finalizeChanges() {
        if (this.getHasPendingChanges()) {
            this.properties = this.dirtyProperties;
            this.dirtyProperties = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getHasPendingChanges() {
        return this.dirtyProperties != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperties() {
        Object source = this.properties;
        if (this.getHasPendingChanges()) {
            source = this.dirtyProperties;
        }
        return this.copyProperties(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISettingsStore getStore() {
        return this.settingsStore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getType() {
        return this.getStore().getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(final Object properties) {
        this.dirtyProperties = this.copyProperties(properties);
    }

    /**
     * Creates a copy of the specified {@code source}.
     *
     * @param source
     * @return the copied object
     */
    private Object copyProperties(final Object source) {
        Object result = null;
        try {
            result = this.getStore().getType().newInstance();
            this.rootAccessor.copyValue(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
