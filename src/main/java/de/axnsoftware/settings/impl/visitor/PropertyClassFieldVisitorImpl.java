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
import de.axnsoftware.settings.impl.accessor.IPropertyAccessor;
import de.axnsoftware.settings.impl.accessor.BranchPropertyAccessorImpl;
import java.lang.reflect.Field;

/**
 * The final class PropertyClassFieldVisitorImpl models a concrete
 * implementation of the {@code IVisitor} interface that is responsible for
 * visiting fields of custom type where the classes have been annotated using
 * the {@code PropertyClass} annotation.{@code Array}.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public final class PropertyClassFieldVisitorImpl
        extends AbstractFieldVisitorImpl
{

    private IVisitor propertyClassVisitor;

    public PropertyClassFieldVisitorImpl(final IVisitor propertyClassVisitor)
    {
        this.propertyClassVisitor = propertyClassVisitor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean canVisitImpl(final Field visitee)
    {
        return this.propertyClassVisitor.canVisit(visitee.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Field visitee, final IAccessor parentAccessor)
    {
        final IPropertyAccessor accessor = new BranchPropertyAccessorImpl();
        this.configureAccessor(accessor, parentAccessor, visitee);
        this.propertyClassVisitor.visit(visitee.getType(), accessor);
    }
}
