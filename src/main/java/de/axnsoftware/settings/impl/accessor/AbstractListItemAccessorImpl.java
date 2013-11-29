/*
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
package de.axnsoftware.settings.impl.accessor;

import java.util.List;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractListItemAccessorImpl extends AbstractContainerItemAccessorImpl<Integer> {

    protected AbstractListItemAccessorImpl() {
        super();
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = null;
        List<Object> container = (List<Object>) this.getParentAccessor().getValue(settingsRoot);
        Integer itemKey = this.getItemKey();
        if (itemKey < container.size()) {
            result = container.get(this.getItemKey());
        }
        return result;
    }

    @Override
    public void setValue(final Object value, final Object settingsRoot) {
        List<Object> container = (List<Object>) this.getParentAccessor().getValue(settingsRoot);
        if (this.getItemKey() < container.size()) {
            container.set(this.getItemKey(), value);
        } else {
            container.add(value);
        }
    }
}
