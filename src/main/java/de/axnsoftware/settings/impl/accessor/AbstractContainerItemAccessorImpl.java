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

import de.axnsoftware.settings.impl.IContainerItemAccessor;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractContainerItemAccessorImpl<T> extends AbstractPropertyAccessorImpl implements IContainerItemAccessor<T> {

    private T itemKey;

    protected AbstractContainerItemAccessorImpl() {
        super();
    }

    @Override
    public T getItemKey() {
        return this.itemKey;
    }

    @Override
    public String getKey() {
        return this.getItemKey().toString();
    }

    @Override
    public void setItemKey(final T itemKey) {
        this.itemKey = itemKey;
    }
}
