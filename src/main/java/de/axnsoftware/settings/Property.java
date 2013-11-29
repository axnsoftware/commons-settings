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
package de.axnsoftware.settings;

import de.axnsoftware.settings.impl.DefaultTypeMapperImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation Property must be present on all fields of a
 * {@link PropertyClass} which should be read from or written to the backend
 * store.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Property {

    /**
     * The key specifies the relative key by which the property is addressed in
     * the underlying store.
     *
     * By default, the annotated field's name will be used. In order to override
     * the default behaviour, a custom key must be specified.
     *
     * Please note that the specified key must adhere to the standard Java
     * package name production rules.
     *
     * @return the specified key or the empty string
     */
    public String key() default "";

    /**
     * For simple types, e.g. Integer or String, and custom types for which
     * there exists a type mapper, one can define a default value. The default
     * value will be set when loading the {@link PropertyClass} via its
     * associated {@link ISettingsStore}.
     *
     * @return the default value as a string or the empty string
     */
    public String defaultValue() default "";

    /**
     * For custom types, which have not been annotated by the
     * {@link PropertyClass} annotation, custom type mappers {@link ITypeMapper}
     * can be specified. By default, the {@link DefaultTypeMapperImpl} will be
     * used.
     *
     * @return the fully qualified class name of the type mapper implementation
     * or the empty string
     */
    public String typeMapper() default "";
}
