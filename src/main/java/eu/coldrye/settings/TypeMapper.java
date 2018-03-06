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

import java.util.prefs.BackingStoreException;

/**
 * The interface TypeMapper models a mapper that is responsible for reading and
 * writing a given object from and to a given backing store. The mapper is
 * responsible for any transformations that need to be applied for a given
 * object to be written to and read from the backing store.
 * <p>
 * By default, the system will use a default implementation capable of mapping
 * all of the standard numeric types, found in both java.lang and java.math,
 * including java.lang.Character, java.lang.String and java.util.UUID. It also
 * acts as a fall back for all enum types for which no custom type mappers have
 * been defined.
 *
 * @since 1.0.0
 */
public interface TypeMapper {

  /**
   * Returns a copy of the specified {@code value}.
   *
   * @param value
   * @return a copy of the specified value or null
   * @throws IllegalArgumentException
   */
  Object copyOf(Object value);

  /**
   * Returns an instance of the specified {@code key} and {@code type} which
   * is read from the specified {@code backingStore}.
   *
   * @param backingStore
   * @param key
   * @param type
   * @return the instance or null
   * @throws BackingStoreException
   */
  Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type) throws BackingStoreException;

  /**
   * Returns an instance of the specified {@code type} for the specified {@code value}.
   *
   * @param value
   * @param type
   * @return instance of type or null
   * @throws IllegalArgumentException
   */
  Object valueOf(String value, Class<?> type);

  /**
   * Writes the specified {@code value} to the specified {@code backingStore}.
   *
   * @param backingStore
   * @param value
   * @throws BackingStoreException
   */
  void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException;
}
