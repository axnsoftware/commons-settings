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
package de.axnsoftware.settings.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * The class OrderedProperties models a specialization of the {@link Properties}
 * class that writes all configuration data ordered alphanumerically by its key.
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class OrderedProperties
        extends Properties
{

    @Override
    public Set<String> stringPropertyNames()
    {
        Set<String> result = new TreeSet<>();
        for (Object key : this.keySet())
        {
            result.add((String) key);
        }
        return result;
    }

    /**
     * Returns the keys in alphanumerical order.
     *
     * @return the alphanumerically ordered set of keys
     */
    @Override
    public synchronized Set<Object> keySet()
    {
        return new TreeSet<>(super.keySet());
    }

    /**
     * Returns an enumerator for the alphanumerically ordered keys.
     *
     * @return enumerator for the alphanumerically ordered keys
     */
    @Override
    public synchronized Enumeration<Object> keys()
    {
        return Collections.enumeration(this.keySet());
    }
}
