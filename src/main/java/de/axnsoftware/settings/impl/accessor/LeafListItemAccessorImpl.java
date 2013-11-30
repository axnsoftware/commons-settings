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
import java.util.Map;

/**
 * The final class LeafListItemAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, responsible for accessing simple type items
 * of {@code List}S, such as {@code Integer} or {@code Enum}S.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class LeafListItemAccessorImpl extends AbstractListItemAccessorImpl {

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
    public void readFromBackingStore(final IBackingStore backingStore, final Object settingsRoot) {
        final Class<?> type = this.getType();
        final Object value = this.getTypeMappings().get(type).readFromBackingStore(backingStore, this.getQualifiedKey(), type);
        this.setValue(value, settingsRoot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToBackingStore(final IBackingStore backingStore, final Object settingsRoot) {
        this.getTypeMappings().get(this.getType()).writeToBackingStore(backingStore, this.getQualifiedKey(), this.getValue(settingsRoot));
    }
}
