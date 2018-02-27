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

import eu.coldrye.settings.TypeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The abstract class AbstractAccessorImpl models the root of a hierarchy of
 * derived classes and it provides the default behaviour for all implementations
 * of the {@code Accessor} interface.
 *
 * @since 1.0.0
 */
public abstract class AbstractAccessorImpl implements Accessor {

  private List<Accessor> childAccessors = new ArrayList<>();

  private String key;

  private transient String cachedQualifiedKey;

  private Accessor parentAccessor;

  private transient Accessor cachedRootAccessor;

  private Class<?> type;

  protected AbstractAccessorImpl() {

  }

  @Override
  public Object clone() {

    Accessor result;
    try {
      result = (Accessor) super.clone();
      ((AbstractAccessorImpl) result).cachedQualifiedKey = null;
      ((AbstractAccessorImpl) result).cachedRootAccessor = null;
      if (null != this.getChildAccessors()) {
        List<Accessor> newChildAccessors = new ArrayList<>();
        for (Accessor childAccessor : getChildAccessors()) {
          Accessor newChildAccessor = (Accessor) childAccessor.clone();
          newChildAccessor.setParentAccessor(result);
          newChildAccessors.add(newChildAccessor);
        }
        result.setChildAccessors(newChildAccessors);
      }
    } catch (CloneNotSupportedException e) {
      // Must never happen
      throw new RuntimeException("unable to clone accessor.", e);
    }
    return result;
  }

  @Override
  public List<Accessor> getChildAccessors() {

    return childAccessors;
  }

  @Override
  public String getKey() {

    return key;
  }

  @Override
  public Accessor getParentAccessor() {

    return parentAccessor;
  }

  @Override
  public String getQualifiedKey() {

    if (null == cachedQualifiedKey) {
      Accessor accessor = getParentAccessor();
      if (null != accessor && !"".equals(accessor.getKey())) {
        cachedQualifiedKey = accessor.getQualifiedKey() + "." + getKey();
      } else {
        cachedQualifiedKey = getKey();
      }
    }
    return cachedQualifiedKey;
  }

  @Override
  public Accessor getRootAccessor() {

    if (null == cachedRootAccessor) {
      Accessor current = this;
      while (null != current.getParentAccessor()) {
        current = current.getParentAccessor();
      }
      cachedRootAccessor = current;
    }
    return cachedRootAccessor;
  }

  @Override
  public Map<Class<?>, TypeMapper> getTypeMappings() {

    return getRootAccessor().getTypeMappings();
  }

  @Override
  public Class<?> getType() {

    return type;
  }

  @Override
  public void setChildAccessors(List<Accessor> childAccessors) {

    this.childAccessors = childAccessors;
  }

  @Override
  public void setKey(String key) {

    this.key = key;
  }

  @Override
  public void setParentAccessor(Accessor parentAccessor) {

    this.parentAccessor = parentAccessor;
  }

  @Override
  public void setType(Class<?> type) {

    this.type = type;
  }
}
