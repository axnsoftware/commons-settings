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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class ListPropertyAccessorImpl extends AbstractContainerPropertyAccessorImpl {

    public ListPropertyAccessorImpl() {
        super();
    }

    @Override
    public void copyValue(final Object source, final Object target) {
        final List<Object> sourceList = (List<Object>) this.getValue(source);
        final List<Object> targetList = new ArrayList<>();
        this.setValue(targetList, target);
        final Map<Class<?>, ITypeMapper> typeMappings = this.getTypeMappings();
        for (int index = 0; index < sourceList.size(); index++) {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
            accessor.setItemKey(Integer.valueOf(index));
            accessor.copyValue(source, target);
        }
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = super.getValue(settingsRoot);
        if (null == result) {
            result = new ArrayList<>();
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
            int nextProvableItemKey = 0;
            String provableKey = key + "." + nextProvableItemKey;
            for (String propertyName : sortedPropertyNames) {
                if (propertyName.startsWith(provableKey)) {
                    itemKeys.add(provableKey);
                    nextProvableItemKey++;
                    provableKey = key + "." + nextProvableItemKey;
                }
            }
            final List<Object> items = new ArrayList<>();
            this.setValue(items, settingsRoot);
            for (int index = 0; index < itemKeys.size(); index++) {
                IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
                accessor.setItemKey(index);
                accessor.readFromProperties(properties, settingsRoot);
            }
            // in case the user or the program created gaps in the order of indices, we will close these now
            if (items.size() > itemKeys.size()) {
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    if (null == iterator.next()) {
                        iterator.remove();
                    }
                }
            }
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        final List<Object> items = (List<Object>) this.getValue(settingsRoot);
        for (int index = 0; index < items.size(); index++) {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this.getItemAccessorTemplate().clone();
            accessor.setItemKey(index);
            accessor.writeToProperties(properties, settingsRoot);
        }
    }
}
