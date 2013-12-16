/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.util;

import java.util.Collections;
import java.util.Enumeration;
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
