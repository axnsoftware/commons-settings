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

import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IContainerPropertyAccessor;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractContainerPropertyAccessorImpl extends AbstractPropertyAccessorImpl implements IContainerPropertyAccessor {

    protected IAccessor itemAccessorTemplate;

    protected AbstractContainerPropertyAccessorImpl() {
        super();
    }

    @Override
    public Object clone() {
        AbstractContainerPropertyAccessorImpl result = (AbstractContainerPropertyAccessorImpl) super.clone();
        IAccessor accessorTemplate = (IAccessor) this.getItemAccessorTemplate().clone();
        accessorTemplate.setParentAccessor(result);
        result.setItemAccessorTemplate(accessorTemplate);
        return result;
    }

    @Override
    public IAccessor getItemAccessorTemplate() {
        return this.itemAccessorTemplate;
    }

    @Override
    public void setItemAccessorTemplate(final IAccessor itemAccessorTemplate) {
        this.itemAccessorTemplate = itemAccessorTemplate;
    }
}
