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
import eu.coldrye.settings.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * The abstract class AbstractFieldVisitorImpl models the root of a hierarchy of
 * derived implementation classes and it provides the default behaviour for all
 * implementations of the {@code Visitor} interface. It also encapsulated
 * commonly used code to be reused by the concrete implementations.
 *
 * @since 1.0.0
 */
public abstract class AbstractFieldVisitorImpl implements Visitor<Field> {

  @Override
  public boolean canVisit(Field visitee) {

    Boolean result = Boolean.FALSE;
    if (visitee.isAnnotationPresent(Property.class) && ReflectionUtils.isMutableField(visitee)) {
      result = canVisitImpl(visitee);
    }
    return result;
  }

  /**
   * Delegated from by {@link #canVisit(java.lang.reflect.Field)}. Derived
   * classes must implement this.
   *
   * @param visitee
   * @return true whether the visitee can be visited, false otherwise
   */
  protected abstract Boolean canVisitImpl(Field visitee);
}
