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
import eu.coldrye.settings.accessors.BranchMapItemAccessor;
import eu.coldrye.settings.accessors.ContainerItemAccessor;
import eu.coldrye.settings.accessors.ContainerPropertyAccessor;
import eu.coldrye.settings.accessors.LeafMapItemAccessor;
import eu.coldrye.settings.accessors.MapPropertyAccessor;
import eu.coldrye.settings.util.VisitorUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * The class MapFieldVisitor models a concrete implementation of the
 * {@code Visitor} interface that is responsible for visiting fields of type
 * {@code Map}.
 *
 * @since 1.0.0
 */
public class MapFieldVisitor extends AbstractContainerFieldVisitor {

  public MapFieldVisitor(Visitor<Class<?>> propertyClassVisitor) {

    super(propertyClassVisitor);
  }

  @Override
  protected Boolean canVisitImpl(Field visitee) {

    Boolean result = Boolean.FALSE;
    Class<?> type = visitee.getType();
    if (Map.class.isAssignableFrom(type)) {
      ParameterizedType parameterizedType = (ParameterizedType) visitee.getGenericType();
      // TODO:log error that keys have to be of type String
      if (String.class.equals(parameterizedType.getActualTypeArguments()[0])) {
        setItemType((Class<?>) parameterizedType.getActualTypeArguments()[1]);
        result = super.canVisitItemType();
      }
    }
    return result;
  }

  @Override
  public void visit(Field visitee, Accessor parentAccessor) {

    Visitor<Class<?>> itemVisitor = getItemVisitor();
    Class<?> itemType = getItemType();
    ContainerPropertyAccessor accessor = new MapPropertyAccessor();
    VisitorUtils.configureAccessor(accessor, parentAccessor, visitee);
    ContainerItemAccessor itemAccessorTemplate;
    if (itemVisitor instanceof SimpleTypeVisitor) {
      itemAccessorTemplate = new LeafMapItemAccessor();
    } else {
      itemAccessorTemplate = new BranchMapItemAccessor();
    }
    itemAccessorTemplate.setParentAccessor(accessor);
    itemAccessorTemplate.setType(itemType);
    accessor.setItemAccessorTemplate(itemAccessorTemplate);
    itemVisitor.visit(itemType, itemAccessorTemplate);
  }
}
