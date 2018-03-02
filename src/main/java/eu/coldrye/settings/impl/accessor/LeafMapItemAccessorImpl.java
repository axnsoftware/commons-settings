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
 * The class LeafMapItemAccessorImpl models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing simple type items
 * of {@code Map}S, such as {@code Integer} or {@code Enum}S.
 *
 * @since 1.0.0
 */
public class LeafMapItemAccessorImpl extends AbstractContainerItemAccessorImpl<String>
  implements MapItemAccessor, LeafItemAccessor {

}
