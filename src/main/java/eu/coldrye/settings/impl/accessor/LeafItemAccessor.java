/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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
import eu.coldrye.settings.TypeMapper;

import java.util.Map;
import java.util.prefs.BackingStoreException;

public interface LeafItemAccessor extends PropertyAccessor {

  @Override
  default void copyValue(Object source, Object target) {

    Map<Class<?>, TypeMapper> typeMappings = getTypeMappings();
    Object copy = typeMappings.get(getType()).copyOf(getValue(source));
    setValue(copy, target);
  }

  @Override
  default void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Class<?> type = getType();
    Object value = getTypeMappings().get(type).readFromBackingStore(backingStore, getQualifiedKey(), type);
    setValue(value, settingsRoot);
  }

  @Override
  default void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    getTypeMappings().get(getType()).writeToBackingStore(backingStore, getQualifiedKey(), getValue(settingsRoot));
  }
}
