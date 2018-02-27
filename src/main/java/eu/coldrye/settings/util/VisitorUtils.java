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

import eu.coldrye.settings.Property;
import eu.coldrye.settings.TypeMapper;
import eu.coldrye.settings.impl.accessor.Accessor;
import eu.coldrye.settings.impl.accessor.PropertyAccessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public final class VisitorUtils {

  /**
   * Must not be instantiated.
   */
  private VisitorUtils() {

  }

  /**
   * Configures the specified {@code accessor} and adds it as a child accessor
   * to the specified {@code parentAccessor}.
   *
   * @param accessor
   * @param parentAccessor
   * @param visitee
   */
  public static void configureAccessor(PropertyAccessor accessor, Accessor parentAccessor, Field visitee) {

    Class<?> type = visitee.getType();
    Property annotation = visitee.getAnnotation(Property.class);
    String key = visitee.getName();
    if (!"".equals(annotation.key())) {
      key = annotation.key();
    }
    accessor.setKey(key);
    accessor.setParentAccessor(parentAccessor);
    accessor.setGetter(ReflectionUtils.getMethod(visitee, ReflectionUtils.getGetterName(visitee)));
    accessor.setSetter(ReflectionUtils.getMethod(visitee, ReflectionUtils.getSetterName(visitee), type));
    accessor.setType(type);
    accessor.setMandatory(annotation.mandatory());
    parentAccessor.getChildAccessors().add(accessor);
  }

  /**
   * Gets an instance of the {@code TypeMapper} interface for the specified
   * {@code typeMapperTypename}. Also registers the instance with the
   * specified {@code typeMappings} for the specified {@code type}.
   *
   * @param type
   * @param typeMapperClass
   * @param typeMappings
   * @return instance of the type mapper
   */
  @SuppressWarnings("unchecked")
  public static TypeMapper registerOrGetExistingTypeMapper(Class<?> type, Class<? extends TypeMapper> typeMapperClass,
                                                           Map<Class<?>, TypeMapper> typeMappings) {

    TypeMapper result = typeMappings.get(type);
    if (null == result) {
      /*
       * Skip the default typeMapper, which is TypeMapper.class.
       */
      if (!TypeMapper.class.equals(typeMapperClass)) {
        result = ReflectionUtils.newInstance(typeMapperClass);
        typeMappings.put(type, result);
      } else if (type.isEnum()) {
        result = typeMappings.get(Enum.class);
      } else {
        throw new IllegalStateException("no type mapper available for " + type);
      }
    }
    return result;
  }
}
