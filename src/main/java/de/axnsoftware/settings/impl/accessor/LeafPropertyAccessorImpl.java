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

import de.axnsoftware.settings.ITypeMapper;
import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class LeafPropertyAccessorImpl extends AbstractPropertyAccessorImpl {

    public LeafPropertyAccessorImpl() {
        super();
    }

    @Override
    public void copyValue(final Object source, final Object target) {
        Object value = this.getValue(source);
        if (value != null) {
            ITypeMapper mapper = this.getTypeMappings().get(value.getClass());
            Object copy = mapper.copyOf(value);
            this.setValue(copy, target);
        }
    }

    @Override
    public void readFromProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        Object value = this.getTypeMappings().get(this.getType()).valueOf(properties.getProperty(this.getQualifiedKey()), this.getType());
        if (value != null) {
            this.setValue(value, settingsRoot);
        }
    }

    @Override
    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        Object value = this.getValue(settingsRoot);
        if (value != null) {
            properties.setProperty(this.getQualifiedKey(), this.getTypeMappings().get(this.getType()).valueOf(value));
        }
    }
}
