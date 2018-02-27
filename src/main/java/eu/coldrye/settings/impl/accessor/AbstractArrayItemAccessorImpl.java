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

/**
 * The abstract class AbstractArrayItemAccessorImpl models the root of a
 * hierarchy of derived implementation classes and it provides the default
 * behaviour for all implementations of the {@code ContainerItemAccessor}
 * interface for all {@code Array} properties.
 *
 * @since 1.0.0
 */
public abstract class AbstractArrayItemAccessorImpl
  extends AbstractContainerItemAccessorImpl<Integer> {

  @Override
  public Object getValue(Object settingsRoot) {

    Object[] container = (Object[]) getParentAccessor().getValue(settingsRoot);
    return container[getItemKey()];
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    Object[] container = (Object[]) getParentAccessor().getValue(settingsRoot);
    container[getItemKey()] = value;
  }
}
