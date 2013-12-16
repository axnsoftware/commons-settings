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
