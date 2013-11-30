/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.examples.settings.JME3AppSettingsExample;

import com.jme3.system.AppSettings;
import de.axnsoftware.settings.IBackingStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class JME3AppSettingsBackingStoreImpl implements IBackingStore {

    private AppSettings appSettings;
    private File storagePath;

    public JME3AppSettingsBackingStoreImpl(final File storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public Object getProperties() {
        return this.appSettings.clone();
    }

    @Override
    public void deleteProperties() throws BackingStoreException {
        if (this.storagePath.exists()) {
            this.storagePath.delete();
        }
    }

    @Override
    public Boolean getBoolean(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Byte getByte(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Character getCharacter(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Double getDouble(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Float getFloat(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Integer getInteger(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Long getLong(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public Short getShort(String key) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public String getString(String key) {
        return this.appSettings.getString(key);
    }

    @Override
    public Set<String> keySet() throws BackingStoreException {
        return this.appSettings.keySet();
    }

    @Override
    public void loadProperties() throws BackingStoreException {
        this.appSettings = new AppSettings(true);
        if (this.storagePath.exists()) {
            try {
                this.appSettings.load(new FileInputStream(this.storagePath));
            } catch (IOException e) {
                throw new BackingStoreException(e);
            }
        }
    }

    @Override
    public void setBoolean(String key, Object value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setByte(String key, Byte value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setCharacter(String key, Character value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setDouble(String key, Double value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setFloat(String key, Float value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setInteger(String key, Integer value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setLong(String key, Long value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setShort(String key, Short value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void setString(String key, String value) {
        throw new UnsupportedOperationException("not implemented yet.");
    }

    @Override
    public void storeProperties() throws BackingStoreException {
        try {
            this.appSettings.save(new FileOutputStream(this.storagePath));
        } catch (IOException e) {
            throw new BackingStoreException(e);
        }
    }
}
