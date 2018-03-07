///*
// * Copyright 2018 coldrye.eu, Carsten Klein
// * Copyright 2013 axn software UG
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package eu.coldrye.settings.util;
//
//import eu.coldrye.settings.TypeMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
///**
// *
// *
// */
//public class DefaultValueHolderTest {
//
//  @Test
//  public void constructorMustFailOnNullType() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new DefaultValueHolder("", null, typeMapperMock);
//    });
//  }
//
//  @Test
//  public void constructorMustFailOnNullTypeMapper() {
//
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new DefaultValueHolder("", Object.class, null);
//    });
//  }
//
//  @Test
//  public void constructorMustNotFailOnNullDefaultValue() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    new DefaultValueHolder(null, Object.class, typeMapperMock);
//  }
//
//  @Test
//  public void valueOfMustCacheNullResultFromTypeMapperOnceOnly() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    Mockito.when(typeMapperMock.valueOf(null, String.class)).thenReturn(null);
//    DefaultValueHolder holder = new DefaultValueHolder(null, String.class, typeMapperMock);
//    holder.getValue();
//    holder.getValue();
//    Mockito.verify(typeMapperMock).valueOf(null, String.class);
//  }
//
//  @Test
//  public void valueOfMustCacheNonNullResultFromTypeMapperOnceOnly() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    Mockito.when(typeMapperMock.valueOf("defaultValue", String.class)).thenReturn("defaultValue");
//    DefaultValueHolder holder = new DefaultValueHolder("defaultValue", String.class, typeMapperMock);
//    holder.getValue();
//    holder.getValue();
//    Mockito.verify(typeMapperMock).valueOf("defaultValue", String.class);
//  }
//
//  @Test
//  public void valueOfMustReturnNonNullDefaultValue() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    Mockito.when(typeMapperMock.valueOf("defaultValue", String.class)).thenReturn("defaultValue");
//    DefaultValueHolder holder = new DefaultValueHolder("defaultValue", String.class, typeMapperMock);
//    Assertions.assertEquals("defaultValue", holder.getValue());
//  }
//
//  @Test
//  public void valueOfMustReturnNullDefaultValue() {
//
//    TypeMapper typeMapperMock = Mockito.mock(TypeMapper.class);
//    Mockito.when(typeMapperMock.valueOf(null, String.class)).thenReturn(null);
//    DefaultValueHolder holder = new DefaultValueHolder(null, String.class, typeMapperMock);
//    Assertions.assertNull(holder.getValue());
//  }
//}
