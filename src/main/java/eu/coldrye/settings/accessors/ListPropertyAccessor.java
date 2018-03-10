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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The class ListPropertyAccessor models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing all {@code List}
 * type properties.
 *
 * @since 1.0.0
 */
public class ListPropertyAccessor extends AbstractContainerPropertyAccessor<Integer> {

  @Override
  @SuppressWarnings("unchecked")
  public void copyValue(Object source, Object target) {

    List<Object> sourceList = (List<Object>) getValue(source);
    if (Objects.isNull(sourceList)) {
      return;
    }
    List<Object> targetList = new ArrayList<>(sourceList.size());

    setValue(targetList, target);
    for (int index = 0; index < sourceList.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!TypeMapperRegistry.INSTANCE.isAvailable(accessor.getType())) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        targetList.add(o);
      } else {
        targetList.add(null);
      }
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

    AtomicInteger maxIndex = new AtomicInteger();
    List<Integer> itemKeys = AccessorUtils.determineItemKeys(backingStore, getQualifiedKey(), maxIndex);
    if (maxIndex.get() == 0) {
      return;
    }

    List items = (List) getType().cast(new ArrayList<>());
    setValue(items, settingsRoot);

    // curry items, we will later remove existing gaps
    for (int index = 0; index < maxIndex.get(); index++) {
      items.add(null);
    }

    for (int index = 0; index < itemKeys.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!TypeMapperRegistry.INSTANCE.isAvailable(accessor.getType())) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        items.set(index, o);
      }
      accessor.setItemKey(itemKeys.get(index));
      accessor.readFromBackingStore(backingStore, settingsRoot);
    }

    // remove existing gaps
    items.removeIf(Objects::isNull);
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
