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

package eu.coldrye.settings.impl.accessor;

import eu.coldrye.settings.TypeMapper;
import eu.coldrye.settings.impl.visitor.Visitor;
import eu.coldrye.settings.impl.visitor.PropertyClassVisitorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class RootAccessorBuilder models a builder for instances of the
 * {@link Accessor} interface that represent the root of a hierarchy of
 * accessors for traversing and accessing the properties of instances of classes
 * that have been annotated with the {@code PropertyClass} annotation.
 *
 * @since 1.0.0
 */
public final class RootAccessorBuilder {

  /**
   * Builds and returns a new instance of the {@link Accessor} interface for
   * the specified {@code type}, which must have been annotated using the
   * {@code PropertyClass} annotation.
   *
   * @param type
   * @return the root accessor
   */
  public Accessor buildRootAccessor(Class<?> type) {

    Accessor result = new RootAccessorImpl();
    List<Accessor> childAccessors = new ArrayList<>();
    result.setChildAccessors(childAccessors);
    result.setKey("");
    result.setType(type);
    Map<Class<?>, TypeMapper> typeMappings;
    typeMappings = result.getTypeMappings();
    typeMappings.putAll(DefaultTypeMapperImpl.getPreparedDefaultTypeMappings());
    Visitor<Class<?>> visitor = new PropertyClassVisitorImpl();
    if (visitor.canVisit(type)) {
      visitor.visit(type, result);
    } else {
      throw new RuntimeException("unable to process type: " + type);
    }
    return result;
  }
}
