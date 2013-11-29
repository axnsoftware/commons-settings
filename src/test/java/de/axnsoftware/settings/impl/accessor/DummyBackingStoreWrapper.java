/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class DummyBackingStoreWrapper implements IMutableBackingStoreWrapper {

    private Properties properties = new Properties();

    @Override
    public void deleteProperties() throws BackingStoreException {
        this.properties.clear();
    }

    @Override
    public void loadProperties() throws BackingStoreException {
    }

    @Override
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    @Override
    public Set<String> keySet() throws BackingStoreException {
        return this.properties.stringPropertyNames();
    }

    @Override
    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    @Override
    public void storeProperties() throws BackingStoreException {
    }

    @Override
    public Object getProperties() {
        return this.properties.clone();
    }
}
