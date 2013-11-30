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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 * The final class MapPropertyAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, responsible for accessing all {@code Map}
 * type properties.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class MapPropertyAccessorImpl extends AbstractContainerPropertyAccessorImpl {

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = super.getValue(settingsRoot);
        if (null == result) {
            result = new HashMap<>();
            this.setValue(result, settingsRoot);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readFromBackingStore(final IBackingStore backingStore, final Object settingsRoot) {
        final String key = this.getQualifiedKey();
        final List<String> itemKeys = new ArrayList<>();
        final List<String> sortedPropertyNames = new ArrayList<>();
        try {
            sortedPropertyNames.addAll(backingStore.keySet());
            Collections.sort(sortedPropertyNames);
            String currentKey = null;
            for (String propertyName : sortedPropertyNames) {
                if (null != currentKey && propertyName.startsWith(currentKey)) {
                    continue;
                }
                if (propertyName.startsWith(key)) {
                    int dotPosition = propertyName.indexOf('.', key.length() + 1);
                    if (-1 == dotPosition) {
                        currentKey = propertyName;
                    } else {
                        currentKey = propertyName.substring(0, dotPosition);
                    }
                    itemKeys.add(currentKey.replace(key + ".", ""));
                }
            }
            this.setValue(this.getType().cast(new HashMap<String, Object>()), settingsRoot);
            for (final String itemKey : itemKeys) {
                IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
                accessor.setItemKey(itemKey);
                accessor.readFromBackingStore(backingStore, settingsRoot);
            }
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToBackingStore(final IBackingStore backingStore, final Object settingsRoot) {
        final Map<String, Object> items = (Map<String, Object>) this.getValue(settingsRoot);
        for (final String itemKey : items.keySet()) {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
            accessor.setItemKey(itemKey);
            accessor.writeToBackingStore(backingStore, settingsRoot);
        }
    }
}
