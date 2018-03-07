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

package eu.coldrye.settings.util;

/**
 * The class DefaultValueHolder models a holder for a default value which was
 * declared by the {@code Property} annotation's {@code defaultValue} property.
 *
 * @since 1.0.0
 */
public class DefaultValueHolder {

  private Object cachedValue;

  private String defaultValue;

  private boolean doNotCacheAgain = false;

  private Class<?> type;

  public DefaultValueHolder(String defaultValue, Class<?> type) {

    if (null == type) {
      throw new IllegalArgumentException("type must not be null.");
    }
    this.defaultValue = defaultValue;
    this.type = type;
  }

  /**
   * Return the mapped default value.
   *
   * @return the mapped default value or null
   */
  public Object getValue() {

    /*
     * Do not resolve the cachedValue more than once when {@code null}.
     */
    if (cachedValue == null && !doNotCacheAgain) {
      // TODO:uncaught exceptions
      cachedValue = TypeMapperRegistry.INSTANCE.getTypeMapper(type).valueOf(defaultValue, type);
      doNotCacheAgain = true;
    }
    return cachedValue;
  }
}
