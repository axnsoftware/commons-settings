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

package eu.coldrye.settings;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation Property must be present on all fields of a {@link PropertyClass} which should be read from or
 * written to the backend store. The system will ignore all fields of a property class that have not been annotated by
 * this.
 *
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Property {

  /**
   * The key specifies the relative key by which the property is addressed in the underlying store.
   * <p>
   * By default, the annotated field's name will be used. In order to override the default behaviour, a custom key must
   * be specified.
   * <p>
   * Please note that the specified key must adhere to the standard Java package name production rules.
   *
   * @return the specified key or the empty string
   */
  String key() default "";

  /**
   * For simple types, e.g. Integer or String, and custom types for which there exists a type mapper, one can define a
   * default value.
   *
   * @return the default value as a string or the empty string
   */
  String defaultValue() default "";

  /**
   * For custom types, which have not been annotated by the {@link Property} annotation, custom type mappers
   * {@link TypeMapper} can be specified.
   *
   * @return the fully qualified class name of the type mapper implementation
   */
  Class<? extends TypeMapper> typeMapper() default TypeMapper.class;

  /**
   * TODO document
   *
   * @return
   */
  boolean mandatory() default false;

  /*
  String regex() default "";
  ...
   */
}
