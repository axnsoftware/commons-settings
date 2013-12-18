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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public abstract class AbstractContainerFieldVisitorImpl
        extends AbstractFieldVisitorImpl
{

    private final List<IVisitor<Class<?>>> visitors;
    private Class<?> itemType;
    private IVisitor<Class<?>> itemVisitor;

    public AbstractContainerFieldVisitorImpl(
            final IVisitor<Class<?>> propertyClassVisitor)
    {
        this.visitors = new ArrayList<>();
        this.visitors.addAll(Arrays.asList(SimpleTypeVisitorImpl
                .getPreparedSimpleTypeVisitors()));
        this.visitors.add(propertyClassVisitor);
        this.visitors.add(new FailFastVisitorImpl<Class<?>>());
    }

    @Override
    public final Boolean canVisit(final Field visitee)
    {
        this.setItemType(null);
        return super.canVisit(visitee);
    }

    protected final Boolean canVisitItemType()
    {
        Boolean result = Boolean.FALSE;

        if (this.itemVisitor != null && this.itemVisitor.canVisit(
                this.itemType))
        {
            result = Boolean.TRUE;
        }
        else
        {
            this.itemVisitor = null;
            for (final IVisitor<Class<?>> visitor : this.visitors)
            {
                if (visitor.canVisit(this.itemType))
                {
                    this.itemVisitor = visitor;
                    result = Boolean.TRUE;
                    break;
                }
            }
        }

        return result;
    }

    public final Class<?> getItemType()
    {
        return itemType;
    }

    public final IVisitor<Class<?>> getItemVisitor()
    {
        return itemVisitor;
    }

    public final void setItemType(final Class<?> itemType)
    {
        this.itemType = itemType;
    }

    public final void setItemVisitor(final IVisitor<Class<?>> itemVisitor)
    {
        this.itemVisitor = itemVisitor;
    }
}
