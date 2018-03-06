package eu.coldrye.settings.mappers;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.TypeMapper;

import java.util.prefs.BackingStoreException;

public class CharacterTypeMapperImpl implements TypeMapper {

  @Override
  public Object copyOf(Object value) {

    return value;
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type) throws BackingStoreException {

    return backingStore.getCharacter(key);
  }

  @Override
  public Object valueOf(String value, Class<?> type) {

    return value.charAt(0);
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    backingStore.setCharacter(key, (Character) value);
  }
}
