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

import de.axnsoftware.settings.util.OrderedProperties;
import de.axnsoftware.settings.EFileFormat;
import de.axnsoftware.settings.IBackingStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 * The final class PropertiesBackingStoreImpl models a concrete implementation
 * of the {@link IBackingStore} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class PropertiesBackingStoreImpl
        implements
        IBackingStore
{

    private final EFileFormat fileFormat;
    private final File storagePath;
    private final Properties properties;

    public PropertiesBackingStoreImpl(final EFileFormat fileFormat,
                                      final File storagePath)
    {
        if (null == fileFormat)
        {
            throw new IllegalArgumentException("fileFormat must not be null.");
        }
        if (null == storagePath)
        {
            throw new IllegalArgumentException("storagePath must not be null.");
        }

        this.fileFormat = fileFormat;
        this.properties = new Properties();
        this.storagePath = storagePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProperties() throws BackingStoreException
    {
        try
        {
            this.properties.clear();
            Path path = FileSystems.getDefault().getPath(this.storagePath
                    .getAbsolutePath());
            Files.delete(path);
        }
        catch (IOException e)
        {
            throw new BackingStoreException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBoolean(final String key) throws BackingStoreException
    {
        Boolean result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Boolean.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte getByte(final String key) throws BackingStoreException
    {
        Byte result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Byte.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getCharacter(final String key) throws BackingStoreException
    {
        Character result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Character.valueOf(value.charAt(0));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getDouble(final String key) throws BackingStoreException
    {
        Double result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Double.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Float getFloat(final String key) throws BackingStoreException
    {
        Float result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Float.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getInteger(final String key) throws BackingStoreException
    {
        Integer result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Integer.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getLong(final String key) throws BackingStoreException
    {
        Long result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Long.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperties() throws BackingStoreException
    {
        return (Properties) this.properties.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getShort(final String key) throws BackingStoreException
    {
        Short result = null;
        String value = this.getString(key);
        if (null != value)
        {
            result = Short.valueOf(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(final String key) throws BackingStoreException
    {
        return this.properties.getProperty(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet() throws BackingStoreException
    {
        return this.properties.stringPropertyNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProperties() throws BackingStoreException
    {
        try (final InputStream inputStream = new FileInputStream(
                this.storagePath))
        {
            this.properties.clear();
            if (this.fileFormat.equals(EFileFormat.PLAIN_TEXT))
            {
                this.properties.load(inputStream);
            }
            else
            {
                this.properties.loadFromXML(inputStream);
            }
        }
        catch (final IOException e)
        {
            throw new BackingStoreException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoolean(final String key, final Object value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setByte(final String key, final Byte value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final String key, final Character value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDouble(final String key, final Double value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFloat(final String key, final Float value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInteger(final String key, final Integer value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLong(final String key, final Long value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShort(final String key, final Short value) throws
            BackingStoreException
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setString(final String key, final String value) throws
            BackingStoreException
    {
        this.properties.setProperty(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeProperties() throws BackingStoreException
    {
        try (final OutputStream outputStream = new FileOutputStream(
                this.storagePath))
        {
            final OrderedProperties orderedProperties = new OrderedProperties();
            orderedProperties.putAll(this.properties);
            if (this.fileFormat.equals(EFileFormat.PLAIN_TEXT))
            {
                orderedProperties.store(outputStream, null);
            }
            else
            {
                orderedProperties.storeToXML(outputStream, null, "utf-8");
            }
        }
        catch (final IOException e)
        {
            throw new BackingStoreException(e);
        }
    }
}
