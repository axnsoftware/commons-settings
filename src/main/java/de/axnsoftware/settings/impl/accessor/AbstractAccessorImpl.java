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
import de.axnsoftware.settings.impl.IAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractAccessorImpl implements IAccessor {

    private List<IAccessor> childAccessors;
    private String key;
    private transient String cachedQualifiedKey;
    private IAccessor parentAccessor;
    private transient IAccessor cachedRootAccessor;
    private Class<?> type;

    protected AbstractAccessorImpl() {
    }

    @Override
    public Object clone() {
        IAccessor result = null;
        try {
            result = (IAccessor) super.clone();
            ((AbstractAccessorImpl) result).cachedQualifiedKey = null;
            ((AbstractAccessorImpl) result).cachedRootAccessor = null;
            if (null != this.getChildAccessors()) {
                List<IAccessor> newChildAccessors = new ArrayList<>();
                for (final IAccessor childAccessor : this.getChildAccessors()) {
                    final IAccessor newChildAccessor = (IAccessor) childAccessor.clone();
                    newChildAccessor.setParentAccessor(result);
                    newChildAccessors.add(newChildAccessor);
                }
                result.setChildAccessors(newChildAccessors);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("TODO:must never happen.", e);
        }
        return result;
    }

    @Override
    public List<IAccessor> getChildAccessors() {
        return childAccessors;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public IAccessor getParentAccessor() {
        return this.parentAccessor;
    }

    @Override
    public String getQualifiedKey() {
        if (null == this.cachedQualifiedKey) {
            IAccessor accessor = this.getParentAccessor();
            if (null != accessor && !"".equals(accessor.getKey())) {
                this.cachedQualifiedKey = accessor.getQualifiedKey() + "." + this.getKey();
            } else {
                this.cachedQualifiedKey = this.getKey();
            }
        }
        return this.cachedQualifiedKey;
    }

    @Override
    public IAccessor getRootAccessor() {
        if (null == this.cachedRootAccessor) {
            IAccessor current = this;
            while (null != current.getParentAccessor()) {
                current = current.getParentAccessor();
            }
            this.cachedRootAccessor = current;
        }
        return this.cachedRootAccessor;
    }

    @Override
    public Map<Class<?>, ITypeMapper> getTypeMappings() {
        return this.getRootAccessor().getTypeMappings();
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public void setChildAccessors(final List<IAccessor> childAccessors) {
        this.childAccessors = childAccessors;
    }

    @Override
    public void setKey(final String key) {
        this.key = key;
    }

    @Override
    public void setParentAccessor(final IAccessor parentAccessor) {
        this.parentAccessor = parentAccessor;
    }

    @Override
    public void setType(final Class<?> type) {
        this.type = type;
    }
}
