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
package de.axnsoftware.settings.util;

import de.axnsoftware.settings.ITypeMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class DefaultValueHolderTest
{

    @Test(expected = IllegalArgumentException.class)
    public void constructorMustFailOnNullType()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        new DefaultValueHolder("", null, typeMapperMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorMustFailOnNullTypeMapper()
    {
        new DefaultValueHolder("", Object.class, null);
    }

    @Test
    public void constructorMustNotFailOnNullDefaultValue()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        new DefaultValueHolder(null, Object.class, typeMapperMock);
    }

    @Test
    public void valueOfMustCacheNullResultFromTypeMapperOnceOnly()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        Mockito.when(typeMapperMock.valueOf(null, String.class))
                .thenReturn(null);
        DefaultValueHolder holder = new DefaultValueHolder(
                null, String.class, typeMapperMock);
        holder.getValue();
        holder.getValue();
        Mockito.verify(typeMapperMock).valueOf(null, String.class);
    }

    @Test
    public void valueOfMustCacheNonNullResultFromTypeMapperOnceOnly()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        Mockito.when(typeMapperMock.valueOf("defaultValue", String.class))
                .thenReturn("defaultValue");
        DefaultValueHolder holder = new DefaultValueHolder(
                "defaultValue", String.class, typeMapperMock);
        holder.getValue();
        holder.getValue();
        Mockito.verify(typeMapperMock).valueOf("defaultValue", String.class);
    }

    @Test
    public void valueOfMustReturnNonNullDefaultValue()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        Mockito.when(typeMapperMock.valueOf("defaultValue", String.class))
                .thenReturn("defaultValue");
        DefaultValueHolder holder = new DefaultValueHolder(
                "defaultValue", String.class, typeMapperMock);
        Assert.assertEquals("defaultValue", holder.getValue());
    }

    @Test
    public void valueOfMustReturnNullDefaultValue()
    {
        ITypeMapper typeMapperMock = Mockito.mock(ITypeMapper.class);
        Mockito.when(typeMapperMock.valueOf(null, String.class))
                .thenReturn(null);
        DefaultValueHolder holder = new DefaultValueHolder(
                null, String.class, typeMapperMock);
        Assert.assertNull(holder.getValue());
    }
}
