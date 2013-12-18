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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * The final class ListPropertyAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, responsible for accessing all {@code List}
 * type properties.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class ListPropertyAccessorImpl
        extends AbstractContainerPropertyAccessorImpl
{

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void copyValue(final Object source, final Object target)
    {
        final List<Object> sourceList = (List<Object>) this.getValue(source);
        final List<Object> targetList = new ArrayList<>();
        this.setValue(targetList, target);
        for (int index = 0; index < sourceList.size(); index++)
        {
            IContainerItemAccessor<Integer> accessor;
            accessor = (IContainerItemAccessor<Integer>) this
                    .getItemAccessorTemplate().clone();
            accessor.setItemKey(Integer.valueOf(index));
            accessor.copyValue(source, target);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(final Object settingsRoot)
    {
        Object result = super.getValue(settingsRoot);
        if (null == result)
        {
            result = new ArrayList<>();
            this.setValue(result, settingsRoot);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void readFromBackingStore(final IBackingStore backingStore,
                                     final Object settingsRoot) throws
            BackingStoreException
    {
        final String key = this.getQualifiedKey();
        final List<String> itemKeys = new ArrayList<>();
        final List<String> sortedPropertyNames = new ArrayList<>();
        sortedPropertyNames.addAll(backingStore.keySet());
        Collections.sort(sortedPropertyNames);
        int nextProvableItemKey = 0;
        String provableKey = key + "." + nextProvableItemKey;
        for (String propertyName : sortedPropertyNames)
        {
            if (propertyName.startsWith(provableKey))
            {
                itemKeys.add(provableKey);
                nextProvableItemKey++;
                provableKey = key + "." + nextProvableItemKey;
            }
        }
        final List<?> items = (List<?>) this.getType().cast(
                new ArrayList<>());
        this.setValue(items, settingsRoot);
        for (int index = 0; index < itemKeys.size(); index++)
        {
            IContainerItemAccessor<Integer> accessor;
            accessor = (IContainerItemAccessor<Integer>) this
                    .getItemAccessorTemplate().clone();
            accessor.setItemKey(index);
            accessor.readFromBackingStore(backingStore, settingsRoot);
        }
        // in case the user or the program created gaps in the
        // order of indices, we will close these now
        if (items.size() > itemKeys.size())
        {
            Iterator iterator = items.iterator();
            while (iterator.hasNext())
            {
                if (null == iterator.next())
                {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void writeToBackingStore(final IBackingStore backingStore,
                                    final Object settingsRoot) throws
            BackingStoreException
    {
        final List<Object> items = (List<Object>) this.getValue(settingsRoot);
        for (int index = 0; index < items.size(); index++)
        {
            IContainerItemAccessor<Integer> accessor;
            accessor = (IContainerItemAccessor<Integer>) this
                    .getItemAccessorTemplate().clone();
            accessor.setItemKey(index);
            accessor.writeToBackingStore(backingStore, settingsRoot);
        }
    }
}
