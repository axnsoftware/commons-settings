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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The class ArrayPropertyAccessor models a concrete implementation of the {@code Accessor} interface,
 * representing the root of a hierarchy of accessors.
 *
 * @since 1.0.0
 */
public class ArrayPropertyAccessor extends AbstractContainerPropertyAccessor {

  @Override
  @SuppressWarnings("unchecked")
  public void copyValue(Object source, Object target) {

    Object[] sourceArray = (Object[]) getValue(source);
    if (Objects.isNull(sourceArray)) {
      return;
    }
    Object[] targetArray = (Object[]) getType().cast(Array.newInstance(getItemAccessorTemplate().getType(), sourceArray.length));
    setValue(targetArray, target);
    for (int index = 0; index < sourceArray.length; index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!TypeMapperRegistry.INSTANCE.isAvailable(accessor.getType())) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        targetArray[index] = o;
      }
      accessor.setItemKey(index);
      accessor.copyValue(source, target);
    }
  }

  @Override
  public Object getValue(Object settingsRoot) {

    return ReflectionUtils.getValue(this, settingsRoot);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    AtomicInteger maxIndex = new AtomicInteger();
    List<Integer> itemKeys = AccessorUtils.determineItemKeys(backingStore, getQualifiedKey(), maxIndex);
    if (maxIndex.get() == 0) {
      return;
    }

    Class componentType = getItemAccessorTemplate().getType();
    Object[] items = (Object[]) Array.newInstance(componentType, maxIndex.get());
    setValue(items, settingsRoot);

    boolean isTypeMapperAvailable = TypeMapperRegistry.INSTANCE.isAvailable(componentType);
    for (int index = 0; index < itemKeys.size(); index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      // if there is a mapper, then leave it to the child accessor, else it must be a property class without any
      if (!isTypeMapperAvailable) {
        Object o = ReflectionUtils.newInstance(accessor.getType());
        items[itemKeys.get(index)] = o;
      }
      accessor.setItemKey(itemKeys.get(index));
      accessor.readFromBackingStore(backingStore, settingsRoot);
    }

    // remove existing gaps
    List tmp = new ArrayList();
    tmp.addAll(Arrays.asList(items));
    tmp.removeIf(Objects::isNull);
    Object[] t = (Object[]) Array.newInstance(componentType, tmp.size());
    setValue(tmp.toArray(t), settingsRoot);
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    ReflectionUtils.setValue(this, value, settingsRoot);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void writeToBackingStore(BackingStore properties, Object settingsRoot) throws BackingStoreException {

    Object[] items = (Object[]) getValue(settingsRoot);
    for (int index = 0; index < items.length; index++) {
      ContainerItemAccessor<Integer> accessor = (ContainerItemAccessor<Integer>) getItemAccessorTemplate().clone();
      accessor.setItemKey(index);
      accessor.writeToBackingStore(properties, settingsRoot);
    }
  }
}
