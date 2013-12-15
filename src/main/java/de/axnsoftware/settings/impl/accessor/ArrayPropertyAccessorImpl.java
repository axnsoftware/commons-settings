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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * The final class ArrayPropertyAccessorImpl models a concrete implementation of
 * the {@code IAccessor} interface, representing the root of a hierarchy of
 * accessors.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class ArrayPropertyAccessorImpl
        extends AbstractContainerPropertyAccessorImpl
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyValue(final Object source, final Object target)
    {
        final Object[] sourceArray = (Object[]) this.getValue(source);
        final Object targetArray = this.getType().cast(Array.newInstance(this
                .getItemAccessorTemplate().getType(), sourceArray.length));
        this.setValue(targetArray, target);
        for (int index = 0; index < sourceArray.length; index++)
        {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this
                    .getItemAccessorTemplate().clone();
            accessor.setItemKey(Integer.valueOf(index));
            accessor.copyValue(source, target);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readFromBackingStore(final IBackingStore properties,
                                     final Object settingsRoot) throws
            BackingStoreException
    {
        final String key = this.getQualifiedKey();
        final List<String> itemKeys = new ArrayList<>();
        final List<String> sortedPropertyNames = new ArrayList<>();
        try
        {
            sortedPropertyNames.addAll(properties.keySet());
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
            final Object items = this.getType().cast(Array.newInstance(this
                    .getItemAccessorTemplate().getType(), nextProvableItemKey));
            this.setValue(items, settingsRoot);
            for (int index = 0; index < itemKeys.size(); index++)
            {
                IContainerItemAccessor accessor = (IContainerItemAccessor) this
                        .getItemAccessorTemplate().clone();
                accessor.setItemKey(index);
                accessor.readFromBackingStore(properties, settingsRoot);
            }
        }
        catch (BackingStoreException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToBackingStore(final IBackingStore properties,
                                    final Object settingsRoot) throws
            BackingStoreException
    {
        final Object[] items = (Object[]) this.getValue(settingsRoot);
        for (int index = 0; index < items.length; index++)
        {
            IContainerItemAccessor accessor = (IContainerItemAccessor) this
                    .getItemAccessorTemplate().clone();
            accessor.setItemKey(index);
            accessor.writeToBackingStore(properties, settingsRoot);
        }
    }
}
