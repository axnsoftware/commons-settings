package eu.coldrye.settings.mappers;

import eu.coldrye.settings.TypeMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TypeMapperRegistry {

  private static Map<Class<?>, TypeMapper> preparedDefaultTypeMappings;

  /**
   * Prepares the default type mappings to be used when building the root
   * accessor.
   *
   * @return the prepared default type mappings
   */
  public static Map<Class<?>, TypeMapper> getPreparedDefaultTypeMappings() {

    if (Objects.isNull(preparedDefaultTypeMappings)) {
      synchronized (TypeMapperRegistry.class) {
        if (Objects.isNull(preparedDefaultTypeMappings)) {
          Map<Class<?>, TypeMapper> defaultTypeMappings = new HashMap<>();
          defaultTypeMappings.put(BigDecimal.class, new BigDecimalTypeMapperImpl());
          defaultTypeMappings.put(BigInteger.class, new BigIntegerTypeMapperImpl());
          defaultTypeMappings.put(Boolean.class, new BooleanTypeMapperImpl());
          defaultTypeMappings.put(Byte.class, new ByteTypeMapperImpl());
          defaultTypeMappings.put(Character.class, new CharacterTypeMapperImpl());
          defaultTypeMappings.put(Double.class, new DoubleTypeMapperImpl());
          defaultTypeMappings.put(Enum.class, new EnumTypeMapperImpl());
          defaultTypeMappings.put(Float.class, new FloatTypeMapperImpl());
          defaultTypeMappings.put(Integer.class, new IntegerTypeMapperImpl());
          defaultTypeMappings.put(Long.class, new LongTypeMapperImpl());
          defaultTypeMappings.put(Short.class, new ShortTypeMapperImpl());
          defaultTypeMappings.put(UUID.class, new UuidTypeMapperImpl());
          defaultTypeMappings.put(String.class, new StringTypeMapperImpl());
          preparedDefaultTypeMappings = Collections.unmodifiableMap(defaultTypeMappings);
        }
      }
    }
    return preparedDefaultTypeMappings;
  }
}
