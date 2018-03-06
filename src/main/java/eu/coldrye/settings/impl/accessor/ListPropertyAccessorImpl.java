/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.impl.accessor;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * The class ListPropertyAccessorImpl models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing all {@code List}
 * type properties.
 *
 * @since 1.0.0
 */
public class ListPropertyAccessorImpl extends AbstractContainerPropertyAccessorImpl<Integer> {

  @Override
  @SuppressWarnings("unchecked")
  public void copyValue(Object source, Object target) {

    List<Object> sourceList = (List<Object>) getValue(source);
    List<Object> targetList = new ArrayList<>();
    setValue(targetList, target);
    for (int index = 0; index < sourceList.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      accessor.setItemKey(index);
      accessor.copyValue(source, target);
    }
  }

  @Override
  public Object getValue(Object settingsRoot) {

    Object result = ReflectionUtils.getValue(this, settingsRoot);
    if (null == result) {
      result = new ArrayList<>();
      setValue(result, settingsRoot);
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    String key = getQualifiedKey();
    List<String> itemKeys = new ArrayList<>();
    List<String> sortedPropertyNames = new ArrayList<>();
    sortedPropertyNames.addAll(backingStore.keySet());
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
    List<?> items = (List<?>) getType().cast(new ArrayList<>());
    setValue(items, settingsRoot);
    for (int index = 0; index < itemKeys.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      accessor.setItemKey(index);
      accessor.readFromBackingStore(backingStore, settingsRoot);
    }
    // in case the user or the program created gaps in the
    // order of indices, we will close these now
    if (items.size() > itemKeys.size()) {
      Iterator iterator = items.iterator();
      while (iterator.hasNext()) {
        if (null == iterator.next()) {
          iterator.remove();
        }
      }
    }
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    ReflectionUtils.setValue(this, value, settingsRoot);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    List<Object> items = (List<Object>) getValue(settingsRoot);
    for (int index = 0; index < items.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      accessor.setItemKey(index);
      accessor.writeToBackingStore(backingStore, settingsRoot);
    }
  }
}
