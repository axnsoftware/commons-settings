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

import java.lang.reflect.Method;

/**
 * The interface IPropertyAccessor models an accessor for accessing the
 * annotated fields in a POJO.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IPropertyAccessor extends IAccessor {

    /**
     * Gets the default value holder.
     *
     * @return the default value holder or null
     */
    public DefaultValueHolder getDefaultValueHolder();

    /**
     * Gets the getter.
     *
     * @return the getter
     */
    public Method getGetter();

    /**
     * Gets the setter.
     *
     * @return the setter
     */
    public Method getSetter();

    /**
     * Replaces the existing default value holder with the specified
     * {@code defaultValueHolder}.
     *
     * @param defaultValueHolder
     */
    public void setDefaultValueHolder(final DefaultValueHolder defaultValueHolder);

    /**
     * Replaces the existing getter with the specified {@code getter}.
     *
     * @param getter
     */
    public void setGetter(final Method getter);

    /**
     * Replaces the existing setter with the specified {@code setter}.
     *
     * @param setter
     */
    public void setSetter(final Method setter);

    /**
     * Sets the value of the property represented by this to the specified
     * {@code value} in the specified {@code settingsRoot}.
     *
     * @param value
     * @param settingsRoot
     */
    public void setValue(final Object value, final Object settingsRoot);
}
