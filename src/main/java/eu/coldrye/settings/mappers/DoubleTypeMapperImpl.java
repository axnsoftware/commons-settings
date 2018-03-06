package eu.coldrye.settings.mappers;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.TypeMapper;

import java.util.prefs.BackingStoreException;

public class DoubleTypeMapperImpl implements TypeMapper {

  @Override
  public Object copyOf(Object value) {

    return value;
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type) throws BackingStoreException {

    return backingStore.getDouble(key);
  }

  @Override
  public Object valueOf(String value, Class<?> type) {

    return Double.valueOf(value);
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    backingStore.setDouble(key, (Double) value);
  }
}
