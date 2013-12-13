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
public final class PreferencesBackingStoreImpl
        implements IBackingStore
{

    private final String storagePath;
    private Preferences preferences;

    public PreferencesBackingStoreImpl(final String storagePath)
    {
        this.storagePath = storagePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProperties() throws BackingStoreException
    {
        if (null == this.preferences)
        {
            throw new IllegalStateException(
                    "TODO:preferences have not been loaded.");
        }
        this.preferences.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBoolean(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte getByte(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getCharacter(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getDouble(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Float getFloat(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getInteger(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getLong(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperties()
    {
        if (null == this.preferences)
        {
            throw new IllegalStateException(
                    "TODO:preferences have not been loaded.");
        }
        return this.preferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getShort(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(final String key)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet() throws BackingStoreException
    {
        if (null == this.preferences)
        {
            throw new IllegalStateException(
                    "TODO:preferences have not been loaded.");
        }
        Set<String> result = new HashSet<>();
        result.addAll(Arrays.asList(this.preferences.keys()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProperties() throws BackingStoreException
    {
        this.preferences = Preferences.userRoot().node(this.storagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoolean(final String key, final Object value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setByte(final String key, final Byte value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final String key, final Character value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDouble(final String key, final Double value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFloat(final String key, final Float value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInteger(final String key, final Integer value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLong(final String key, final Long value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShort(final String key, final Short value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setString(final String key, final String value)
    {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeProperties() throws BackingStoreException
    {
        if (null == this.preferences)
        {
            throw new IllegalStateException(
                    "TODO:preferences have not been loaded.");
        }
        this.preferences.sync();
    }
}
