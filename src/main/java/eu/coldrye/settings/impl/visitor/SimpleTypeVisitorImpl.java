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

package eu.coldrye.settings.impl.visitor;

import eu.coldrye.settings.Property;
import eu.coldrye.settings.TypeMapper;
import eu.coldrye.settings.impl.accessor.DefaultTypeMapperImpl;
import eu.coldrye.settings.impl.accessor.Accessor;
import eu.coldrye.settings.util.VisitorUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * The class AbstractFieldVisitorImpl models a concrete implementation of the {@code Visitor} interface that is
 * responsible for visiting classes that have been annotated with the {@code PropertyClass} annotation.
 *
 * @since 1.0.0
 */
public class SimpleTypeVisitorImpl implements Visitor<Class<?>> {

  private static Visitor<Class<?>>[] preparedSimpleTypeVisitors;

  private Class<?> valueType;

  public SimpleTypeVisitorImpl(Class<?> valueType) {

    this.valueType = valueType;
  }

  @Override
  public boolean canVisit(Class<?> visitee) {

    Boolean result = Boolean.FALSE;
    if (this.valueType.isAssignableFrom(visitee)) {
      if (DefaultTypeMapperImpl.getPreparedDefaultTypeMappings().containsKey(visitee)) {
        result = Boolean.TRUE;
      } else if (visitee.isAnnotationPresent(Property.class)) {
        Property annotation = visitee.getAnnotation(Property.class);
        if (!TypeMapper.class.equals(annotation.typeMapper())) {
          result = Boolean.TRUE;
        }
      }
    }
    return result;
  }

  @Override
  public void visit(Class<?> visitee, Accessor parentAccessor) {

    Property annotation = visitee.getAnnotation(Property.class);
    if (annotation != null && !TypeMapper.class.equals(annotation.typeMapper())) {
      VisitorUtils.registerOrGetExistingTypeMapper(visitee, annotation.typeMapper(),
        parentAccessor.getTypeMappings());
    }
  }

  @SuppressWarnings("unchecked")
  public static Visitor<Class<?>>[] getPreparedSimpleTypeVisitors() {

    if (null == preparedSimpleTypeVisitors) {
      preparedSimpleTypeVisitors = new Visitor[]{
        new SimpleTypeVisitorImpl(BigDecimal.class),
        new SimpleTypeVisitorImpl(BigInteger.class),
        new SimpleTypeVisitorImpl(Boolean.class),
        new SimpleTypeVisitorImpl(Byte.class),
        new SimpleTypeVisitorImpl(Double.class),
        new SimpleTypeVisitorImpl(Enum.class),
        new SimpleTypeVisitorImpl(Float.class),
        new SimpleTypeVisitorImpl(Integer.class),
        new SimpleTypeVisitorImpl(Long.class),
        new SimpleTypeVisitorImpl(Short.class),
        new SimpleTypeVisitorImpl(String.class),
        new SimpleTypeVisitorImpl(UUID.class),
        /*
         * Visitor for custom types with an appropriate type mapper
         */
        new SimpleTypeVisitorImpl(Object.class)
      };
    }
    return preparedSimpleTypeVisitors.clone();
  }
}
