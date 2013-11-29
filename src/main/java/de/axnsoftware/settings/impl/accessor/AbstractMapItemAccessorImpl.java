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

import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractMapItemAccessorImpl extends AbstractContainerItemAccessorImpl<String> {

    protected AbstractMapItemAccessorImpl() {
        super();
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Map<String, Object> container = (Map<String, Object>) this.getParentAccessor().getValue(settingsRoot);
        return container.get(this.getItemKey());
    }

    @Override
    public void setValue(final Object value, final Object settingsRoot) {
        Map<String, Object> container = (Map<String, Object>) this.getParentAccessor().getValue(settingsRoot);
        container.put(this.getItemKey(), value);
    }
}
