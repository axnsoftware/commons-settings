/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.integration.customtype;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ITypeMapper;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class CustomTypeTypeMapperImpl
        implements ITypeMapper
{

    @Override
    public Object copyOf(Object value)
    {
        CustomType original = (CustomType) value;
        CustomType result = new CustomType();
        result.setFloatValue(original.getFloatValue());
        result.setIntValue(original.getIntValue());
        return result;
    }

    @Override
    public Object readFromBackingStore(IBackingStore backingStore, String key,
                                       Class<?> type) throws
            BackingStoreException
    {
        CustomType result = new CustomType();
        result.setIntValue(backingStore.getInteger(key + ".int"));
        result.setFloatValue(backingStore.getFloat(key + ".float"));
        return result;
    }

    @Override
    public Object valueOf(String value,
                          Class<?> type)
    {
        CustomType result = new CustomType();
        String[] components = value.split("|");
        result.setFloatValue(Float.valueOf(components[0]));
        result.setIntValue(Integer.valueOf(components[1]));
        return result;
    }

    @Override
    public void writeToBackingStore(IBackingStore backingStore, String key,
                                    Object value) throws BackingStoreException
    {
        CustomType original = (CustomType) value;
        backingStore.setFloat(key + ".float", original.getFloatValue());
        backingStore.setInteger(key + ".int", original.getIntValue());
    }
}
