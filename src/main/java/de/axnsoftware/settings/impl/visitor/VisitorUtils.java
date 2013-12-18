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
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public final class VisitorUtils
{

    /**
     * Must not be instantiated.
     */
    private VisitorUtils()
    {
    }

    public static String capitalizeFieldName(final Field field)
    {
        final String fieldName = field.getName();
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * Configures the specified {@code accessor} and adds it as a child accessor
     * to the specified {@code parentAccessor}.
     *
     * @param accessor
     * @param parentAccessor
     * @param visitee
     */
    public static void configureAccessor(final IPropertyAccessor accessor,
                                         final IAccessor parentAccessor,
                                         final Field visitee)
    {
        final Class<?> type = visitee.getType();
        final Property annotation = visitee.getAnnotation(Property.class);
        String key = visitee.getName();
        if (!"".equals(annotation.key()))
        {
            key = annotation.key();
        }
        accessor.setKey(key);
        accessor.setParentAccessor(parentAccessor);
        accessor.setGetter(getMethod(visitee, getGetterName(visitee)));
        accessor.setSetter(getMethod(visitee, getSetterName(visitee), type));
        accessor.setType(type);
        if (null == parentAccessor.getChildAccessors())
        {
            parentAccessor.setChildAccessors(new ArrayList<IAccessor>());
        }
        parentAccessor.getChildAccessors().add(accessor);
    }

    public static String getGetterName(final Field field)
    {
        return "get" + capitalizeFieldName(field);
    }

    public static String getSetterName(final Field field)
    {
        return "set" + capitalizeFieldName(field);
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
    public static Method getMethod(final Field visitee,
                                   final String methodName,
                                   final Class<?>... parameterTypes)
    {
        Method result = null;
        try
        {
            result = visitee.getDeclaringClass().getMethod(methodName,
                                                           parameterTypes);
        }
        catch (NoSuchMethodException e)
        {
            // FIXME:requires more elaborate information for the user
            throw new RuntimeException("Unable to access method " + methodName
                                       + " for field:" + visitee.toString(), e);
        }
        return result;
    }

    public static Boolean isMethodAvailable(final Class<?> type,
                                            final String methodName,
                                            final Class<?> returnType,
                                            final Class<?>... parameterTypes)
    {
        Boolean result = Boolean.FALSE;
        try
        {
            type.getMethod(methodName, parameterTypes);
            result = Boolean.TRUE;
        }
        catch (NoSuchMethodException e)
        {
            // FIXME:requires more elaborate information for the user
        }
        return result;
    }

    public static Boolean isMutableField(final Field field)
    {
        Boolean result = Boolean.FALSE;
        Class<?> type = field.getDeclaringClass();
        Class<?> fieldType = field.getType();
        if (isMethodAvailable(type, getGetterName(field), fieldType)
            && isMethodAvailable(type, getSetterName(field), null, fieldType))
        {
            result = Boolean.TRUE;
        }
        return result;
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
    @SuppressWarnings("unchecked")
    public static ITypeMapper registerOrGetExistingTypeMapper(
            final Class<?> type, final String typeMapperTypename,
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
                    // FIXME:requires more elaborate information for the user
                    throw new RuntimeException(
                            "Unable to instantiate the specified type mapper: "
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
}
