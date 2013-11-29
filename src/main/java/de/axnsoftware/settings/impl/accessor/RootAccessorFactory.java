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
import de.axnsoftware.settings.impl.visitor.IVisitor;
import de.axnsoftware.settings.impl.visitor.PropertyClassVisitorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The final class RootAccessorFactory models a factory for instances of the
 * {@code IAccessor} interface that represent the root of a hierarchy of
 * accessors for traversing and accessing the properties of instances of classes
 * that have been annotated with the {@code PropertyClass} annotation.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class RootAccessorFactory {

    /**
     * Returns a new instance of this.
     *
     * @return the instance
     */
    public static RootAccessorFactory newInstance() {
        return new RootAccessorFactory();
    }

    /**
     * Builds and returns a new instance of the {@code IAccessor} interface for
     * the specified {@code type}, which must have been annotated using the
     * {@code PropertyClass} annotation.
     *
     * @param type
     * @return the root accessor
     */
    public IAccessor buildRootAccessor(final Class<?> type) {
        final IAccessor result = new RootAccessorImpl();
        final List<IAccessor> childAccessors = new ArrayList<>();
        result.setChildAccessors(childAccessors);
        result.setKey("");
        result.setType(type);
        final Map<Class<?>, ITypeMapper> typeMappings = result.getTypeMappings();
        typeMappings.putAll(DefaultTypeMapperImpl.getPreparedDefaultTypeMappings());
        IVisitor visitor = new PropertyClassVisitorImpl();
        if (visitor.canVisit(type)) {
            visitor.visit(type, result);
        } else {
            throw new RuntimeException("TODO:unable to process type: " + type);
        }
        return result;
    }
}
