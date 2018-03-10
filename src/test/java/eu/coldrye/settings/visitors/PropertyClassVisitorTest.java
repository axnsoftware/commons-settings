/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.visitors;

import eu.coldrye.settings.fixtures.CustomTypeSettingsRoot;
import eu.coldrye.settings.fixtures.FieldVisitor;
import eu.coldrye.settings.fixtures.SettingsRootWithNonDefaultConstructor;
import eu.coldrye.settings.fixtures.SettingsRootWithPrivateDefaultConstructor;
import eu.coldrye.settings.fixtures.SettingsRootWithSingleProperty;
import eu.coldrye.settings.fixtures.SettingsRootWithoutAnyFields;
import eu.coldrye.settings.accessors.Accessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 *
 */
public class PropertyClassVisitorTest {

  @Test
  public void canVisitMustReturnFalseForSettingsRootWithNonDefaultConstructor() {

    Visitor<Class<?>> visitor = new PropertyClassVisitor();
    Assertions.assertFalse(visitor.canVisit(SettingsRootWithNonDefaultConstructor.class));
  }

  @Test
  public void canVisitMustReturnFalseForSettingsRootWithPrivateDefaultConstructor() {

    Visitor<Class<?>> visitor = new PropertyClassVisitor();
    Assertions.assertFalse(visitor.canVisit(SettingsRootWithPrivateDefaultConstructor.class));
  }

  @Test
  public void canVisitMustReturnFalseForSettingsRootWithoutAnyFields() {

    Visitor<Class<?>> visitor = new PropertyClassVisitor();
    Assertions.assertFalse(visitor.canVisit(SettingsRootWithoutAnyFields.class));
  }

  @Test
  public void canVisitMustReturnFalseForNonAnnotatedClass() {

    Visitor<Class<?>> visitor = new PropertyClassVisitor();
    Assertions.assertFalse(visitor.canVisit(Object.class));
  }

  @Test
  public void canVisitMustReturnTrueForAnnotatedClassWithDefaultConstructor() {

    Visitor<Class<?>> visitor = new PropertyClassVisitor();
    Assertions.assertTrue(visitor.canVisit(CustomTypeSettingsRoot.class));
  }

  @Test
  public void visitMustNotVisitFieldVisitorWhenUnableToVisit() {

    PropertyClassVisitor visitor = new PropertyClassVisitor();
    Accessor parentAccessorMock = Mockito.mock(Accessor.class);
    List<Visitor<Field>> fieldVisitors = new ArrayList<>();
    Visitor<Field> fieldVisitorMock = Mockito.mock(FieldVisitor.class);
    fieldVisitors.add(fieldVisitorMock);
    visitor.setFieldVisitors(fieldVisitors);
    Mockito.when(fieldVisitorMock.canVisit(Mockito.any(Field.class))).thenReturn(Boolean.FALSE);
    visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
    Mockito.verify(fieldVisitorMock).canVisit(Mockito.any(Field.class));
    Mockito.verifyNoMoreInteractions(fieldVisitorMock);
  }

  @Test
  public void visitMustVisitFieldVisitor() {

    PropertyClassVisitor visitor = new PropertyClassVisitor();
    Accessor parentAccessorMock = Mockito.mock(Accessor.class);
    List<Visitor<Field>> fieldVisitors = new ArrayList<>();
    Visitor<Field> fieldVisitorMock = Mockito.mock(FieldVisitor.class);
    fieldVisitors.add(fieldVisitorMock);
    visitor.setFieldVisitors(fieldVisitors);
    Mockito.when(fieldVisitorMock.canVisit(Mockito.any(Field.class))).thenReturn(Boolean.TRUE);
    visitor.visit(SettingsRootWithSingleProperty.class, parentAccessorMock);
    Mockito.verify(fieldVisitorMock).canVisit(Mockito.any(Field.class));
    Mockito.verify(fieldVisitorMock).visit(Mockito.any(Field.class), Mockito.eq(parentAccessorMock));
  }
}
