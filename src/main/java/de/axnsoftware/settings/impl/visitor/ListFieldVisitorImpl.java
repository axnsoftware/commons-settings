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

import de.axnsoftware.settings.impl.accessor.IAccessor;
import de.axnsoftware.settings.impl.accessor.IContainerPropertyAccessor;
import de.axnsoftware.settings.impl.accessor.IContainerItemAccessor;
import de.axnsoftware.settings.impl.accessor.BranchListItemAccessorImpl;
import de.axnsoftware.settings.impl.accessor.LeafListItemAccessorImpl;
import de.axnsoftware.settings.impl.accessor.ListPropertyAccessorImpl;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * The final class ListFieldVisitorImpl models a concrete implementation of the
 * {@code IVisitor} interface that is responsible for visiting fields of type
 * {@code List}.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class ListFieldVisitorImpl
        extends AbstractContainerFieldVisitorImpl
{

    public ListFieldVisitorImpl(final IVisitor<Class<?>> propertyClassVisitor)
    {
        super(propertyClassVisitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean canVisitImpl(final Field visitee)
    {
        Boolean result = Boolean.FALSE;
        final Class<?> type = visitee.getType();
        if (List.class.isAssignableFrom(type))
        {
            ParameterizedType parameterizedType = (ParameterizedType) visitee
                    .getGenericType();
            this.setItemType((Class<?>) parameterizedType
                    .getActualTypeArguments()[0]);
            result = super.canVisitItemType();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Field visitee, final IAccessor parentAccessor)
    {
        IVisitor<Class<?>> itemVisitor = this.getItemVisitor();
        Class<?> itemType = this.getItemType();
        IContainerPropertyAccessor accessor = new ListPropertyAccessorImpl();
        VisitorUtils.configureAccessor(accessor, parentAccessor, visitee);
        IContainerItemAccessor itemAccessorTemplate;
        if (itemVisitor instanceof SimpleTypeVisitorImpl)
        {
            itemAccessorTemplate = new LeafListItemAccessorImpl();
        }
        else
        {
            itemAccessorTemplate = new BranchListItemAccessorImpl();
        }
        itemAccessorTemplate.setParentAccessor(accessor);
        itemAccessorTemplate.setType(itemType);
        accessor.setItemAccessorTemplate(itemAccessorTemplate);
        itemVisitor.visit(itemType, itemAccessorTemplate);
    }
}
