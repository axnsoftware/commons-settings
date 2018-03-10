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

/**
 * The class FailFastVisitor models a concrete implementation of the
 * {@code Visitor} interface that acts as a sentinel. When accessed it will
 * cause the visitation process to be stopped.
 *
 * @since 1.0.0
 */
public class FailFastVisitor<T> implements Visitor<T> {

  @Override
  public boolean canVisit(T visitee) {

    throw new RuntimeException(String.format(
      "Unsupported type for field: %s. Did you forget to annotate the class with the "
        + "PropertyClass annotation or provide a type mapper?", visitee.toString()));
  }

  @Override
  public void visit(T visitee, Accessor parentAccessor) {

    throw new RuntimeException(String.format(
      "Unsupported type for field: %s.  Did you forget to annotate the class with the "
        + "PropertyClass annotation or provide a type mapper?", visitee.toString()));
  }
}
