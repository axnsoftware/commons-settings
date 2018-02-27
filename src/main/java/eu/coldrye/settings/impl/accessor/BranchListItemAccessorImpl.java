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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.prefs.BackingStoreException;

/**
 * The class BranchListItemAccessorImpl models a concrete implementation
 * of the {@code Accessor} interface, responsible for accessing items in
 * {@code List}S that are instances of {@code PropertyClass} annotated classes.
 *
 * @since 1.0.0
 */
public class BranchListItemAccessorImpl extends AbstractListItemAccessorImpl {

  @Override
  public void copyValue(Object source, Object target) {

    for (Accessor childAccessor : getChildAccessors()) {
      childAccessor.copyValue(source, target);
    }
  }

  @Override
  public Object getValue(Object settingsRoot) {

    Object result = super.getValue(settingsRoot);
    if (null == result) {
      try {
        Constructor constructor = getType().getConstructor();
        result = constructor.newInstance();
        setValue(result, settingsRoot);
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return result;
  }

  @Override
  public void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    for (Accessor childAccessor : getChildAccessors()) {
      childAccessor.readFromBackingStore(backingStore, settingsRoot);
    }
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    for (Accessor childAccessor : getChildAccessors()) {
      childAccessor.writeToBackingStore(backingStore, settingsRoot);
    }
  }
}
