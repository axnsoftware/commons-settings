/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ITypeMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class DefaultTypeMapperImplTest
{

    private static enum TestEnum
    {

        ABC;
    }

    @Test
    public void copyOfMustReturnSameInstance()
    {
        Object value = new Object();
        Assert.assertEquals(value, new DefaultTypeMapperImpl().copyOf(value));
    }

    @Test
    public void copyOfMustNotFailOnNullValue()
    {
        Assert.assertNull(new DefaultTypeMapperImpl().copyOf(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFromBackingStoreMustFailOnNullBackingStore() throws
            Exception
    {
        new DefaultTypeMapperImpl().readFromBackingStore(null, "notNull",
                                                         Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFromBackingStoreMustFailOnNullKey() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
                                                         "notNull", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFromBackingStoreMustFailOnNullType() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
                                                         null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFromBackingStoreMustFailOnUnsupportedType() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
                                                         "notNull",
                                                         Object.class);
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetStringForStringType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getString("key")).thenReturn("value");
        Assert.assertEquals("value", new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", String.class));
        Mockito.verify(backingStoreMock).getString("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetStringForBigDecimalType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getString("key")).thenReturn("1234");
        Assert.assertEquals(new BigDecimal("1234"), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", BigDecimal.class));
        Mockito.verify(backingStoreMock).getString("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetStringForBigIntegerType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getString("key")).thenReturn("1234");
        Assert.assertEquals(new BigInteger("1234"), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", BigInteger.class));
        Mockito.verify(backingStoreMock).getString("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetStringForEnumType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getString("key")).thenReturn("ABC");
        Assert.assertEquals(TestEnum.ABC, new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", TestEnum.class));
        Mockito.verify(backingStoreMock).getString("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetStringForUUIDType()
            throws Exception
    {
        UUID uuid = UUID.randomUUID();
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getString("key")).thenReturn(uuid
                .toString());
        Assert.assertEquals(uuid, new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", UUID.class));
        Mockito.verify(backingStoreMock).getString("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetBooleanForBooleanType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getBoolean("key")).thenReturn(
                Boolean.FALSE);
        Assert.assertEquals(Boolean.FALSE, new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Boolean.class));
        Mockito.verify(backingStoreMock).getBoolean("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetByteForByteType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getByte("key")).thenReturn(Byte.valueOf(
                (byte) 0));
        Assert.assertEquals(Byte.valueOf((byte) 0), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Byte.class));
        Mockito.verify(backingStoreMock).getByte("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetCharacterForCharacterType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getCharacter("key")).thenReturn(Character
                .valueOf('a'));
        Assert.assertEquals(Character.valueOf('a'), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Character.class));
        Mockito.verify(backingStoreMock).getCharacter("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetDoubleForDoubleType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getDouble("key")).thenReturn(Double
                .valueOf(0));
        Assert.assertEquals(Double.valueOf(0), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Double.class));
        Mockito.verify(backingStoreMock).getDouble("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetFloatForFloatType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getFloat("key")).thenReturn(Float
                .valueOf(0));
        Assert.assertEquals(Float.valueOf(0), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Float.class));
        Mockito.verify(backingStoreMock).getFloat("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetIntegerForIntegerType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getInteger("key")).thenReturn(Integer
                .valueOf(0));
        Assert.assertEquals(Integer.valueOf(0), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Integer.class));
        Mockito.verify(backingStoreMock).getInteger("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetLongForLongType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getLong("key")).thenReturn(Long
                .valueOf(0));
        Assert.assertEquals(Long.valueOf(0), new DefaultTypeMapperImpl()
                .readFromBackingStore(backingStoreMock, "key", Long.class));
        Mockito.verify(backingStoreMock).getLong("key");
    }

    @Test
    public void readFromBackingStoreMustCallBackingStoreGetShortForShortType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        Mockito.when(backingStoreMock.getShort("key")).thenReturn(Short
                .valueOf((short) 0));
        Assert.assertEquals(Short.valueOf((short) 0),
                            new DefaultTypeMapperImpl().readFromBackingStore(
                backingStoreMock, "key", Short.class));
        Mockito
                .verify(backingStoreMock).getShort("key");
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfMustFailOnNullType()
    {
        new DefaultTypeMapperImpl().valueOf("notNull", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfMustFailOnUnsupportedType()
    {
        new DefaultTypeMapperImpl().valueOf("notNull", Object.class);
    }

    @Test
    public void valueOfMustNotFailOnNullValue()
    {
        Assert.assertNull(new DefaultTypeMapperImpl().valueOf(null,
                                                              String.class));
    }

    @Test
    public void valueOfMustReturnBigDecimalForBigDecimalType()
    {
        BigDecimal value = new BigDecimal("1234");
        Assert.assertEquals(value, new DefaultTypeMapperImpl().valueOf(value
                .toString(), BigDecimal.class));
    }

    @Test
    public void valueOfMustReturnBigIntegerForBigIntegerType()
    {
        BigInteger value = new BigInteger("1234");
        Assert.assertEquals(value, new DefaultTypeMapperImpl().valueOf(value
                .toString(), BigInteger.class));
    }

    @Test
    public void valueOfMustReturnBooleanForBooleanType()
    {
        Assert.assertEquals(Boolean.FALSE, new DefaultTypeMapperImpl()
                .valueOf(Boolean.FALSE.toString(), Boolean.class));
    }

    @Test
    public void valueOfMustReturnByteForByteType()
    {
        Assert.assertEquals(Byte.valueOf((byte) 0), new DefaultTypeMapperImpl()
                .valueOf(Byte.valueOf((byte) 0).toString(), Byte.class));
    }

    @Test
    public void valueOfMustReturnCharacterForCharacterType()
    {
        Assert.assertEquals(Character.valueOf('a'), new DefaultTypeMapperImpl()
                .valueOf(Character.valueOf('a').toString(), Character.class));
    }

    @Test
    public void valueOfMustReturnDoubleForDoubleType()
    {
        Assert.assertEquals(Double.valueOf(0), new DefaultTypeMapperImpl()
                .valueOf(Double.valueOf(0).toString(), Double.class));
    }

    @Test
    public void valueOfMustReturnFloatForFloatType()
    {
        Assert.assertEquals(Float.valueOf(0), new DefaultTypeMapperImpl()
                .valueOf(Float.valueOf(0).toString(), Float.class));
    }

    @Test
    public void valueOfMustReturnIntegerForIntegerType()
    {
        Assert.assertEquals(Integer.valueOf(0), new DefaultTypeMapperImpl()
                .valueOf(Integer.valueOf(0).toString(), Integer.class));
    }

    @Test
    public void valueOfMustReturnLongForLongType()
    {
        Assert.assertEquals(Long.valueOf(0), new DefaultTypeMapperImpl()
                .valueOf(Long.valueOf(0).toString(), Long.class));
    }

    @Test
    public void valueOfMustReturnShortForShortType()
    {
        Assert.assertEquals(Short.valueOf((short) 0),
                            new DefaultTypeMapperImpl().valueOf(Short.valueOf(
                (short) 0).toString(), Short.class));
    }

    @Test
    public void valueOfMustReturnStringForStringType()
    {
        Assert.assertEquals("value", new DefaultTypeMapperImpl()
                .valueOf("value", String.class));
    }

    @Test
    public void valueOfMustReturnEnumConstantForEnumType()
    {
        Assert.assertEquals(TestEnum.ABC, new DefaultTypeMapperImpl().valueOf(
                TestEnum.ABC.toString(), TestEnum.class));
    }

    @Test
    public void valueOfMustReturnUUIDForUUIDType()
    {
        UUID uuid = UUID.randomUUID();
        Assert.assertEquals(uuid, new DefaultTypeMapperImpl().valueOf(uuid
                .toString(), UUID.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeToBackingStoreMustFailOnNullBackingStore() throws
            Exception
    {
        new DefaultTypeMapperImpl().writeToBackingStore(null, "notNull",
                                                        new Integer(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeToBackingStoreMustFailOnNullKey() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock,
                                                        null, new Integer(0));
    }

    @Test
    public void writeToBackingStoreMustNotFailOnNullValue() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock,
                                                        "notNull", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeToBackingStoreMustFailOnUnsupportedType() throws
            Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock,
                                                        "notNull",
                                                        new Object());
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetStringForStringType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        "value");
        Mockito.verify(backingStoreMock).setString("key", "value");
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetStringForBigDecimalType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        new BigDecimal("1234"));
        Mockito.verify(backingStoreMock).setString("key", new BigDecimal("1234")
                .toString());
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetStringForBigIntegerType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        new BigInteger("1234"));
        Mockito.verify(backingStoreMock).setString("key", new BigInteger("1234")
                .toString());
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetStringForEnumType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        TestEnum.ABC);
        Mockito.verify(backingStoreMock).setString("key", TestEnum.ABC
                .toString());
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetStringForUUIDType()
            throws Exception
    {
        UUID uuid = UUID.randomUUID();
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        uuid);
        Mockito.verify(backingStoreMock).setString("key", uuid.toString());
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetBooleanForBooleanType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Boolean.FALSE);
        Mockito.verify(backingStoreMock).setBoolean("key", Boolean.FALSE);
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetByteForByteType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Byte.valueOf((byte) 0));
        Mockito.verify(backingStoreMock).setByte("key", Byte.valueOf((byte) 0));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetCharacterForCharacterType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Character.valueOf(
                'a'));
        Mockito.verify(backingStoreMock).setCharacter("key", Character.valueOf(
                'a'));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetDoubleForDoubleType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Double.valueOf(0));
        Mockito.verify(backingStoreMock).setDouble("key", Double.valueOf(0));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetFloatForFloatType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Float.valueOf(0));
        Mockito.verify(backingStoreMock).setFloat("key", Float.valueOf(0));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetIntegerForIntegerType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Integer.valueOf(0));
        Mockito.verify(backingStoreMock).setInteger("key", Integer.valueOf(0));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetLongForLongType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Long.valueOf(0));
        Mockito.verify(backingStoreMock).setLong("key", Long.valueOf(0));
    }

    @Test
    public void writeToBackingStoreMustCallBackingStoreSetShortForShortType()
            throws Exception
    {
        IBackingStore backingStoreMock = Mockito.mock(IBackingStore.class);
        new DefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key",
                                                        Short.valueOf((short) 0));
        Mockito.verify(backingStoreMock).setShort("key", Short
                .valueOf((short) 0));
    }

    @Test
    public void preparedDefaultTypeMappingsMustBeProperlyConfigured()
    {
        Map<Class<?>, ITypeMapper> mappings = DefaultTypeMapperImpl
                .getPreparedDefaultTypeMappings();

        Class<?>[] expected = new Class[]
        {
            BigDecimal.class,
            BigInteger.class,
            Boolean.class,
            Byte.class,
            Character.class,
            Double.class,
            Enum.class,
            Float.class,
            Integer.class,
            Long.class,
            Short.class,
            String.class,
            UUID.class,
        };
        Comparator<Class<?>> comparator = new Comparator<Class<?>>()
        {
            @Override
            public int compare(
                    Class<?> o1,
                    Class<?> o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Class<?>[] actual = mappings.keySet().toArray(new Class<?>[]
        {
        });
        Arrays.sort(expected, comparator);
        Arrays.sort(actual, comparator);
        Assert.assertArrayEquals(expected, actual);
    }
}
