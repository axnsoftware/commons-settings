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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.BackingStoreException;

/**
 * The final class PropertiesBackingStoreImpl models a concrete implementation
 * of the {@code IBackingStoreWrapper} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class PropertiesBackingStoreImpl
        implements
        IBackingStore
{

    private static class OrderedProperties
            extends Properties
    {

        @Override
        public synchronized Enumeration<Object> keys()
        {
            return Collections.enumeration(new TreeSet<>(super.keySet()));
        }
    }
    private final EFileFormat fileFormat;
    private final File storagePath;
    private Properties properties;

    public PropertiesBackingStoreImpl(final EFileFormat fileFormat,
                                      final File storagePath)
    {
        this.fileFormat = fileFormat;
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
    public Boolean getBoolean(final String key)
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
    public Byte getByte(final String key)
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
    public Character getCharacter(final String key)
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
    public Double getDouble(final String key)
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
    public Float getFloat(final String key)
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
    public Integer getInteger(final String key)
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
    public Long getLong(final String key)
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
    public Object getProperties()
    {
        if (null == this.properties)
        {
            throw new IllegalStateException(
                    "TODO:properties have not been loaded.");
        }
        return (Properties) this.properties.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getShort(final String key)
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
    public String getString(final String key)
    {
        return this.properties.getProperty(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet() throws BackingStoreException
    {
        if (null == this.properties)
        {
            throw new IllegalStateException(
                    "TODO:properties have not been loaded.");
        }
        return this.properties.stringPropertyNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProperties() throws BackingStoreException
    {
        if (this.storagePath.exists())
        {
            this.properties = new Properties();
            try
            {
                final InputStream inputStream = new FileInputStream(
                        this.storagePath);
                try
                {
                    if (this.fileFormat.equals(
                            EFileFormat.FILE_FORMAT_PLAIN_TEXT))
                    {
                        this.properties.load(inputStream);
                    }
                    else
                    {
                        this.properties.loadFromXML(inputStream);
                    }
                }
                catch (IOException e)
                {
                    throw new BackingStoreException(e);
                }
                finally
                {
                    try
                    {
                        inputStream.close();
                    }
                    catch (IOException e)
                    {
                        throw new BackingStoreException(e);
                    }
                }
            }
            catch (IOException e)
            {
                throw new BackingStoreException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoolean(final String key, final Object value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setByte(final String key, final Byte value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final String key, final Character value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDouble(final String key, final Double value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFloat(final String key, final Float value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInteger(final String key, final Integer value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLong(final String key, final Long value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShort(final String key, final Short value)
    {
        this.setString(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setString(final String key, final String value)
    {
        this.properties.setProperty(key, value.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeProperties() throws BackingStoreException
    {
        if (null == this.properties)
        {
            throw new IllegalStateException(
                    "TODO:properties have not been loaded.");
        }
        try
        {
            final OutputStream outputStream = new FileOutputStream(
                    this.storagePath);
            try
            {
                OrderedProperties orderedProperties = new OrderedProperties();
                orderedProperties.putAll(this.properties);
                if (this.fileFormat.equals(EFileFormat.FILE_FORMAT_PLAIN_TEXT))
                {
                    orderedProperties.store(outputStream, null);
                }
                else
                {
                    orderedProperties.storeToXML(outputStream, null, "utf-8");
                }
            }
            catch (IOException e)
            {
                throw new BackingStoreException(e);
            }
            finally
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    throw new BackingStoreException(e);
                }
            }
        }
        catch (IOException e)
        {
            throw new BackingStoreException(e);
        }
    }
}
