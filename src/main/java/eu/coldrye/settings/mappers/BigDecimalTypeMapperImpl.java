package eu.coldrye.settings.mappers;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.TypeMapper;

import java.math.BigDecimal;
import java.util.prefs.BackingStoreException;

public class BigDecimalTypeMapperImpl implements TypeMapper {

  @Override
  public Object copyOf(Object value) {

    return value;
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type) throws BackingStoreException {

    return valueOf(backingStore.getString(key), type);
  }

  @Override
  public Object valueOf(String value, Class<?> type) {

    return new BigDecimal(value);
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    backingStore.setString(key, value.toString());
  }
}
