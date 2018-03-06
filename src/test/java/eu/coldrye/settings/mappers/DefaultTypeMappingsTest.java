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
//package eu.coldrye.settings.impl.accessor;
//
//import eu.coldrye.settings.BackingStore;
//import eu.coldrye.settings.TypeMapper;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.mockito.Mockito;
//
///**
// *
// *
// */
//public class DefaultTypeMappingsTest {
//
//  private static enum TestEnum {
//
//    ABC;
//  }
//
//  @ParameterizedTest
//  @ArgumentsSource(TypeMapperAndValueArguments.class)
//  public void copyOfMustReturnSameInstance(TypeMapper mapper, Object value) {
//
//    Assertions.assertEquals(value, mapper.copyOf(value));
//  }
//
//  @Test
//  public void copyOfMustNotFailOnNullValue() {
//
//    Assertions.assertNull(new AbstractDefaultTypeMapperImpl().copyOf(null));
//  }
//
//  @Test
//  public void readFromBackingStoreMustFailOnNullBackingStore() {
//
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().readFromBackingStore(null, "notNull", Object.class);
//    });
//  }
//
//  @Test
//  public void readFromBackingStoreMustFailOnNullKey() {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, "notNull", null);
//    });
//  }
//
//  @Test
//  public void readFromBackingStoreMustFailOnNullType() {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, null, Object.class);
//    });
//  }
//
//  @Test
//  public void readFromBackingStoreMustFailOnUnsupportedType() {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, "notNull", Object.class);
//    });
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetStringForStringType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getString("key")).thenReturn("value");
//    Assertions.assertEquals("value", new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", String.class));
//    Mockito.verify(backingStoreMock).getString("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetStringForBigDecimalType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getString("key")).thenReturn("1234");
//    Assertions.assertEquals(new BigDecimal("1234"), new AbstractDefaultTypeMapperImpl().readFromBackingStore(
//      backingStoreMock, "key", BigDecimal.class));
//    Mockito.verify(backingStoreMock).getString("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetStringForBigIntegerType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getString("key")).thenReturn("1234");
//    Assertions.assertEquals(new BigInteger("1234"), new AbstractDefaultTypeMapperImpl().readFromBackingStore(
//      backingStoreMock, "key", BigInteger.class));
//    Mockito.verify(backingStoreMock).getString("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetStringForEnumType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getString("key")).thenReturn("ABC");
//    Assertions.assertEquals(TestEnum.ABC, new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, "key",
//      TestEnum.class));
//    Mockito.verify(backingStoreMock).getString("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetStringForUUIDType() throws Exception {
//
//    UUID uuid = UUID.randomUUID();
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getString("key")).thenReturn(uuid.toString());
//    Assertions.assertEquals(uuid, new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, "key",
//      UUID.class));
//    Mockito.verify(backingStoreMock).getString("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetBooleanForBooleanType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getBoolean("key")).thenReturn(Boolean.FALSE);
//    Assertions.assertEquals(Boolean.FALSE, new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock, "key",
//      Boolean.class));
//    Mockito.verify(backingStoreMock).getBoolean("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetByteForByteType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getByte("key")).thenReturn(Byte.valueOf((byte) 0));
//    Assertions.assertEquals(Byte.valueOf((byte) 0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Byte.class));
//    Mockito.verify(backingStoreMock).getByte("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetCharacterForCharacterType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getCharacter("key")).thenReturn(Character.valueOf('a'));
//    Assertions.assertEquals(Character.valueOf('a'), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Character.class));
//    Mockito.verify(backingStoreMock).getCharacter("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetDoubleForDoubleType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getDouble("key")).thenReturn(Double.valueOf(0));
//    Assertions.assertEquals(Double.valueOf(0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Double.class));
//    Mockito.verify(backingStoreMock).getDouble("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetFloatForFloatType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getFloat("key")).thenReturn(Float.valueOf(0));
//    Assertions.assertEquals(Float.valueOf(0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Float.class));
//    Mockito.verify(backingStoreMock).getFloat("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetIntegerForIntegerType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getInteger("key")).thenReturn(Integer.valueOf(0));
//    Assertions.assertEquals(Integer.valueOf(0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Integer.class));
//    Mockito.verify(backingStoreMock).getInteger("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetLongForLongType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getLong("key")).thenReturn(Long.valueOf(0));
//    Assertions.assertEquals(Long.valueOf(0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Long.class));
//    Mockito.verify(backingStoreMock).getLong("key");
//  }
//
//  @Test
//  public void readFromBackingStoreMustCallBackingStoreGetShortForShortType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Mockito.when(backingStoreMock.getShort("key")).thenReturn(Short.valueOf((short) 0));
//    Assertions.assertEquals(Short.valueOf((short) 0), new AbstractDefaultTypeMapperImpl().readFromBackingStore(backingStoreMock,
//      "key", Short.class));
//    Mockito.verify(backingStoreMock).getShort("key");
//  }
//
//  @Test
//  public void valueOfMustFailOnNullType() {
//
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().valueOf("notNull", null);
//    });
//  }
//
//  @Test
//  public void valueOfMustFailOnUnsupportedType() {
//
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().valueOf("notNull", Object.class);
//    });
//  }
//
//  @Test
//  public void valueOfMustNotFailOnNullValue() {
//
//    Assertions.assertNull(new AbstractDefaultTypeMapperImpl().valueOf(null, String.class));
//  }
//
//  @Test
//  public void valueOfMustReturnBigDecimalForBigDecimalType() {
//
//    BigDecimal value = new BigDecimal("1234");
//    Assertions.assertEquals(value, new AbstractDefaultTypeMapperImpl().valueOf(value.toString(), BigDecimal.class));
//  }
//
//  @Test
//  public void valueOfMustReturnBigIntegerForBigIntegerType() {
//
//    BigInteger value = new BigInteger("1234");
//    Assertions.assertEquals(value, new AbstractDefaultTypeMapperImpl().valueOf(value.toString(), BigInteger.class));
//  }
//
//  @Test
//  public void valueOfMustReturnBooleanForBooleanType() {
//
//    Assertions.assertEquals(Boolean.FALSE, new AbstractDefaultTypeMapperImpl().valueOf(Boolean.FALSE.toString(),
//      Boolean.class));
//  }
//
//  @Test
//  public void valueOfMustReturnByteForByteType() {
//
//    Assertions.assertEquals(Byte.valueOf((byte) 0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Byte.valueOf((byte) 0).toString(), Byte.class));
//  }
//
//  @Test
//  public void valueOfMustReturnCharacterForCharacterType() {
//
//    Assertions.assertEquals(Character.valueOf('a'), new AbstractDefaultTypeMapperImpl().valueOf(
//      Character.valueOf('a').toString(), Character.class));
//  }
//
//  @Test
//  public void valueOfMustReturnDoubleForDoubleType() {
//
//    Assertions.assertEquals(Double.valueOf(0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Double.valueOf(0).toString(), Double.class));
//  }
//
//  @Test
//  public void valueOfMustReturnFloatForFloatType() {
//
//    Assertions.assertEquals(Float.valueOf(0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Float.valueOf(0).toString(), Float.class));
//  }
//
//  @Test
//  public void valueOfMustReturnIntegerForIntegerType() {
//
//    Assertions.assertEquals(Integer.valueOf(0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Integer.valueOf(0).toString(), Integer.class));
//  }
//
//  @Test
//  public void valueOfMustReturnLongForLongType() {
//
//    Assertions.assertEquals(Long.valueOf(0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Long.valueOf(0).toString(), Long.class));
//  }
//
//  @Test
//  public void valueOfMustReturnShortForShortType() {
//
//    Assertions.assertEquals(Short.valueOf((short) 0), new AbstractDefaultTypeMapperImpl().valueOf(
//      Short.valueOf((short) 0).toString(), Short.class));
//  }
//
//  @Test
//  public void valueOfMustReturnStringForStringType() {
//
//    Assertions.assertEquals("value", new AbstractDefaultTypeMapperImpl().valueOf("value", String.class));
//  }
//
//  @Test
//  public void valueOfMustReturnEnumConstantForEnumType() {
//
//    Assertions.assertEquals(TestEnum.ABC, new AbstractDefaultTypeMapperImpl().valueOf(TestEnum.ABC.toString(), TestEnum.class));
//  }
//
//  @Test
//  public void valueOfMustReturnUUIDForUUIDType() {
//
//    UUID uuid = UUID.randomUUID();
//    Assertions.assertEquals(uuid, new AbstractDefaultTypeMapperImpl().valueOf(uuid.toString(), UUID.class));
//  }
//
//  @Test
//  public void writeToBackingStoreMustFailOnNullBackingStore() {
//
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().writeToBackingStore(null, "notNull", new Integer(0));
//    });
//  }
//
//  @Test
//  public void writeToBackingStoreMustFailOnNullKey() {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, null, new Integer(0));
//    });
//  }
//
//  @Test
//  public void writeToBackingStoreMustNotFailOnNullValue() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "notNull", null);
//  }
//
//  @Test
//  public void writeToBackingStoreMustFailOnUnsupportedType() {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    Assertions.assertThrows(IllegalArgumentException.class, () -> {
//      new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "notNull", new Object());
//    });
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetStringForStringType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", "value");
//    Mockito.verify(backingStoreMock).setString("key", "value");
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetStringForBigDecimalType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", new BigDecimal("1234"));
//    Mockito.verify(backingStoreMock).setString("key", new BigDecimal("1234").toString());
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetStringForBigIntegerType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", new BigInteger("1234"));
//    Mockito.verify(backingStoreMock).setString("key", new BigInteger("1234").toString());
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetStringForEnumType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", TestEnum.ABC);
//    Mockito.verify(backingStoreMock).setString("key", TestEnum.ABC.toString());
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetStringForUUIDType() throws Exception {
//
//    UUID uuid = UUID.randomUUID();
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", uuid);
//    Mockito.verify(backingStoreMock).setString("key", uuid.toString());
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetBooleanForBooleanType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Boolean.FALSE);
//    Mockito.verify(backingStoreMock).setBoolean("key", Boolean.FALSE);
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetByteForByteType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Byte.valueOf((byte) 0));
//    Mockito.verify(backingStoreMock).setByte("key", Byte.valueOf((byte) 0));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetCharacterForCharacterType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Character.valueOf('a'));
//    Mockito.verify(backingStoreMock).setCharacter("key", Character.valueOf('a'));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetDoubleForDoubleType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Double.valueOf(0));
//    Mockito.verify(backingStoreMock).setDouble("key", Double.valueOf(0));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetFloatForFloatType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Float.valueOf(0));
//    Mockito.verify(backingStoreMock).setFloat("key", Float.valueOf(0));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetIntegerForIntegerType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Integer.valueOf(0));
//    Mockito.verify(backingStoreMock).setInteger("key", Integer.valueOf(0));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetLongForLongType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Long.valueOf(0));
//    Mockito.verify(backingStoreMock).setLong("key", Long.valueOf(0));
//  }
//
//  @Test
//  public void writeToBackingStoreMustCallBackingStoreSetShortForShortType() throws Exception {
//
//    BackingStore backingStoreMock = Mockito.mock(BackingStore.class);
//    new AbstractDefaultTypeMapperImpl().writeToBackingStore(backingStoreMock, "key", Short.valueOf((short) 0));
//    Mockito.verify(backingStoreMock).setShort("key", Short.valueOf((short) 0));
//  }
//
//  @Test
//  public void preparedDefaultTypeMappingsMustBeProperlyConfigured() {
//
//    Map<Class<?>, TypeMapper> mappings = AbstractDefaultTypeMapperImpl.getPreparedDefaultTypeMappings();
//
//    Class<?>[] expected = new Class[]{
//      BigDecimal.class,
//      BigInteger.class,
//      Boolean.class,
//      Byte.class,
//      Character.class,
//      Double.class,
//      Enum.class,
//      Float.class,
//      Integer.class,
//      Long.class,
//      Short.class,
//      String.class,
//      UUID.class,
//    };
//    Comparator<Class<?>> comparator = Comparator.comparing(o -> o.getName());
//    Class<?>[] actual = mappings.keySet().toArray(new Class<?>[]{});
//    Arrays.sort(expected, comparator);
//    Arrays.sort(actual, comparator);
//    Assertions.assertArrayEquals(expected, actual);
//  }
//
//
//  public static class TypeMappers implements ArgumentsProvider {
//
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
//
//      return Stream.of(
//        Arguments.of()
//      );
//    }
//  }
//}
