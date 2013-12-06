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
import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import de.axnsoftware.settings.impl.accessor.IPropertyAccessor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * The abstract class AbstractFieldVisitorImpl models the root of a hierarchy of
 * derived implementation classes and it provides the default behaviour for all
 * implementations of the {@code IVisitor} interface. It also encapsulated
 * commonly used code to be reused by the concrete implementations.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public abstract class AbstractFieldVisitorImpl
        implements IVisitor<Field>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public final Boolean canVisit(final Field visitee)
    {
        Boolean result = Boolean.FALSE;
        if (visitee.isAnnotationPresent(Property.class))
        {
            result = this.canVisitImpl(visitee);
        }
        return result;
    }

    /**
     * Delegated from by {@link #canVisit(java.lang.reflect.Field)}. Derived
     * classes must implement this.
     *
     * @param visitee
     * @return true whether the visitee can be visited, false otherwise
     */
    protected abstract Boolean canVisitImpl(final Field visitee);

    /**
     * Configures the specified {@code accessor} and adds it as a child accessor
     * to the specified {@code parentAccessor}.
     *
     * @param accessor
     * @param parentAccessor
     * @param visitee
     */
    protected final void configureAccessor(final IPropertyAccessor accessor,
                                           final IAccessor parentAccessor,
                                           final Field visitee)
    {
        final Class<?> type = visitee.getType();
        final Property annotation = visitee.getAnnotation(Property.class);
        final String fieldName = visitee.getName();
        final String capitalizedFieldName = fieldName.substring(0, 1)
                .toUpperCase() + fieldName.substring(1);
        final String getterName = "get" + capitalizedFieldName;
        final String setterName = "set" + capitalizedFieldName;
        String key = fieldName;
        if (!"".equals(annotation.key()))
        {
            key = annotation.key();
        }
        accessor.setKey(key);
        accessor.setParentAccessor(parentAccessor);
        accessor.setGetter(this.getMethod(visitee, getterName));
        accessor.setSetter(this.getMethod(visitee, setterName, type));
        accessor.setType(type);
        if (null == parentAccessor.getChildAccessors())
        {
            parentAccessor.setChildAccessors(new ArrayList<IAccessor>());
        }
        parentAccessor.getChildAccessors().add(accessor);
    }

    /**
     * Gets an instance of the {@code ITypeMapper} interface for the specified
     * {@code typeMapperTypename}. Also registers the instance with the
     * specified {@code typeMappings} for the specified {@code type}.
     *
     * @param type
     * @param typeMapperTypename
     * @param typeMappings
     * @return instance of the type mapper
     */
    protected final ITypeMapper getAndRegisterTypeMapper(
            final Class<?> type,
            final String typeMapperTypename,
            final Map<Class<?>, ITypeMapper> typeMappings)
    {
        ITypeMapper result = typeMappings.get(type);
        if (null == result)
        {
            if (!"".equals(typeMapperTypename))
            {
                try
                {
                    final Class<? extends ITypeMapper> typeMapperType;
                    typeMapperType = (Class<? extends ITypeMapper>) Class
                            .forName(typeMapperTypename);
                    for (ITypeMapper typeMapper : typeMappings.values())
                    {
                        if (typeMapperType.equals(typeMapper.getClass()))
                        {
                            result = typeMapper;
                            break;
                        }
                    }
                    if (null == result)
                    {
                        result = typeMapperType.newInstance();
                    }
                    typeMappings.put(type, result);
                }
                catch (ClassNotFoundException | InstantiationException |
                       IllegalAccessException e)
                {
                    throw new RuntimeException(
                            "TODO: type mapper could not be instantiated: "
                            + typeMapperTypename, e);
                }
            }
            else if (type.isEnum())
            {
                result = typeMappings.get(Enum.class);
            }
        }
        return result;
    }

    /**
     * Gets the method specified by {@code methodName} for the optionally
     * specified {@code parameterTypes} from the specified {@code visitee}'s
     * declaring class.
     *
     * @param visitee
     * @param methodName
     * @param parameterTypes
     * @return the method
     */
    protected final Method getMethod(final Field visitee,
                                     final String methodName,
                                     final Class<?>... parameterTypes)
    {
        Method result = null;
        try
        {
            result = visitee.getDeclaringClass().getMethod(methodName,
                                                           parameterTypes);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            throw new RuntimeException("unable to access method " + methodName
                                       + " for field:" + visitee.toString(), e);
        }
        return result;
    }
}
