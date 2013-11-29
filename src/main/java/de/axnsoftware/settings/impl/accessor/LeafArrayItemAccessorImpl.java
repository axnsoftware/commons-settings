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

import de.axnsoftware.settings.ITypeMapper;
import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;
import java.util.Map;

/**
 * The final class LeafArrayItemAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, responsible for accessing simple type items
 * of {@code Array}S, such as {@code Integer} or {@code Enum}S.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class LeafArrayItemAccessorImpl extends AbstractArrayItemAccessorImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyValue(final Object source, final Object target) {
        Map<Class<?>, ITypeMapper> typeMappings = this.getTypeMappings();
        Object copy = typeMappings.get(this.getType()).copyOf(this.getValue(source));
        this.setValue(copy, target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readFromProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        final Class<?> type = this.getType();
        final Object value = this.getTypeMappings().get(type).valueOf(properties.getProperty(this.getQualifiedKey()), type);
        this.setValue(value, settingsRoot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        String value = this.getTypeMappings().get(this.getType()).valueOf(this.getValue(settingsRoot));
        properties.setProperty(this.getQualifiedKey(), value);
    }
}
