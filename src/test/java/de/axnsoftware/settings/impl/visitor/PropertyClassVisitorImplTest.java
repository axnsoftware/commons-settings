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

import de.axnsoftware.settings.fixtures.CustomTypeSettingsRoot;
import de.axnsoftware.settings.fixtures.IFieldVisitor;
import de.axnsoftware.settings.fixtures.SettingsRootWithNonDefaultConstructor;
import de.axnsoftware.settings.fixtures.SettingsRootWithPrivateDefaultConstructor;
import de.axnsoftware.settings.fixtures.SettingsRootWithSingleProperty;
import de.axnsoftware.settings.fixtures.SettingsRootWithoutAnyFields;
import de.axnsoftware.settings.impl.accessor.IAccessor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class PropertyClassVisitorImplTest
{

    @Test
    public void canVisitMustReturnFalseForSettingsRootWithNonDefaultConstructor()
    {
        IVisitor<Class<?>> visitor = new PropertyClassVisitorImpl();
        Assert.assertFalse(visitor.canVisit(
                SettingsRootWithNonDefaultConstructor.class));
    }

    @Test
    public void canVisitMustReturnFalseForSettingsRootWithPrivateDefaultConstructor()
    {
        IVisitor<Class<?>> visitor = new PropertyClassVisitorImpl();
        Assert.assertFalse(visitor.canVisit(
                SettingsRootWithPrivateDefaultConstructor.class));
    }

    @Test
    public void canVisitMustReturnFalseForSettingsRootWithoutAnyFields()
    {
        IVisitor<Class<?>> visitor = new PropertyClassVisitorImpl();
        Assert.assertFalse(visitor.canVisit(
                SettingsRootWithoutAnyFields.class));
    }

    @Test
    public void canVisitMustReturnFalseForNonAnnotatedClass()
    {
        IVisitor<Class<?>> visitor = new PropertyClassVisitorImpl();
        Assert.assertFalse(visitor.canVisit(Object.class));
    }

    @Test
    public void canVisitMustReturnTrueForAnnotatedClassWithDefaultConstructor()
    {
        IVisitor<Class<?>> visitor = new PropertyClassVisitorImpl();
        Assert.assertTrue(visitor.canVisit(CustomTypeSettingsRoot.class));
    }

    @Test
    public void visitMustNotVisitFieldVisitorWhenUnableToVisit()
    {
        PropertyClassVisitorImpl visitor = new PropertyClassVisitorImpl();
        IAccessor parentAccessorMock = Mockito.mock(IAccessor.class);
        List<IVisitor<Field>> fieldVisitors = new ArrayList<>();
        IVisitor<Field> fieldVisitorMock = Mockito.mock(IFieldVisitor.class);
        fieldVisitors.add(fieldVisitorMock);
        visitor.setFieldVisitors(fieldVisitors);
        Mockito.when(fieldVisitorMock.canVisit(Mockito.<Field>any(Field.class)))
                .thenReturn(Boolean.FALSE);
        visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
        Mockito.verify(fieldVisitorMock).canVisit(Mockito
                .<Field>any(Field.class));
        Mockito.verifyNoMoreInteractions(fieldVisitorMock);
    }

    @Test
    public void visitMustVisitFieldVisitor()
    {
        PropertyClassVisitorImpl visitor = new PropertyClassVisitorImpl();
        IAccessor parentAccessorMock = Mockito.mock(IAccessor.class);
        List<IVisitor<Field>> fieldVisitors = new ArrayList<>();
        IVisitor<Field> fieldVisitorMock = Mockito.mock(IFieldVisitor.class);
        fieldVisitors.add(fieldVisitorMock);
        visitor.setFieldVisitors(fieldVisitors);
        Mockito.when(fieldVisitorMock.canVisit(Mockito.<Field>any(Field.class)))
                .thenReturn(Boolean.TRUE);
        visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
        Mockito.verify(fieldVisitorMock).canVisit(Mockito
                .<Field>any(Field.class));
        Mockito.verify(fieldVisitorMock).visit(Mockito
                .<Field>any(Field.class), Mockito.eq(parentAccessorMock));
    }

    @Test
    public void visitMustGetChildAccessorsFromParentAccessor()
    {
        PropertyClassVisitorImpl visitor = new PropertyClassVisitorImpl();
        IAccessor parentAccessorMock = Mockito.mock(IAccessor.class);
        visitor.setFieldVisitors(new ArrayList<IVisitor<Field>>());
        Mockito.when(parentAccessorMock.getChildAccessors()).thenReturn(
                new ArrayList<IAccessor>());
        visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
        Mockito.verify(parentAccessorMock).getChildAccessors();
        Mockito.verifyNoMoreInteractions(parentAccessorMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void visitMustSetChildAccessorsOnParentAccessorIfNull()
    {
        PropertyClassVisitorImpl visitor = new PropertyClassVisitorImpl();
        IAccessor parentAccessorMock = Mockito.mock(IAccessor.class);
        visitor.setFieldVisitors(new ArrayList<IVisitor<Field>>());
        Mockito.when(parentAccessorMock.getChildAccessors()).thenReturn(null);
        visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
        Mockito.verify(parentAccessorMock).getChildAccessors();
        Mockito.verify(parentAccessorMock).setChildAccessors(Mockito.anyList());
    }
}
