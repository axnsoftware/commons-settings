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
package de.axnsoftware.settings.impl.visitor;

import de.axnsoftware.settings.ITypeMapper;
import de.axnsoftware.settings.impl.IVisitor;
import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IPropertyAccessor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractFieldVisitorImpl implements IVisitor<Field> {

    @Override
    public final Boolean canVisit(final Field visitee) {
        Boolean result = Boolean.FALSE;
        if (visitee.isAnnotationPresent(Property.class)) {
            result = this.canVisitImpl(visitee);
        }
        return result;
    }

    protected abstract Boolean canVisitImpl(final Field visitee);

    protected void configureAccessor(final IPropertyAccessor accessor, final IAccessor parentAccessor, final Field visitee) {
        final Class<?> type = visitee.getType();
        final Property annotation = visitee.getAnnotation(Property.class);
        final String fieldName = visitee.getName();
        final String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        final String getterName = "get" + capitalizedFieldName;
        final String setterName = "set" + capitalizedFieldName;
        String key = fieldName;
        if (!"".equals(annotation.key())) {
            key = annotation.key();
        }
        accessor.setKey(key);
        accessor.setParentAccessor(parentAccessor);
        accessor.setGetter(this.getMethod(visitee, getterName));
        accessor.setSetter(this.getMethod(visitee, setterName, type));
        accessor.setType(type);
        if (null == parentAccessor.getChildAccessors()) {
            parentAccessor.setChildAccessors(new ArrayList<IAccessor>());
        }
        parentAccessor.getChildAccessors().add(accessor);
    }

    protected ITypeMapper getAndRegisterTypeMapper(final Class<?> type, final String typeMapperTypename, final Map<Class<?>, ITypeMapper> typeMappings) {
        ITypeMapper result = typeMappings.get(type);
        if (null == result) {
            if (!"".equals(typeMapperTypename)) {
                try {
                    final Class<? extends ITypeMapper> typeMapperType = (Class<? extends ITypeMapper>) Class.forName(typeMapperTypename);
                    for (ITypeMapper typeMapper : typeMappings.values()) {
                        if (typeMapperType.equals(typeMapper.getClass())) {
                            result = typeMapper;
                            break;
                        }
                    }
                    if (null == result) {
                        result = typeMapperType.newInstance();
                    }
                    typeMappings.put(type, result);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("TODO: type mapper could not be instantiated: " + typeMapperTypename, e);
                }
            } else if (type.isEnum()) {
                result = typeMappings.get(Enum.class);
            }
        }
        return result;
    }

    protected Method getMethod(final Field visitee, final String methodName, final Class<?>... parameterTypes) {
        Method result = null;
        try {
            result = visitee.getDeclaringClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("unable to access method " + methodName + " for field:" + visitee.toString(), e);
        }
        return result;
    }
}
