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

/**
 * The final class LeafPropertyAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, responsible for accessing simple type
 * fields, such as {@code Integer} or {@code Enum}S.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class LeafPropertyAccessorImpl
        extends AbstractPropertyAccessorImpl
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyValue(final Object source, final Object target)
    {
        Object value = this.getValue(source);
        if (value != null)
        {
            ITypeMapper mapper = this.getTypeMappings().get(value.getClass());
            Object copy = mapper.copyOf(value);
            this.setValue(copy, target);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readFromBackingStore(final IBackingStore backingStore,
                                     final Object settingsRoot)
    {
        Object value = this.getTypeMappings().get(this.getType())
                .readFromBackingStore(backingStore, this.getQualifiedKey(), this
                .getType());
        if (value != null)
        {
            this.setValue(value, settingsRoot);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToBackingStore(final IBackingStore backingStore,
                                    final Object settingsRoot)
    {
        Object value = this.getValue(settingsRoot);
        if (value != null)
        {
            this.getTypeMappings().get(this.getType()).writeToBackingStore(
                    backingStore, this.getQualifiedKey(), value);
        }
    }
}
