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

package eu.coldrye.settings;

import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 * The interface BackingStore models a backing store which is responsible for
 * both retrieving configuration data from and persistently storing
 * configuration data to an underlying storage.
 *
 * @since 1.0.0
 */
public interface BackingStore {

  /**
   * Returns a copy of the loaded properties.
   * <p>
   * This will throw an {@code IllegalStateException} if the properties have
   * not been loaded.
   *
   * @return the properties
   * @throws BackingStoreException
   */
  Object getProperties() throws BackingStoreException;

  /**
   * Deletes the properties. Depending on the implementation this could mean
   * that the file will be removed from the file system, or that the entries
   * in a registry will be removed.
   *
   * @throws BackingStoreException
   */
  void deleteProperties() throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   * @throws BackingStoreException
   */
  Boolean getBoolean(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   */
  Byte getByte(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   * @throws BackingStoreException
   */
  Character getCharacter(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   * @throws BackingStoreException
   */
  Double getDouble(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   */
  Float getFloat(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   * @throws BackingStoreException
   */
  Integer getInteger(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   */
  Long getLong(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   * @throws BackingStoreException
   */
  Short getShort(String key) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @return the value or null
   */
  String getString(String key) throws BackingStoreException;

  /**
   * Returns all available keys from the underlying properties object.
   *
   * @return all available keys in no specific order
   * @throws BackingStoreException
   */
  Set<String> keySet() throws BackingStoreException;

  /**
   * Loads the properties into memory.
   *
   * @throws BackingStoreException
   */
  void loadProperties() throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setBoolean(String key, Object value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   */
  void setByte(String key, Byte value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setCharacter(String key, Character value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setDouble(String key, Double value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setFloat(String key, Float value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setInteger(String key, Integer value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setLong(String key, Long value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setShort(String key, Short value) throws BackingStoreException;

  /**
   * Gets the value of the property identified by the specified {@code key}.
   *
   * @param key
   * @param value
   * @throws BackingStoreException
   */
  void setString(String key, String value) throws BackingStoreException;

  /**
   * Stored the properties and makes them permanent.
   *
   * @throws BackingStoreException
   */
  void storeProperties() throws BackingStoreException;
}
