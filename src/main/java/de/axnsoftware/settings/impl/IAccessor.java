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

import de.axnsoftware.settings.ITypeMapper;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface IAccessor extends Cloneable {

    public Object clone();

    public void copyValue(final Object source, final Object target);

    public List<IAccessor> getChildAccessors();

    public String getKey();

    public IAccessor getParentAccessor();

    public String getQualifiedKey();

    public IAccessor getRootAccessor();

    public Class<?> getType();

    public Map<Class<?>, ITypeMapper> getTypeMappings();

    public Object getValue(final Object settingsRoot);

    public void readFromProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot);

    public void setChildAccessors(final List<IAccessor> childAccessors);

    public void setKey(final String key);

    public void setParentAccessor(final IAccessor parentAccessor);

    public void setType(final Class<?> type);

    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot);
}
