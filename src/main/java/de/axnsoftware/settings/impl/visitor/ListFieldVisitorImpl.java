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

import de.axnsoftware.settings.impl.IVisitor;
import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IContainerPropertyAccessor;
import de.axnsoftware.settings.impl.IContainerItemAccessor;
import de.axnsoftware.settings.impl.accessor.BranchListItemAccessorImpl;
import de.axnsoftware.settings.impl.accessor.LeafListItemAccessorImpl;
import de.axnsoftware.settings.impl.accessor.ListPropertyAccessorImpl;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class ListFieldVisitorImpl extends AbstractFieldVisitorImpl {

    private final List<IVisitor> visitors;
    private Class<?> itemType;
    private IVisitor<Class<?>> itemVisitor;

    public ListFieldVisitorImpl(final IVisitor propertyClassVisitor) {
        this.visitors = new ArrayList<>();
        this.visitors.add(propertyClassVisitor);
        this.visitors.addAll(Arrays.asList(SimpleTypeVisitorImpl.getPreparedSimpleTypeVisitors()));
        this.visitors.add(new FailFastVisitorImpl<Class<?>>());
    }

    @Override
    protected Boolean canVisitImpl(final Field visitee) {
        Boolean result = Boolean.FALSE;
        final Class<?> type = visitee.getType();
        this.itemType = null;
        if (List.class.isAssignableFrom(type)) {
            ParameterizedType parameterizedType = (ParameterizedType) visitee.getGenericType();
            this.itemType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            if (this.itemVisitor != null && this.itemVisitor.canVisit(this.itemType)) {
                result = Boolean.TRUE;
            } else {
                this.itemVisitor = null;
                for (final IVisitor visitor : this.visitors) {
                    if (visitor.canVisit(this.itemType)) {
                        this.itemVisitor = visitor;
                        result = Boolean.TRUE;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void visit(final Field visitee, final IAccessor parentAccessor) {
        IContainerPropertyAccessor accessor = new ListPropertyAccessorImpl();
        this.configureAccessor(accessor, parentAccessor, visitee);
        IContainerItemAccessor itemAccessorTemplate;
        if (this.itemVisitor instanceof SimpleTypeVisitorImpl) {
            itemAccessorTemplate = new LeafListItemAccessorImpl();
        } else {
            itemAccessorTemplate = new BranchListItemAccessorImpl();
        }
        itemAccessorTemplate.setParentAccessor(accessor);
        itemAccessorTemplate.setType(this.itemType);
        accessor.setItemAccessorTemplate(itemAccessorTemplate);
        this.itemVisitor.visit(this.itemType, itemAccessorTemplate);
    }
}
