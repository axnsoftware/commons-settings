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
package de.axnsoftware.settings;

/**
 * The interface ITypeMapper models a mapper that is responsible for reading and
 * writing a given object from and to a given backing store. The mapper is
 * responsible for any transformations that need to be applied for a given
 * object to be written to and read from the backing store.
 *
 * By default, the system will use a default implementation capable of mapping
 * all of the standard numeric types, found in both java.lang and java.math,
 * including java.lang.Character, java.lang.String and java.util.UUID. It also
 * acts as a fall back for all enum types for which no custom type mappers have
 * been defined.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface ITypeMapper {

    /**
     * Returns a copy of the specified {@code value}.
     *
     * @param value
     * @return a copy of the specified value or null
     * @throws IllegalArgumentException
     */
    public Object copyOf(final Object value);

    /**
     * Returns an instance of the specified {@code key} and {@code type} which
     * is read from the specified {@code backingStore}.
     *
     * @param backingStore
     * @param key
     * @param type
     * @return the instance or null
     * @throws IllegalArgumentException
     */
    public Object readFromBackingStore(final IBackingStore backingStore, final String key, final Class<?> type);

    /**
     * Returns an instance of the specified {@code type} for the specified
     * {@code value}.
     *
     * @param value
     * @param type
     * @return instance of type or null
     * @throws IllegalArgumentException
     */
    public Object valueOf(final String value, final Class<?> type);

    /**
     * Writes the specified {@code value} to the specified {@code backingStore}.
     *
     * @param backingStore
     * @param value
     * @throws IllegalArgumentException
     */
    public void writeToBackingStore(final IBackingStore backingStore, final String key, final Object value);
}
