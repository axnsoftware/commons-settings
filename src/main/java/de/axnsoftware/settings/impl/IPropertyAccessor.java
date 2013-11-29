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
package de.axnsoftware.settings.impl;

import java.lang.reflect.Method;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IPropertyAccessor extends IAccessor {

    public DefaultValueHolder getDefaultValueHolder();

    public Method getGetter();

    public Method getSetter();

    public void setDefaultValueHolder(final DefaultValueHolder defaultValueHolder);

    public void setGetter(final Method getter);

    public void setSetter(final Method setter);

    public void setValue(final Object value, final Object settingsRoot);
}
