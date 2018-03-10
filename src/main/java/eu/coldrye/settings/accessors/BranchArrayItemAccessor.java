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

package eu.coldrye.settings.accessors;

import eu.coldrye.settings.util.ReflectionUtils;

/**
 * The class BranchArrayItemAccessor models a concrete implementation
 * of the {@code Accessor} interface, responsible for accessing items in
 * {@code Array}S that are instances of {@code PropertyClass} annotated classes.
 *
 * @since 1.0.0
 */
public class BranchArrayItemAccessor extends AbstractContainerItemAccessor<Integer>
  implements ArrayItemAccessor {

  @Override
  public void copyValue(Object source, Object target) {

    Object copy = ReflectionUtils.newInstance(getType());
    setValue(copy, target);
    ArrayItemAccessor.super.copyValue(source, target);
  }
}
