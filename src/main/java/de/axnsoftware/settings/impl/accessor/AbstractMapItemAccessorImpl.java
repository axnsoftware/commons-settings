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

import java.util.Map;

/**
 * The abstract class AbstractMapItemAccessorImpl models the root of a hierarchy
 * of derived implementation classes and it provides the default behaviour for
 * all implementations of the {@code IContainerItemAccessor} interface for all
 * {@code Map} like properties.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractMapItemAccessorImpl
        extends AbstractContainerItemAccessorImpl<String>
{

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(final Object settingsRoot)
    {
        Map<String, Object> container = (Map<String, Object>) this
                .getParentAccessor().getValue(settingsRoot);
        return container.get(this.getItemKey());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final void setValue(final Object value, final Object settingsRoot)
    {
        Map<String, Object> container = (Map<String, Object>) this
                .getParentAccessor().getValue(settingsRoot);
        container.put(this.getItemKey(), value);
    }
}
