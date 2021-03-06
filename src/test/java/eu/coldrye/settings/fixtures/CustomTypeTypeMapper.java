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

package eu.coldrye.settings.fixtures;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.BackingStoreException;
import eu.coldrye.settings.TypeMapper;

/**
 *
 *
 */
public class CustomTypeTypeMapper implements TypeMapper {

  @Override
  public Object copyOf(Object value) {

    CustomType original = (CustomType) value;
    return new CustomType(original.getIntValue(), original.getFloatValue());
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type) throws
    BackingStoreException {

    return new CustomType(backingStore.getInteger(key + ".int"), backingStore.getFloat(key + ".float"));
  }

  @Override
  public Object valueOf(String value, Class<?> type) {

    String[] components = value.split("|");
    return new CustomType(Integer.valueOf(components[0]), Float.valueOf(components[1]));
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    CustomType original = (CustomType) value;
    backingStore.setFloat(key + ".float", original.getFloatValue());
    backingStore.setInteger(key + ".int", original.getIntValue());
  }
}
