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
import de.axnsoftware.settings.impl.accessor.DefaultTypeMapperImpl;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import de.axnsoftware.settings.impl.accessor.IPropertyAccessor;
import de.axnsoftware.settings.util.DefaultValueHolder;
import de.axnsoftware.settings.impl.accessor.LeafPropertyAccessorImpl;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * The final class SimpleTypeFieldVisitorImpl models a concrete implementation
 * of the {@code IVisitor} interface that is responsible for visiting all simple
 * typed fields, such as {@code Integer} or {@code Enum} typed fields.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class SimpleTypeFieldVisitorImpl
        extends AbstractFieldVisitorImpl
{

    private static IVisitor<Field>[] preparedSimpleTypeFieldVisitors;
    private final Class<?> valueType;
    private Property propertyAnnotation;

    public SimpleTypeFieldVisitorImpl(final Class<?> valueType)
    {
        this.valueType = valueType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean canVisitImpl(final Field visitee)
    {
        this.propertyAnnotation = null;
        Boolean result = Boolean.FALSE;
        Class<?> type = visitee.getType();
        if (this.valueType.isAssignableFrom(type) && visitee
                .isAnnotationPresent(Property.class))
        {
            /*
             * must set propertyAnnotation now or otherwise this will fail on
             * visiting all default supported type typed fields.
             */
            this.propertyAnnotation = visitee.getAnnotation(Property.class);
            if (DefaultTypeMapperImpl.getPreparedDefaultTypeMappings()
                    .containsKey(type))
            {
                result = Boolean.TRUE;
            }
            else
            {
                if ("".equals(this.propertyAnnotation.typeMapper()) && type
                        .isAnnotationPresent(Property.class))
                {
                    this.propertyAnnotation = type.getAnnotation(Property.class);
                }
                if (!"".equals(this.propertyAnnotation.typeMapper()))
                {
                    result = Boolean.TRUE;
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Field visitee, final IAccessor parentAccessor)
    {
        Class<?> type = visitee.getType();
        IPropertyAccessor accessor = new LeafPropertyAccessorImpl();
        ITypeMapper typeMapper = VisitorUtils.registerOrGetExistingTypeMapper(
                type, this.propertyAnnotation.typeMapper(), parentAccessor
                .getTypeMappings());
        DefaultValueHolder defaultValueHolder = new DefaultValueHolder(
                this.propertyAnnotation.defaultValue(), type, typeMapper);
        accessor.setDefaultValueHolder(defaultValueHolder);
        VisitorUtils.configureAccessor(accessor, parentAccessor, visitee);
    }

    @SuppressWarnings("unchecked")
    public static IVisitor<Field>[] getPreparedSimpleTypeFieldVisitors()
    {
        if (null == preparedSimpleTypeFieldVisitors)
        {
            preparedSimpleTypeFieldVisitors = new IVisitor[]
            {
                new SimpleTypeFieldVisitorImpl(BigDecimal.class),
                new SimpleTypeFieldVisitorImpl(BigInteger.class),
                new SimpleTypeFieldVisitorImpl(Boolean.class),
                new SimpleTypeFieldVisitorImpl(Byte.class),
                new SimpleTypeFieldVisitorImpl(Double.class),
                new SimpleTypeFieldVisitorImpl(Enum.class),
                new SimpleTypeFieldVisitorImpl(Float.class),
                new SimpleTypeFieldVisitorImpl(Integer.class),
                new SimpleTypeFieldVisitorImpl(Long.class),
                new SimpleTypeFieldVisitorImpl(Short.class),
                new SimpleTypeFieldVisitorImpl(String.class),
                new SimpleTypeFieldVisitorImpl(UUID.class),
                /*
                 * Visitor for custom types with an appropriate type mapper
                 */
                new SimpleTypeFieldVisitorImpl(Object.class)
            };
        }
        return preparedSimpleTypeFieldVisitors.clone();
    }
}
