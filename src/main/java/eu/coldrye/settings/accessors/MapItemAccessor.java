/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

import java.util.Map;

public interface MapItemAccessor extends ContainerItemAccessor<String> {

  @Override
  @SuppressWarnings("unchecked")
  default Object getValue(Object settingsRoot) {

    Map<String, Object> container = (Map<String, Object>) getParentAccessor().getValue(settingsRoot);
    return container.get(getItemKey());
  }

  @Override
  @SuppressWarnings("unchecked")
  default void setValue(Object value, Object settingsRoot) {

    Map<String, Object> container = (Map<String, Object>) getParentAccessor().getValue(settingsRoot);
    container.put(getItemKey(), value);
  }
}
