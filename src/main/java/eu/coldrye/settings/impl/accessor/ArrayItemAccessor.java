package eu.coldrye.settings.impl.accessor;

public interface ArrayItemAccessor extends ContainerItemAccessor<Integer> {

  @Override
  default Object getValue(Object settingsRoot) {

    Object[] container = (Object[]) getParentAccessor().getValue(settingsRoot);
    return container[getItemKey()];
  }

  @Override
  default void setValue(Object value, Object settingsRoot) {

    Object[] container = (Object[]) getParentAccessor().getValue(settingsRoot);
    container[getItemKey()] = value;
  }
}
