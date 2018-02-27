/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.impl;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.util.OrderedProperties;
import eu.coldrye.settings.FileFormat;

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
 * The class PropertiesBackingStoreImpl models a concrete implementation
 * of the {@link BackingStore} interface.
 *
 * @since 1.0.0
 */
public class PropertiesBackingStoreImpl implements BackingStore {

  private FileFormat fileFormat;

  private File storagePath;

  private Properties properties;

  public PropertiesBackingStoreImpl(FileFormat fileFormat, File storagePath) {

    if (null == fileFormat) {
      throw new IllegalArgumentException("fileFormat must not be null.");
    }
    if (null == storagePath) {
      throw new IllegalArgumentException("storagePath must not be null.");
    }

    this.fileFormat = fileFormat;
    properties = new OrderedProperties();
    this.storagePath = storagePath;
  }

  @Override
  public void deleteProperties() throws BackingStoreException {

    try {
      properties.clear();
      Path path = FileSystems.getDefault().getPath(storagePath.getAbsolutePath());
      Files.delete(path);
    } catch (IOException e) {
      throw new BackingStoreException(e);
    }
  }

  @Override
  public Boolean getBoolean(String key) throws BackingStoreException {

    Boolean result = null;
    String value = getString(key);
    if (null != value) {
      result = Boolean.valueOf(value);
    }
    return result;
  }

  @Override
  public Byte getByte(String key) throws BackingStoreException {

    Byte result = null;
    String value = getString(key);
    if (null != value) {
      result = Byte.valueOf(value);
    }
    return result;
  }

  @Override
  public Character getCharacter(String key) throws BackingStoreException {

    Character result = null;
    String value = getString(key);
    if (null != value) {
      result = Character.valueOf(value.charAt(0));
    }
    return result;
  }

  @Override
  public Double getDouble(String key) throws BackingStoreException {

    Double result = null;
    String value = getString(key);
    if (null != value) {
      result = Double.valueOf(value);
    }
    return result;
  }

  @Override
  public Float getFloat(String key) throws BackingStoreException {

    Float result = null;
    String value = getString(key);
    if (null != value) {
      result = Float.valueOf(value);
    }
    return result;
  }

  @Override
  public Integer getInteger(String key) throws BackingStoreException {

    Integer result = null;
    String value = getString(key);
    if (null != value) {
      result = Integer.valueOf(value);
    }
    return result;
  }

  @Override
  public Long getLong(String key) throws BackingStoreException {

    Long result = null;
    String value = getString(key);
    if (null != value) {
      result = Long.valueOf(value);
    }
    return result;
  }

  @Override
  public Object getProperties() throws BackingStoreException {

    return (Properties) properties.clone();
  }

  @Override
  public Short getShort(String key) throws BackingStoreException {

    Short result = null;
    String value = getString(key);
    if (null != value) {
      result = Short.valueOf(value);
    }
    return result;
  }

  @Override
  public String getString(String key) throws BackingStoreException {

    return properties.getProperty(key);
  }

  @Override
  public Set<String> keySet() throws BackingStoreException {

    return properties.stringPropertyNames();
  }

  @Override
  public void loadProperties() throws BackingStoreException {

    try (InputStream inputStream = new FileInputStream(storagePath)) {
      properties.clear();
      if (fileFormat.equals(FileFormat.PLAIN_TEXT)) {
        properties.load(inputStream);
      } else {
        properties.loadFromXML(inputStream);
      }
    } catch (IOException e) {
      throw new BackingStoreException(e);
    }
  }

  @Override
  public void setBoolean(String key, Object value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setByte(String key, Byte value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setCharacter(String key, Character value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setDouble(String key, Double value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setFloat(String key, Float value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setInteger(String key, Integer value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setLong(String key, Long value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setShort(String key, Short value) throws BackingStoreException {

    setString(key, value.toString());
  }

  @Override
  public void setString(String key, String value) throws BackingStoreException {

    properties.setProperty(key, value.toString());
  }

  @Override
  public void storeProperties() throws BackingStoreException {

    try (OutputStream outputStream = new FileOutputStream(storagePath)) {
      if (fileFormat.equals(FileFormat.PLAIN_TEXT)) {
        properties.store(outputStream, null);
      } else {
        properties.storeToXML(outputStream, null, "utf-8");
      }
    } catch (IOException e) {
      throw new BackingStoreException(e);
    }
  }
}
