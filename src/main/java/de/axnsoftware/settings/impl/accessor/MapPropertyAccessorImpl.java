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
import de.axnsoftware.settings.impl.IContainerItemAccessor;
import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class MapPropertyAccessorImpl extends AbstractContainerPropertyAccessorImpl {

    public MapPropertyAccessorImpl() {
        super();
    }

    @Override
    public void copyValue(final Object source, final Object target) {
        final Map<String, Object> sourceMap = (Map<String, Object>) this.getValue(source);
        final Map<String, Object> targetMap = new HashMap<>();
        this.setValue(targetMap, target);
        final Map<Class<?>, ITypeMapper> typeMappings = this.getTypeMappings();
        for (final Map.Entry<String, Object> entry : sourceMap.entrySet()) {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
            accessor.setItemKey(entry.getKey());
            accessor.copyValue(source, target);
        }
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = super.getValue(settingsRoot);
        if (null == result) {
            result = new HashMap<>();
            this.setValue(result, settingsRoot);
        }
        return result;
    }

    @Override
    public void readFromProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        final String key = this.getKey();
        final List<String> itemKeys = new ArrayList<>();
        final List<String> sortedPropertyNames = new ArrayList<>();
        try {
            sortedPropertyNames.addAll(properties.keySet());
            Collections.sort(sortedPropertyNames);
            String currentKey = "";
            for (String propertyName : sortedPropertyNames) {
                if (propertyName.startsWith(currentKey)) {
                    continue;
                }
                if (propertyName.startsWith(key)) {
                    int dotPosition = propertyName.indexOf('.', key.length() + 1);
                    if (-1 == dotPosition) {
                        currentKey = propertyName;
                    } else {
                        currentKey = propertyName.substring(0, dotPosition);
                    }
                    itemKeys.add(currentKey);
                }
            }
            this.setValue(new HashMap<String, Object>(), settingsRoot);
            for (final String itemKey : itemKeys) {
                IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
                accessor.setItemKey(itemKey);
                accessor.readFromProperties(properties, settingsRoot);
            }
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        final Map<String, Object> items = (Map<String, Object>) this.getValue(settingsRoot);
        for (final String itemKey : items.keySet()) {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
            accessor.setItemKey(itemKey);
            accessor.writeToProperties(properties, settingsRoot);
        }
    }
}
