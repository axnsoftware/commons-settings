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

package eu.coldrye.settings.util;

import eu.coldrye.settings.TypeMapper;
import eu.coldrye.settings.mappers.BigDecimalTypeMapperImpl;
import eu.coldrye.settings.mappers.BigIntegerTypeMapperImpl;
import eu.coldrye.settings.mappers.BooleanTypeMapperImpl;
import eu.coldrye.settings.mappers.ByteTypeMapperImpl;
import eu.coldrye.settings.mappers.CharacterTypeMapperImpl;
import eu.coldrye.settings.mappers.DoubleTypeMapperImpl;
import eu.coldrye.settings.mappers.EnumTypeMapperImpl;
import eu.coldrye.settings.mappers.FloatTypeMapperImpl;
import eu.coldrye.settings.mappers.IntegerTypeMapperImpl;
import eu.coldrye.settings.mappers.LongTypeMapperImpl;
import eu.coldrye.settings.mappers.ShortTypeMapperImpl;
import eu.coldrye.settings.mappers.StringTypeMapperImpl;
import eu.coldrye.settings.mappers.UuidTypeMapperImpl;

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
    defaultMappers.put(BigDecimal.class, new BigDecimalTypeMapperImpl());
    defaultMappers.put(BigInteger.class, new BigIntegerTypeMapperImpl());
    defaultMappers.put(Boolean.class, new BooleanTypeMapperImpl());
    defaultMappers.put(Byte.class, new ByteTypeMapperImpl());
    defaultMappers.put(Character.class, new CharacterTypeMapperImpl());
    defaultMappers.put(Double.class, new DoubleTypeMapperImpl());
    defaultMappers.put(Enum.class, new EnumTypeMapperImpl());
    defaultMappers.put(Float.class, new FloatTypeMapperImpl());
    defaultMappers.put(Integer.class, new IntegerTypeMapperImpl());
    defaultMappers.put(Long.class, new LongTypeMapperImpl());
    defaultMappers.put(Short.class, new ShortTypeMapperImpl());
    defaultMappers.put(UUID.class, new UuidTypeMapperImpl());
    defaultMappers.put(String.class, new StringTypeMapperImpl());
  }
}
