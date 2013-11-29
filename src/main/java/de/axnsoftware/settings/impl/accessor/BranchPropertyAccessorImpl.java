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

import de.axnsoftware.settings.impl.IPropertyAccessor;
import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class BranchPropertyAccessorImpl extends AbstractPropertyAccessorImpl {

    public BranchPropertyAccessorImpl() {
        super();
    }

    @Override
    public void copyValue(final Object source, final Object target) {
        for (IAccessor childAccessor : this.getChildAccessors()) {
            ((IPropertyAccessor) childAccessor).copyValue(source, target);
        }
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = super.getValue(settingsRoot);
        if (null == result) {
            try {
                result = this.getType().newInstance();
                this.setValue(result, settingsRoot);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public void readFromProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        for (IAccessor childAccessor : this.getChildAccessors()) {
            ((IPropertyAccessor) childAccessor).readFromProperties(properties, settingsRoot);
        }
    }

    @Override
    public void writeToProperties(final IMutableBackingStoreWrapper properties, final Object settingsRoot) {
        for (IAccessor childAccessor : this.getChildAccessors()) {
            ((IPropertyAccessor) childAccessor).writeToProperties(properties, settingsRoot);
        }
    }
}
