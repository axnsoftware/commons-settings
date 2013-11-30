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
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 * The final class PropertiesFileBackingStoreWrapperImpl models a concrete
 * implementation of the {@code IBackingStoreWrapper} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class PropertiesFileBackingStoreWrapperImpl implements IBackingStore {

    private final EFileFormat fileFormat;
    private final File storagePath;
    private Properties properties;

    public PropertiesFileBackingStoreWrapperImpl(final EFileFormat fileFormat, final File storagePath) {
        this.fileFormat = fileFormat;
        this.storagePath = storagePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProperties() throws BackingStoreException {
        try {
            Path path = FileSystems.getDefault().getPath(this.storagePath.getAbsolutePath());
            Files.delete(path);
        } catch (IOException e) {
            throw new BackingStoreException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperties() {
        if (null == this.properties) {
            throw new IllegalStateException("TODO:properties have not been loaded.");
        }
        return (Properties) this.properties.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(final String key) {
        if (null == this.properties) {
            throw new IllegalStateException("TODO:properties have not been loaded.");
        }
        return this.properties.getProperty(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet() throws BackingStoreException {
        if (null == this.properties) {
            throw new IllegalStateException("TODO:properties have not been loaded.");
        }
        return this.properties.stringPropertyNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProperties() throws BackingStoreException {
        if (this.storagePath.exists()) {
            this.properties = new Properties();
            try {
                final InputStream inputStream = new FileInputStream(this.storagePath);
                try {
                    if (this.fileFormat.equals(EFileFormat.FILE_FORMAT_PLAIN_TEXT)) {
                        this.properties.load(inputStream);
                    } else {
                        this.properties.loadFromXML(inputStream);
                    }
                } catch (IOException e) {
                    throw new BackingStoreException(e);
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new BackingStoreException(e);
                    }
                }
            } catch (IOException e) {
                throw new BackingStoreException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperty(final String key, final String value) {
        if (null == this.properties) {
            throw new IllegalStateException("TODO:properties have not been loaded.");
        }
        this.properties.setProperty(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeProperties() throws BackingStoreException {
        if (null == this.properties) {
            throw new IllegalStateException("TODO:properties have not been loaded.");
        }
        try {
            final OutputStream outputStream = new FileOutputStream(this.storagePath);
            try {
                if (this.fileFormat.equals(EFileFormat.FILE_FORMAT_PLAIN_TEXT)) {
                    this.properties.store(outputStream, null);
                } else {
                    this.properties.storeToXML(outputStream, null, "utf-8");
                }
            } catch (IOException e) {
                throw new BackingStoreException(e);
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new BackingStoreException(e);
                }
            }
        } catch (IOException e) {
            throw new BackingStoreException(e);
        }
    }
}
