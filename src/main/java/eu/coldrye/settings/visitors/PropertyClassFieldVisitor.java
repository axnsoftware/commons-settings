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

import eu.coldrye.settings.accessors.Accessor;
import eu.coldrye.settings.accessors.PropertyAccessor;
import eu.coldrye.settings.accessors.BranchPropertyAccessor;
import eu.coldrye.settings.util.VisitorUtils;

import java.lang.reflect.Field;

/**
 * The class PropertyClassFieldVisitor models a concrete implementation of the {@code Visitor} interface that is
 * responsible for visiting fields of custom type where the classes have been annotated using the {@code PropertyClass}
 * annotation.
 *
 * @since 1.0.0
 */
public class PropertyClassFieldVisitor extends AbstractFieldVisitor {

  private Visitor<Class<?>> propertyClassVisitor;

  public PropertyClassFieldVisitor(Visitor<Class<?>> propertyClassVisitor) {

    this.propertyClassVisitor = propertyClassVisitor;
  }

  @Override
  protected Boolean canVisitImpl(Field visitee) {

    return propertyClassVisitor.canVisit(visitee.getType());
  }

  @Override
  public void visit(Field visitee, Accessor parentAccessor) {

    PropertyAccessor accessor = new BranchPropertyAccessor();
    VisitorUtils.configureAccessor(accessor, parentAccessor, visitee);
    propertyClassVisitor.visit(visitee.getType(), accessor);
  }
}
