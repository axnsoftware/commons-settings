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

import de.axnsoftware.settings.PropertyClass;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The final class AbstractFieldVisitorImpl models a concrete implementation of
 * the {@link IVisitor} interface that is responsible for visiting classes that
 * have been annotated with the {@link PropertyClass} annotation.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class PropertyClassVisitorImpl
        implements IVisitor<Class<?>>
{

    private List<IVisitor<Field>> fieldVisitors;

    public PropertyClassVisitorImpl()
    {
        this.fieldVisitors = new ArrayList<>();
        this.fieldVisitors.addAll(Arrays.asList(SimpleTypeFieldVisitorImpl
                .getPreparedSimpleTypeFieldVisitors()));
        this.fieldVisitors.add(new ArrayFieldVisitorImpl(this));
        this.fieldVisitors.add(new ListFieldVisitorImpl(this));
        this.fieldVisitors.add(new MapFieldVisitorImpl(this));
        this.fieldVisitors.add(new PropertyClassFieldVisitorImpl(this));
        this.fieldVisitors.add(new FailFastVisitorImpl<Field>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean canVisit(final Class<?> visitee)
    {
        return visitee.isAnnotationPresent(PropertyClass.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Class<?> visitee, final IAccessor parentAccessor)
    {
        if (null == parentAccessor.getChildAccessors())
        {
            parentAccessor.setChildAccessors(new ArrayList<IAccessor>());
        }
        for (Field field : visitee.getDeclaredFields())
        {
            for (IVisitor visitor : this.fieldVisitors)
            {
                if (visitor.canVisit(field))
                {
                    visitor.visit(field, parentAccessor);
                    break;
                }
            }
        }
    }
}
