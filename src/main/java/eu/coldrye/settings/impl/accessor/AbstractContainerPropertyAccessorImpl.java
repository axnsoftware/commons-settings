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

/**
 * The abstract class AbstractArrayItemAccessorImpl models the root of a
 * hierarchy of derived classes and it provides the default behaviour for all
 * implementations of the {@code ContainerPropertyAccessor} interface.
 *
 * @since 1.0.0
 */
public abstract class AbstractContainerPropertyAccessorImpl extends AbstractPropertyAccessorImpl
  implements ContainerPropertyAccessor {

  private Accessor itemAccessorTemplate;

  protected AbstractContainerPropertyAccessorImpl() {

    super();
  }

  @Override
  public final Object clone() {

    AbstractContainerPropertyAccessorImpl result;
    result = (AbstractContainerPropertyAccessorImpl) super.clone();
    Accessor accessorTemplate = (Accessor) getItemAccessorTemplate().clone();
    accessorTemplate.setParentAccessor(result);
    result.setItemAccessorTemplate(accessorTemplate);
    return result;
  }

  @Override
  public final Accessor getItemAccessorTemplate() {

    return itemAccessorTemplate;
  }

  @Override
  public final void setItemAccessorTemplate(Accessor itemAccessorTemplate) {

    this.itemAccessorTemplate = itemAccessorTemplate;
  }
}
