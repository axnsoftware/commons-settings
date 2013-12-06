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
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ITypeMapper;
import java.util.List;
import java.util.Map;

/**
 * The interface IAccessor models the root of a hierarchy of derived interfaces.
 * Accessors provide the system with the ability to traverse an object tree and
 * both retrieve values from and set values in such an object tree.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IAccessor
        extends Cloneable
{

    /**
     * Creates a deep copy of this.
     *
     * @return the copy
     */
    Object clone();

    /**
     * Copies the value of the property represented by this from the specified
     * {@code source} to the specified {@code target}.
     *
     * @param source
     * @param target
     */
    void copyValue(final Object source, final Object target);

    /**
     * Returns the child accessors.
     *
     * @return the child accessors or null
     */
    List<IAccessor> getChildAccessors();

    /**
     * Returns the unqualified key of the property represented by this.
     *
     * @return the key
     */
    String getKey();

    /**
     * Returns the parent accessor.
     *
     * @return the parent accessor or null
     */
    IAccessor getParentAccessor();

    /**
     * Returns the qualified key of the property, e.g. org.example.timeout.
     *
     * @return the qualified key
     */
    String getQualifiedKey();

    /**
     * Gets the root accessor.
     *
     * @return the root accessor or null in case that this is the root accessor
     */
    IAccessor getRootAccessor();

    /**
     * Gets the type of the property represented by this.
     *
     * @return the type of the property
     */
    Class<?> getType();

    /**
     * Gets the available type mappings from the root accessor.
     *
     * @return the available type mappings
     */
    Map<Class<?>, ITypeMapper> getTypeMappings();

    /**
     * Gets the {@code value} of the property represented by this from the
     * specified {@code settingsRoot}.
     *
     * @param settingsRoot
     * @return the value or null
     */
    Object getValue(final Object settingsRoot);

    /**
     * Reads the value of the property represented by this from the specified
     * {@code backingStore} into the specified {@code settingsRoot}.
     *
     * @param backingStore
     * @param settingsRoot
     */
    void readFromBackingStore(final IBackingStore backingStore,
                              final Object settingsRoot);

    /**
     * Replaces the existing child accessors with the specified
     * {@code childAccessors}.
     *
     * @param childAccessors
     */
    void setChildAccessors(final List<IAccessor> childAccessors);

    /**
     * Replaces the existing key with the specified {@code key}.
     *
     * @param key
     */
    void setKey(final String key);

    /**
     * Replaces the existing parent accessor with the specified
     * {@code parentAccessor}.
     *
     * @param parentAccessor
     */
    void setParentAccessor(final IAccessor parentAccessor);

    /**
     * Replaces the existing type with the specified {@code type}.
     *
     * @param type
     */
    void setType(final Class<?> type);

    /**
     * Gets the value of the property represented by this from the specified
     * {@code settingsRoot} and writes it to the specified {@code backingStore}.
     *
     * @param backingStore
     * @param settingsRoot
     */
    void writeToBackingStore(final IBackingStore backingStore,
                             final Object settingsRoot);
}
