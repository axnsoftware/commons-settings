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
import eu.coldrye.settings.TypeMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.prefs.BackingStoreException;

/**
 * The class DefaultTypeMapperImpl models a concrete implementation of the
 * {@code TypeMapper} interface, with support for only the standard Java types
 * such as {@code java.lang.Boolean} and {@code java.math.BigDecimal} including
 * generic {@code Enum} support. In addition, it provides support for
 * {@code java.util.UUID}.
 *
 * @since 1.0.0
 */
public class DefaultTypeMapperImpl implements TypeMapper {

  /**
   * Default type mappings to be used when building the root accessor.
   */
  private static Map<Class<?>, TypeMapper> preparedDefaultTypeMappings;

  @Override
  public Object copyOf(Object value) {

    return value;
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type)
    throws BackingStoreException {

    if (null == backingStore) {
      throw new IllegalArgumentException("backingStore must not be null.");
    }
    if (null == key) {
      throw new IllegalArgumentException("key must not be null.");
    }
    if (null == type) {
      throw new IllegalArgumentException("type must not be null.");
    }

    Object result;
    if (BigDecimal.class.equals(type) || BigInteger.class.equals(type) || type.isEnum() || String.class.equals(type)
      || UUID.class.equals(type)) {
      return valueOf(backingStore.getString(key), type);
    } else if (Boolean.class.equals(type)) {
      result = backingStore.getBoolean(key);
    } else if (Byte.class.equals(type)) {
      result = backingStore.getByte(key);
    } else if (Character.class.equals(type)) {
      result = backingStore.getCharacter(key);
    } else if (Double.class.equals(type)) {
      result = backingStore.getDouble(key);
    } else if (Float.class.equals(type)) {
      result = backingStore.getFloat(key);
    } else if (Integer.class.equals(type)) {
      result = backingStore.getInteger(key);
    } else if (Long.class.equals(type)) {
      result = backingStore.getLong(key);
    } else if (Short.class.equals(type)) {
      result = backingStore.getShort(key);
    } else {
      throw new IllegalArgumentException("unsupported type " + type.getName());
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object valueOf(String value, Class<?> type) {

    Object result = value;
    if (null == type) {
      throw new IllegalArgumentException("type must not be null.");
    }
    if (null != value) {
      if (BigDecimal.class.equals(type)) {
        result = new BigDecimal(value);
      } else if (BigInteger.class.equals(type)) {
        result = new BigInteger(value);
      } else if (Boolean.class.equals(type)) {
        result = Boolean.valueOf(value);
      } else if (Byte.class.equals(type)) {
        result = Byte.valueOf(value);
      } else if (Character.class.equals(type)) {
        result = Character.valueOf(value.charAt(0));
      } else if (Double.class.equals(type)) {
        result = Double.valueOf(value);
      } else if (type.isEnum()) {
        result = Enum.valueOf((Class<? extends Enum>) type, value);
      } else if (Float.class.equals(type)) {
        result = Float.valueOf(value);
      } else if (Integer.class.equals(type)) {
        result = Integer.valueOf(value);
      } else if (Long.class.equals(type)) {
        result = Long.valueOf(value);
      } else if (Short.class.equals(type)) {
        result = Short.valueOf(value);
      } else if (String.class.equals(type)) {
        result = value;
      } else if (UUID.class.equals(type)) {
        result = UUID.fromString(value);
      } else {
        throw new IllegalArgumentException("unsupported type " + type.getName());
      }
    }
    return result;
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    if (null == backingStore) {
      throw new IllegalArgumentException("backingStore must not be null.");
    }
    if (null == key) {
      throw new IllegalArgumentException("key must not be null.");
    }
    if (value != null) {
      Class<?> type = value.getClass();
      if (BigDecimal.class.equals(type) || BigInteger.class.equals(type) || type.isEnum() || String.class.equals(type)
        || UUID.class.equals(type)) {
        backingStore.setString(key, value.toString());
      } else if (Boolean.class.equals(type)) {
        backingStore.setBoolean(key, value);
      } else if (Byte.class.equals(type)) {
        backingStore.setByte(key, (Byte) value);
      } else if (Character.class.equals(type)) {
        backingStore.setCharacter(key, (Character) value);
      } else if (Double.class.equals(type)) {
        backingStore.setDouble(key, (Double) value);
      } else if (Float.class.equals(type)) {
        backingStore.setFloat(key, (Float) value);
      } else if (Integer.class.equals(type)) {
        backingStore.setInteger(key, (Integer) value);
      } else if (Long.class.equals(type)) {
        backingStore.setLong(key, (Long) value);
      } else if (Short.class.equals(type)) {
        backingStore.setShort(key, (Short) value);
      } else {
        throw new IllegalArgumentException("unsupported type " + type.getName());
      }
    }
  }

  /**
   * Prepares the default type mappings to be used when building the root
   * accessor.
   *
   * @return the prepared default type mappings
   */
  public static Map<Class<?>, TypeMapper> getPreparedDefaultTypeMappings() {

    if (null == preparedDefaultTypeMappings) {
      TypeMapper mapper = new DefaultTypeMapperImpl();
      Map<Class<?>, TypeMapper> defaultTypeMappings = new HashMap<>();
      defaultTypeMappings.put(BigDecimal.class, mapper);
      defaultTypeMappings.put(BigInteger.class, mapper);
      defaultTypeMappings.put(Boolean.class, mapper);
      defaultTypeMappings.put(Byte.class, mapper);
      defaultTypeMappings.put(Character.class, mapper);
      defaultTypeMappings.put(Double.class, mapper);
      defaultTypeMappings.put(Enum.class, mapper);
      defaultTypeMappings.put(Float.class, mapper);
      defaultTypeMappings.put(Integer.class, mapper);
      defaultTypeMappings.put(Long.class, mapper);
      defaultTypeMappings.put(Short.class, mapper);
      defaultTypeMappings.put(UUID.class, mapper);
      defaultTypeMappings.put(String.class, mapper);
      preparedDefaultTypeMappings = Collections.unmodifiableMap(defaultTypeMappings);
    }
    return preparedDefaultTypeMappings;
  }
}
