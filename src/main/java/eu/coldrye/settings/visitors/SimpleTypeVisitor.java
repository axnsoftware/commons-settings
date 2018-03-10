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

package eu.coldrye.settings.visitors;

import eu.coldrye.settings.Property;
import eu.coldrye.settings.TypeMapper;
import eu.coldrye.settings.accessors.Accessor;
import eu.coldrye.settings.TypeMapperRegistry;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * The class AbstractFieldVisitor models a concrete implementation of the {@code Visitor} interface that is
 * responsible for visiting classes that have been annotated with the {@code PropertyClass} annotation.
 *
 * @since 1.0.0
 */
public class SimpleTypeVisitor implements Visitor<Class<?>> {

  private static Visitor<Class<?>>[] preparedSimpleTypeVisitors;

  private Class<?> valueType;

  public SimpleTypeVisitor(Class<?> valueType) {

    this.valueType = valueType;
  }

  @Override
  public boolean canVisit(Class<?> visitee) {

    Boolean result = Boolean.FALSE;
    if (this.valueType.isAssignableFrom(visitee)) {
      if (TypeMapperRegistry.INSTANCE.isAvailable(visitee)) {
        result = Boolean.TRUE;
      } else if (visitee.isAnnotationPresent(Property.class)) {
        Property annotation = visitee.getAnnotation(Property.class);
        Class<? extends TypeMapper> typeMapperClass = annotation.typeMapper();
        if (!TypeMapper.class.equals(typeMapperClass)) {
          result = Boolean.TRUE;
          TypeMapperRegistry.INSTANCE.registerTypeMapper(visitee, typeMapperClass);
        }
      }
    }
    return result;
  }

  @Override
  public void visit(Class<?> visitee, Accessor parentAccessor) {

    // TypeMapper, if any, is already registered in #canVisit()
  }

  @SuppressWarnings("unchecked")
  public static Visitor<Class<?>>[] getPreparedSimpleTypeVisitors() {

    if (null == preparedSimpleTypeVisitors) {
      preparedSimpleTypeVisitors = new Visitor[]{
        new SimpleTypeVisitor(BigDecimal.class),
        new SimpleTypeVisitor(BigInteger.class),
        new SimpleTypeVisitor(Boolean.class),
        new SimpleTypeVisitor(Byte.class),
        new SimpleTypeVisitor(Double.class),
        new SimpleTypeVisitor(Enum.class),
        new SimpleTypeVisitor(Float.class),
        new SimpleTypeVisitor(Integer.class),
        new SimpleTypeVisitor(Long.class),
        new SimpleTypeVisitor(Short.class),
        new SimpleTypeVisitor(String.class),
        new SimpleTypeVisitor(UUID.class),
        /*
         * Visitor for custom types with an appropriate type mapper
         */
        new SimpleTypeVisitor(Object.class)
      };
    }
    return preparedSimpleTypeVisitors.clone();
  }
}
