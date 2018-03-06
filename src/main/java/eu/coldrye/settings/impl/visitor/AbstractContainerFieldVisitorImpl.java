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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 */
public abstract class AbstractContainerFieldVisitorImpl extends AbstractFieldVisitorImpl {

  private List<Visitor<Class<?>>> visitors;

  private Class<?> itemType;

  private Visitor<Class<?>> itemVisitor;

  public AbstractContainerFieldVisitorImpl(Visitor<Class<?>> propertyClassVisitor) {

    this.visitors = new ArrayList<>();
    this.visitors.add(propertyClassVisitor);
    this.visitors.addAll(Arrays.asList(SimpleTypeVisitorImpl.getPreparedSimpleTypeVisitors()));
    this.visitors.add(new FailFastVisitorImpl<>());
  }

  @Override
  public boolean canVisit(Field visitee) {

    this.setItemType(null);
    return super.canVisit(visitee);
  }

  protected Boolean canVisitItemType() {

    Boolean result = Boolean.FALSE;

    if (this.itemVisitor != null && this.itemVisitor.canVisit(this.itemType)) {
      result = Boolean.TRUE;
    } else {
      this.itemVisitor = null;
      for (Visitor<Class<?>> visitor : this.visitors) {
        if (visitor.canVisit(this.itemType)) {
          this.itemVisitor = visitor;
          result = Boolean.TRUE;
          break;
        }
      }
    }

    return result;
  }

  public Class<?> getItemType() {

    return itemType;
  }

  public Visitor<Class<?>> getItemVisitor() {

    return itemVisitor;
  }

  public void setItemType(Class<?> itemType) {

    this.itemType = itemType;
  }

  public void setItemVisitor(Visitor<Class<?>> itemVisitor) {

    this.itemVisitor = itemVisitor;
  }
}
