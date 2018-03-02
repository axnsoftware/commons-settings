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

package eu.coldrye.settings.impl.accessor;

import java.util.List;

public interface ListItemAccessor extends ContainerItemAccessor<Integer> {

  @Override
  @SuppressWarnings("unchecked")
  default Object getValue(Object settingsRoot) {

    List<Object> container = (List<Object>) getParentAccessor().getValue(settingsRoot);
    return container.get(getItemKey());
  }

  @Override
  @SuppressWarnings("unchecked")
  default void setValue(Object value, Object settingsRoot) {

    List<Object> container = (List<Object>) getParentAccessor().getValue(settingsRoot);
    Integer itemKey = getItemKey();
    if (itemKey < container.size()) {
      container.set(itemKey, value);
    } else {
      container.add(value);
    }
  }
}
