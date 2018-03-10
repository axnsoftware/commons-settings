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
import eu.coldrye.settings.accessors.LeafPropertyAccessor;
import eu.coldrye.settings.accessors.PropertyAccessor;
import eu.coldrye.settings.util.DefaultValueHolder;
import eu.coldrye.settings.TypeMapperRegistry;
import eu.coldrye.settings.util.VisitorUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * The class SimpleTypeFieldVisitor models a concrete implementation
 * of the {@code Visitor} interface that is responsible for visiting all simple
 * typed fields, such as {@code Integer} or {@code Enum} typed fields.
 *
 * @since 1.0.0
 */
public class SimpleTypeFieldVisitor extends AbstractFieldVisitor {

  private static Visitor<Field>[] preparedSimpleTypeFieldVisitors;

  private Class<?> valueType;

  public SimpleTypeFieldVisitor(Class<?> valueType) {

    this.valueType = valueType;
  }

  @Override
  protected Boolean canVisitImpl(Field visitee) {

    Boolean result = Boolean.FALSE;
    Class<?> type = visitee.getType();
    if (visitee.isAnnotationPresent(Property.class) && valueType.isAssignableFrom(type)) {
      Property propertyAnnotation = visitee.getAnnotation(Property.class);
      /*
       * must set propertyAnnotation now or otherwise this will fail on
       * visiting all default supported type typed fields.
       */
      if (TypeMapperRegistry.INSTANCE.isAvailable(type)) {
        result = Boolean.TRUE;
      } else {
        Class<? extends TypeMapper> typeMapperClass;
        if (type.isAnnotationPresent(Property.class)) {
          typeMapperClass = type.getAnnotation(Property.class).typeMapper();
        } else {
          typeMapperClass = propertyAnnotation.typeMapper();
        }
        if (!TypeMapper.class.equals(typeMapperClass)) {
          TypeMapperRegistry.INSTANCE.registerTypeMapper(type, typeMapperClass);
          result = Boolean.TRUE;
        }
      }
    }
    return result;
  }

  @Override
  public void visit(Field visitee, Accessor parentAccessor) {

    Class<?> type = visitee.getType();
    PropertyAccessor accessor = new LeafPropertyAccessor();
    Property propertyAnnotation = visitee.getAnnotation(Property.class);
    DefaultValueHolder defaultValueHolder = new DefaultValueHolder(propertyAnnotation.defaultValue(), type);
    accessor.setDefaultValueHolder(defaultValueHolder);
    VisitorUtils.configureAccessor(accessor, parentAccessor, visitee);
  }

  @SuppressWarnings("unchecked")
  public static Visitor<Field>[] getPreparedSimpleTypeFieldVisitors() {

    if (null == preparedSimpleTypeFieldVisitors) {
      preparedSimpleTypeFieldVisitors = new Visitor[]{
        new SimpleTypeFieldVisitor(BigDecimal.class),
        new SimpleTypeFieldVisitor(BigInteger.class),
        new SimpleTypeFieldVisitor(Boolean.class),
        new SimpleTypeFieldVisitor(Byte.class),
        new SimpleTypeFieldVisitor(Double.class),
        new SimpleTypeFieldVisitor(Enum.class),
        new SimpleTypeFieldVisitor(Float.class),
        new SimpleTypeFieldVisitor(Integer.class),
        new SimpleTypeFieldVisitor(Long.class),
        new SimpleTypeFieldVisitor(Short.class),
        new SimpleTypeFieldVisitor(String.class),
        new SimpleTypeFieldVisitor(UUID.class),
        /*
         * Visitor for custom types with an appropriate type mapper
         */
        new SimpleTypeFieldVisitor(Object.class)
      };
    }
    return preparedSimpleTypeFieldVisitors.clone();
  }
}
