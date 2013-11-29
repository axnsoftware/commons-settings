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

import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IVisitor;

/**
 * TODO: this is the last visitor in a series of visitors causing the process to
 * always fail when visited.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class FailFastVisitorImpl<T> implements IVisitor<T> {

    @Override
    public Boolean canVisit(final T visitee) {
        throw new RuntimeException("Unsupported type for field: " + visitee.toString() + ". Did you forget to annotate the class with the PropertyClass annotation or provide a type mapper?");
    }

    @Override
    public void visit(final T visitee, final IAccessor parentAccessor) {
        throw new RuntimeException("Unsupported type for field: " + visitee.toString() + ". Did you forget to annotate the class with the PropertyClass annotation or provide a type mapper?");
    }
}
