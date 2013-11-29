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
 * The interface ITypeMapper models a mapper that is responsible for rendering a
 * given object to a {@code String}, instantiating a given object from a
 * {@code String}, and producing copies of an existing object.
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
     * Returns an instance of the specified {@code type} for the specified
     * {@code value}.
     *
     * This will throw an {@code IllegalArgumentException} if either this does
     * not support the specified {@code type}, or the specified {@code value}
     * cannot be parsed.
     *
     * @param value
     * @param type
     * @return the instance or null
     * @throws IllegalArgumentException
     */
    public Object valueOf(final String value, final Class<?> type);

    /**
     * Returns a string representation of the specified {@code value}.
     *
     * This will throw an {@code IllegalArgumentException} if the type of the
     * {@code value} is not supported.
     *
     * @param value
     * @return string representation of the specified value or null
     * @throws IllegalArgumentException
     */
    public String valueOf(final Object value);

    /**
     * Returns a copy of the specified {@code value}.
     *
     * This will throw an {@code IllegalArgumentException} if the type of the
     * {@code value} is not supported.
     *
     * @param value
     * @return a copy of the specified value or null
     * @throws IllegalArgumentException
     */
    public Object copyOf(final Object value);
}
