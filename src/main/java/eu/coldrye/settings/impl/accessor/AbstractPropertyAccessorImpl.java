/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.impl.accessor;

import eu.coldrye.settings.util.DefaultValueHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The abstract class AbstractPropertyAccessorImpl models the root of a
 * hierarchy of derived implementation classes and it provides the default
 * behaviour for all implementations of the {@code PropertyAccessor} interface.
 *
 * @since 1.0.0
 */
public abstract class AbstractPropertyAccessorImpl extends AbstractAccessorImpl implements PropertyAccessor {

  private DefaultValueHolder defaultValueHolder;

  private Method getter;

  private Method setter;

  private boolean mandatory;

  protected AbstractPropertyAccessorImpl() {

    super();
  }

  @Override
  public DefaultValueHolder getDefaultValueHolder() {

    return defaultValueHolder;
  }

  @Override
  public Method getGetter() {

    return getter;
  }

  @Override
  public Method getSetter() {

    return setter;
  }

  public boolean getMandatory() {

    return mandatory;
  }

  public void setMandatory(boolean mandatory) {

    this.mandatory = mandatory;
  }

  @Override
  public Object getValue(Object settingsRoot) {

    Object result;
    Object valueHolder = settingsRoot;
    Accessor parentAccessor = getParentAccessor();
    if (parentAccessor != null) {
      valueHolder = parentAccessor.getValue(settingsRoot);
    }
    Method get = getGetter();
    // FIXME valueHolder is either simple value or instance of ...
    if (!get.getDeclaringClass().equals(valueHolder.getClass())) {
      throw new IllegalStateException(
        "valueHolder is of an unexpected type, expected: "
          + get.getDeclaringClass().getName()
          + ", was: " + valueHolder.getClass().getName());
    }
    try {
      result = get.invoke(valueHolder);
      DefaultValueHolder holder = getDefaultValueHolder();
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
  public void setGetter(Method getter) {

    this.getter = getter;
  }

  @Override
  public void setSetter(Method setter) {

    this.setter = setter;
  }

  @Override
  public void setValue(Object value, Object settingsRoot) {

    Object valueHolder = settingsRoot;
    Accessor parentAccessor = getParentAccessor();
    if (parentAccessor != null) {
      valueHolder = parentAccessor.getValue(settingsRoot);
    }
    Method set = getSetter();
    if (!set.getDeclaringClass().equals(valueHolder.getClass())) {
      throw new IllegalStateException(
        "valueHolder is of an unexpected type, expected: "
          + set.getDeclaringClass().getName()
          + ", was: " + valueHolder.getClass().getName());
    }
    try {
      set.invoke(valueHolder, value);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}