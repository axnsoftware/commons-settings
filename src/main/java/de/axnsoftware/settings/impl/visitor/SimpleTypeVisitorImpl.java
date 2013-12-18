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

import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.impl.accessor.DefaultTypeMapperImpl;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * The final class AbstractFieldVisitorImpl models a concrete implementation of
 * the {@code IVisitor} interface that is responsible for visiting classes that
 * have been annotated with the {@code PropertyClass} annotation.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SimpleTypeVisitorImpl
        implements IVisitor<Class<?>>
{

    private static IVisitor<Class<?>>[] preparedSimpleTypeVisitors;
    private final Class<?> valueType;
    private Boolean isAnnotatedType;

    public SimpleTypeVisitorImpl(final Class<?> valueType)
    {
        this.valueType = valueType;
        this.isAnnotatedType = Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean canVisit(final Class<?> visitee)
    {
        Boolean result = Boolean.FALSE;
        this.isAnnotatedType = Boolean.FALSE;
        if (this.valueType.isAssignableFrom(visitee))
        {
            if (DefaultTypeMapperImpl.getPreparedDefaultTypeMappings()
                    .containsKey(visitee))
            {
                result = Boolean.TRUE;
            }
            else if (visitee.isAnnotationPresent(Property.class))
            {
                Property annotation = visitee.getAnnotation(Property.class);
                if (!"".equals(annotation.typeMapper()))
                {
                    result = Boolean.TRUE;
                    this.isAnnotatedType = Boolean.TRUE;
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Class<?> visitee, final IAccessor parentAccessor)
    {
        if (this.isAnnotatedType)
        {
            Property annotation = visitee.getAnnotation(Property.class);
            if (!"".equals(annotation.typeMapper()))
            {
                VisitorUtils.registerOrGetExistingTypeMapper(visitee, annotation
                        .typeMapper(), parentAccessor.getTypeMappings());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static IVisitor<Class<?>>[] getPreparedSimpleTypeVisitors()
    {
        if (null == preparedSimpleTypeVisitors)
        {
            preparedSimpleTypeVisitors = new IVisitor[]
            {
                new SimpleTypeVisitorImpl(BigDecimal.class),
                new SimpleTypeVisitorImpl(BigInteger.class),
                new SimpleTypeVisitorImpl(Boolean.class),
                new SimpleTypeVisitorImpl(Byte.class),
                new SimpleTypeVisitorImpl(Double.class),
                new SimpleTypeVisitorImpl(Enum.class),
                new SimpleTypeVisitorImpl(Float.class),
                new SimpleTypeVisitorImpl(Integer.class),
                new SimpleTypeVisitorImpl(Long.class),
                new SimpleTypeVisitorImpl(Short.class),
                new SimpleTypeVisitorImpl(String.class),
                new SimpleTypeVisitorImpl(UUID.class),
                /*
                 * Visitor for custom types with an appropriate type mapper
                 */
                new SimpleTypeVisitorImpl(Object.class)
            };
        }
        return preparedSimpleTypeVisitors.clone();
    }
}
