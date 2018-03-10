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
import eu.coldrye.settings.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * The class BranchPropertyAccessor models a concrete implementation
 * of the {@code Accessor} interface, responsible for accessing properties that
 * are instances of {@code PropertyClass} annotated classes.
 *
 * @since 1.0.0
 */
public class BranchPropertyAccessor extends AbstractPropertyAccessor {

  @Override
  public void copyValue(Object source, Object target) {

    throw new RuntimeException("not implemented yet");
  }

  @Override
  public Object getValue(Object settingsRoot) {

    Accessor parentAccessor = getParentAccessor();
    Object valueHolder = parentAccessor.getValue(settingsRoot);
    Method getter = getGetter();
    return ReflectionUtils.invokeGetter(getter, valueHolder, getDefaultValueHolder());
  }

  @Override
  public void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Object value = getValue(settingsRoot);
    if (Objects.nonNull(value)) {
      for(Accessor accessor : getChildAccessors()) {
        accessor.writeToBackingStore(backingStore, settingsRoot);
      }
    }
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    Accessor parentAccessor = getParentAccessor();
    Object valueHolder = parentAccessor.getValue(settingsRoot);
    Method setter = getSetter();
    ReflectionUtils.invokeSetter(setter, valueHolder, value);
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Object value = getValue(settingsRoot);
    if (Objects.nonNull(value)) {
      for(Accessor accessor : getChildAccessors()) {
        accessor.writeToBackingStore(backingStore, settingsRoot);
      }
    }
  }
}
