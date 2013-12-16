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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The abstract class AbstractAccessorImpl models the root of a hierarchy of
 * derived classes and it provides the default behaviour for all implementations
 * of the {@code IAccessor} interface.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractAccessorImpl
        implements IAccessor
{

    private List<IAccessor> childAccessors;
    private String key;
    private transient String cachedQualifiedKey;
    private IAccessor parentAccessor;
    private transient IAccessor cachedRootAccessor;
    private Class<?> type;

    protected AbstractAccessorImpl()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone()
    {
        IAccessor result = null;
        try
        {
            result = (IAccessor) super.clone();
            ((AbstractAccessorImpl) result).cachedQualifiedKey = null;
            ((AbstractAccessorImpl) result).cachedRootAccessor = null;
            if (null != this.getChildAccessors())
            {
                List<IAccessor> newChildAccessors = new ArrayList<>();
                for (final IAccessor childAccessor : this.getChildAccessors())
                {
                    final IAccessor newChildAccessor = (IAccessor) childAccessor
                            .clone();
                    newChildAccessor.setParentAccessor(result);
                    newChildAccessors.add(newChildAccessor);
                }
                result.setChildAccessors(newChildAccessors);
            }
        }
        catch (CloneNotSupportedException e)
        {
            // Must never happen
            throw new RuntimeException("unable to clone accessor.", e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<IAccessor> getChildAccessors()
    {
        return childAccessors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey()
    {
        return this.key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccessor getParentAccessor()
    {
        return this.parentAccessor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getQualifiedKey()
    {
        if (null == this.cachedQualifiedKey)
        {
            IAccessor accessor = this.getParentAccessor();
            if (null != accessor && !"".equals(accessor.getKey()))
            {
                this.cachedQualifiedKey = accessor.getQualifiedKey() + "."
                                          + this.getKey();
            }
            else
            {
                this.cachedQualifiedKey = this.getKey();
            }
        }
        return this.cachedQualifiedKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccessor getRootAccessor()
    {
        if (null == this.cachedRootAccessor)
        {
            IAccessor current = this;
            while (null != current.getParentAccessor())
            {
                current = current.getParentAccessor();
            }
            this.cachedRootAccessor = current;
        }
        return this.cachedRootAccessor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<?>, ITypeMapper> getTypeMappings()
    {
        return this.getRootAccessor().getTypeMappings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Class<?> getType()
    {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setChildAccessors(final List<IAccessor> childAccessors)
    {
        this.childAccessors = childAccessors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setKey(final String key)
    {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setParentAccessor(final IAccessor parentAccessor)
    {
        this.parentAccessor = parentAccessor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setType(final Class<?> type)
    {
        this.type = type;
    }
}
