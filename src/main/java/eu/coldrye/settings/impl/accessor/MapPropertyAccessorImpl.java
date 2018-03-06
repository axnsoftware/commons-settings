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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 * The class MapPropertyAccessorImpl models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing all {@code Map}
 * type properties.
 *
 * @since 1.0.0
 */
public class MapPropertyAccessorImpl extends AbstractContainerPropertyAccessorImpl<String> {

  @Override
  @SuppressWarnings("unchecked")
  public void copyValue(Object source, Object target) {

    Map<String, Object> sourceMap = (Map<String, Object>) getValue(source);
    Map<String, Object> targetMap = new HashMap<>();
    setValue(targetMap, target);
    for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
      ContainerItemAccessor<String> accessor = (ContainerItemAccessor<String>) getItemAccessorTemplate().clone();
      accessor.setItemKey(entry.getKey());
      accessor.copyValue(source, target);
    }
  }

  @Override
  public Object getValue(Object settingsRoot) {

    Object result = ReflectionUtils.getValue(this, settingsRoot);
    if (null == result) {
      result = new HashMap<String, Object>();
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
    String currentKey = null;
    for (String propertyName : sortedPropertyNames) {
      // TODO skip duplicate keys early? can there ever be duplicate keys in the backing store?
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
    setValue(getType().cast(new HashMap<String, Object>()), settingsRoot);
    for (String itemKey : itemKeys) {
      ContainerItemAccessor<String> accessor = (ContainerItemAccessor<String>) getItemAccessorTemplate().clone();
      accessor.setItemKey(itemKey);
      accessor.readFromBackingStore(backingStore, settingsRoot);
    }
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    ReflectionUtils.setValue(this, value, settingsRoot);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Map<String, Object> items = (Map<String, Object>) getValue(settingsRoot);
    for (String itemKey : items.keySet()) {
      ContainerItemAccessor<String> accessor = (ContainerItemAccessor<String>) getItemAccessorTemplate().clone();
      accessor.setItemKey(itemKey);
      accessor.writeToBackingStore(backingStore, settingsRoot);
    }
  }
}
