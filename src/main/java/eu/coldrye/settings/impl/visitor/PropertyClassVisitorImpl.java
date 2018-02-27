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
import eu.coldrye.settings.PropertyClass;
import eu.coldrye.settings.impl.accessor.Accessor;
import eu.coldrye.settings.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The class AbstractFieldVisitorImpl models a concrete implementation of
 * the {@link Visitor} interface that is responsible for visiting classes that
 * have been annotated with the {@link PropertyClass} annotation.
 *
 * @since 1.0.0
 */
public class PropertyClassVisitorImpl implements Visitor<Class<?>> {

  private List<Visitor<Field>> fieldVisitors;

  public PropertyClassVisitorImpl() {

    fieldVisitors = new ArrayList<>();
    fieldVisitors.add(new ArrayFieldVisitorImpl(this));
    fieldVisitors.add(new ListFieldVisitorImpl(this));
    fieldVisitors.add(new MapFieldVisitorImpl(this));
    fieldVisitors.add(new PropertyClassFieldVisitorImpl(this));
    fieldVisitors.addAll(Arrays.asList(SimpleTypeFieldVisitorImpl.getPreparedSimpleTypeFieldVisitors()));
    fieldVisitors.add(new FailFastVisitorImpl<>());
  }

  @Override
  public boolean canVisit(Class<?> visitee) {

    if (visitee.isAnnotationPresent(PropertyClass.class)) {
      List<Field> fields = ReflectionUtils.collectAnnotatedFields(visitee, Property.class);
      // We refrain from visiting a property class without any visitable fields
      if (fields.size() == 0) {
        return false;
      }
      int modifiers = visitee.getModifiers();
      if (!Modifier.isPublic(modifiers)) {
        throw new IllegalStateException("property class " + visitee + " is not public");
      }
      if (Modifier.isAbstract(modifiers)) {
        throw new IllegalStateException("property class " + visitee + " cannot be abstract");
      }
      return !Objects.isNull(ReflectionUtils.getPublicDefaultConstructorWithNoArguments(visitee));
    }
    return false;
  }

  /**
   * Introduced for testing purposes only. Sets the fieldVisitors.
   *
   * @param fieldVisitors
   */
  void setFieldVisitors(List<Visitor<Field>> fieldVisitors) {

    this.fieldVisitors = fieldVisitors;
  }

  @Override
  public void visit(Class<?> visitee, Accessor parentAccessor) {

    List<Field> fields = ReflectionUtils.collectAnnotatedFields(visitee, Property.class);
    for (Field field : fields) {
      for (Visitor<Field> visitor : fieldVisitors) {
        if (visitor.canVisit(field)) {
          visitor.visit(field, parentAccessor);
          break;
        }
      }
    }
  }
}
