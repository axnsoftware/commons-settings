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

import java.util.List;

/**
 * The abstract class AbstractListItemAccessorImpl models the root of a
 * hierarchy of derived implementation classes and it provides the default
 * behaviour for all implementations of the {@code IContainerItemAccessor}
 * interface for all {@code List} like properties.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractListItemAccessorImpl
        extends AbstractContainerItemAccessorImpl<Integer>
{

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(final Object settingsRoot)
    {
        Object result = null;
        final List<Object> container = (List<Object>) this.getParentAccessor()
                .getValue(settingsRoot);
        final Integer itemKey = this.getItemKey();
        if (itemKey.intValue() < container.size())
        {
            result = container.get(itemKey);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final void setValue(final Object value, final Object settingsRoot)
    {
        final List<Object> container = (List<Object>) this.getParentAccessor()
                .getValue(settingsRoot);
        final Integer itemKey = this.getItemKey();
        if (itemKey.intValue() < container.size())
        {
            container.set(itemKey, value);
        }
        else
        {
            container.add(value);
        }
    }
}
