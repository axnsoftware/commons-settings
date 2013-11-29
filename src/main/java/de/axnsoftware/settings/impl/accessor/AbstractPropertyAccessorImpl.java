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
import de.axnsoftware.settings.impl.DefaultValueHolder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractPropertyAccessorImpl extends AbstractAccessorImpl implements IPropertyAccessor {

    private DefaultValueHolder defaultValueHolder;
    private Method getter;
    private Method setter;

    protected AbstractPropertyAccessorImpl() {
        super();
    }

    @Override
    public DefaultValueHolder getDefaultValueHolder() {
        return this.defaultValueHolder;
    }

    @Override
    public Method getGetter() {
        return this.getter;
    }

    @Override
    public Method getSetter() {
        return this.setter;
    }

    @Override
    public Object getValue(final Object settingsRoot) {
        Object result = null;
        Object valueHolder = settingsRoot;
        IAccessor parentAccessor = this.getParentAccessor();
        if (parentAccessor != null) {
            valueHolder = parentAccessor.getValue(settingsRoot);
        }
        final Method get = this.getGetter();
        if (!get.getDeclaringClass().equals(valueHolder.getClass())) {
            throw new IllegalStateException("TODO:unexpected class of valueHolder");
        }
        try {
            result = get.invoke(valueHolder);
            DefaultValueHolder holder = this.getDefaultValueHolder();
            if (null == result && null != holder) {
                result = holder.getValue();
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void setDefaultValueHolder(DefaultValueHolder defaultValueHolder) {
        this.defaultValueHolder = defaultValueHolder;
    }

    @Override
    public void setGetter(final Method getter) {
        this.getter = getter;
    }

    @Override
    public void setSetter(final Method setter) {
        this.setter = setter;
    }

    @Override
    public void setValue(final Object value, final Object settingsRoot) {
        Object valueHolder = settingsRoot;
        IAccessor parentAccessor = this.getParentAccessor();
        if (parentAccessor != null) {
            valueHolder = parentAccessor.getValue(settingsRoot);
        }
        final Method set = this.getSetter();
        if (!set.getDeclaringClass().equals(valueHolder.getClass())) {
            throw new IllegalStateException("TODO:unexpected class of valueHolder");
        }
        try {
            set.invoke(valueHolder, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
