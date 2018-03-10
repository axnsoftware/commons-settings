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

package eu.coldrye.settings;

import eu.coldrye.settings.mappers.BigDecimalTypeMapper;
import eu.coldrye.settings.mappers.BigIntegerTypeMapper;
import eu.coldrye.settings.mappers.BooleanTypeMapper;
import eu.coldrye.settings.mappers.ByteTypeMapper;
import eu.coldrye.settings.mappers.CharacterTypeMapper;
import eu.coldrye.settings.mappers.DoubleTypeMapper;
import eu.coldrye.settings.mappers.EnumTypeMapper;
import eu.coldrye.settings.mappers.FloatTypeMapper;
import eu.coldrye.settings.mappers.IntegerTypeMapper;
import eu.coldrye.settings.mappers.LongTypeMapper;
import eu.coldrye.settings.mappers.ShortTypeMapper;
import eu.coldrye.settings.mappers.StringTypeMapper;
import eu.coldrye.settings.mappers.UuidTypeMapper;
import eu.coldrye.settings.util.ReflectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class TypeMapperRegistry {

  private Map<Class<?>, TypeMapper> registeredMappers = new HashMap<>();

  private Map<Class<?>, TypeMapper> defaultMappers;

  public static final TypeMapperRegistry INSTANCE = new TypeMapperRegistry();

  TypeMapperRegistry() {

    prepareDefaultTypeMappings();
  }

  public boolean isAvailable(Class<?> type) {

    return Objects.nonNull(registeredMappers.getOrDefault(type, defaultMappers.getOrDefault(type, null)));
  }

  public TypeMapper getTypeMapper(Class<?> type) {

    TypeMapper result = registeredMappers.getOrDefault(type, defaultMappers.getOrDefault(type, null));
    if (Objects.isNull(result)) {
      throw new IllegalStateException("no type mapper available for " + type.getName());
    }
    return result;
  }

  public void registerTypeMapper(Class<?> type, Class<? extends TypeMapper> typeMapperClass) {

    registeredMappers.put(type, ReflectionUtils.newInstance(typeMapperClass));
  }

  /**
   * Prepares the default type mappings to be used when building the root
   * accessor.
   *
   * @return the prepared default type mappings
   */
  private void prepareDefaultTypeMappings() {

    defaultMappers = new HashMap<>();
    defaultMappers.put(BigDecimal.class, new BigDecimalTypeMapper());
    defaultMappers.put(BigInteger.class, new BigIntegerTypeMapper());
    defaultMappers.put(Boolean.class, new BooleanTypeMapper());
    defaultMappers.put(Byte.class, new ByteTypeMapper());
    defaultMappers.put(Character.class, new CharacterTypeMapper());
    defaultMappers.put(Double.class, new DoubleTypeMapper());
    defaultMappers.put(Enum.class, new EnumTypeMapper());
    defaultMappers.put(Float.class, new FloatTypeMapper());
    defaultMappers.put(Integer.class, new IntegerTypeMapper());
    defaultMappers.put(Long.class, new LongTypeMapper());
    defaultMappers.put(Short.class, new ShortTypeMapper());
    defaultMappers.put(UUID.class, new UuidTypeMapper());
    defaultMappers.put(String.class, new StringTypeMapper());
  }
}
