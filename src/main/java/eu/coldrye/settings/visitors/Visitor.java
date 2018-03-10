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
 * The interface IVistor models a generic visitors used in building a hierarchy
 * of {@code Accessor}S from both annotated {@code Class}es and {@code Field}S.
 *
 * @since 1.0.0
 */
public interface Visitor<T> {

  /**
   * Returns true whether visitors can visit the specified {@code visitee}.
   *
   * @param visitee
   * @return true whether the visitee can be visited, false otherwise
   */
  boolean canVisit(T visitee);

  /**
   * Visits the specified {@code visited} and contributes the specified
   * {@code parentAccessor} while doing so.
   *
   * @param visitee
   * @param parentAccessor
   */
  void visit(T visitee, Accessor parentAccessor);
}
