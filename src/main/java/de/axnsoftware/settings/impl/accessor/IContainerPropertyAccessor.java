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

/**
 * The interface IContainerPropertyAccessor models an accessor for all container
 * like properties such as {@code Array}s, {@code List}s, and {@code Map}s.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IContainerPropertyAccessor extends IPropertyAccessor {

    /**
     * Returns the item accessor acting as a template for accessing individual
     * items in the container.
     *
     * @return the item accessor template
     */
    public IAccessor getItemAccessorTemplate();

    /**
     * Sets the item accessor template.
     *
     * @param itemAccessorTemplate
     */
    public void setItemAccessorTemplate(final IAccessor itemAccessorTemplate);
}
