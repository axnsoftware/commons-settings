/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.impl.accessor;

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
