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

package eu.coldrye.settings.accessors;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.BackingStoreException;
import eu.coldrye.settings.util.AccessorUtils;
import eu.coldrye.settings.util.ReflectionUtils;
import eu.coldrye.settings.TypeMapperRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The class MapPropertyAccessor models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing all {@code Map}
 * type properties.
 *
 * @since 1.0.0
 */
public class MapPropertyAccessor extends AbstractContainerPropertyAccessor<String> {

  @Override
  @SuppressWarnings("unchecked")
  public void copyValue(Object source, Object target) {

    Map<String, Object> sourceMap = (Map<String, Object>) getValue(source);
    if (Objects.isNull(sourceMap)) {
      return;
    }
    Map<String, Object> targetMap = new HashMap<>();
    setValue(targetMap, target);
    for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
      ContainerItemAccessor<String> accessor = (ContainerItemAccessor<String>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!TypeMapperRegistry.INSTANCE.isAvailable(accessor.getType())) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        targetMap.put(entry.getKey(), o);
      }
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

    List<String> itemKeys = AccessorUtils.determineItemKeys(backingStore, getQualifiedKey());
    if (itemKeys.isEmpty()) {
      return;
    }

    Map<String, Object> items = (Map<String, Object>) getType().cast(new HashMap<String, Object>());
    setValue(items, settingsRoot);

    for (String itemKey : itemKeys) {
      ContainerItemAccessor<String> accessor = (ContainerItemAccessor<String>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!TypeMapperRegistry.INSTANCE.isAvailable(accessor.getType())) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        items.put(itemKey, o);
      }
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
