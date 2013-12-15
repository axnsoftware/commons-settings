/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.PropertyClass;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class MapPropertyAccessorImplTest
{

    private IAccessor simpleSettingsRootAccessor;
    private IAccessor compoundSettingsRootAccessor;
    private IBackingStore properties;

    @PropertyClass
    public static class SimpleSettingsRoot
    {

        @Property
        private Map<String, Integer> values;

        public Map<String, Integer> getValues()
        {
            return values;
        }

        public void setValues(Map<String, Integer> values)
        {
            this.values = values;
        }
    }

    @PropertyClass
    public static class CompoundSettingsRoot
    {

        @Property
        private Map<String, SimpleSettingsRoot> values;

        public Map<String, SimpleSettingsRoot> getValues()
        {
            return values;
        }

        public void setValues(Map<String, SimpleSettingsRoot> values)
        {
            this.values = values;
        }
    }

    @Before
    public void setup()
    {
        this.simpleSettingsRootAccessor = RootAccessorFactory.newInstance()
                .buildRootAccessor(SimpleSettingsRoot.class);
        this.compoundSettingsRootAccessor = RootAccessorFactory.newInstance()
                .buildRootAccessor(CompoundSettingsRoot.class);
        this.properties = new DummyBackingStore();
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot()
    {
        SimpleSettingsRoot source = new SimpleSettingsRoot();
        SimpleSettingsRoot target = new SimpleSettingsRoot();
        source.setValues(new HashMap<String, Integer>());
        source.getValues().put("0", 1);
        source.getValues().put("1", 1);
        source.getValues().put("2", 1);
        this.simpleSettingsRootAccessor.copyValue(source, target);
        Assert.assertEquals(source.getValues(), target.getValues());
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot()
    {
        CompoundSettingsRoot source = new CompoundSettingsRoot();
        CompoundSettingsRoot target = new CompoundSettingsRoot();
        SimpleSettingsRoot v1 = new SimpleSettingsRoot();
        v1.setValues(new HashMap<String, Integer>());
        v1.getValues().put("0", 1);
        v1.getValues().put("1", 2);
        v1.getValues().put("2", 3);
        source.setValues(new HashMap<String, SimpleSettingsRoot>());
        source.getValues().put("0", v1);
        this.compoundSettingsRootAccessor.copyValue(source, target);
        Assert
                .assertEquals(source.getValues().size(), target.getValues()
                .size());
        Assert.assertEquals(source.getValues().get("0").getValues(), target
                .getValues().get("0").getValues());
    }

    @Test
    public void readFromPropertiesMustPopulateSimpleSettingsRootAsExpected()
            throws Exception
    {
        SimpleSettingsRoot settingsRoot = new SimpleSettingsRoot();
        properties.setString("values.0", "1");
        properties.setString("values.1", "2");
        properties.setString("values.2", "3");
        this.simpleSettingsRootAccessor.readFromBackingStore(properties,
                                                             settingsRoot);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("0", 1);
        expected.put("1", 2);
        expected.put("2", 3);
        Assert.assertEquals(expected, settingsRoot.getValues());
    }

    @Test
    public void readFromPropertiesMustPopulateCompoundSettingsRootAsExpected()
            throws Exception
    {
        CompoundSettingsRoot settingsRoot = new CompoundSettingsRoot();
        properties.setString("values.0.values.0", "1");
        properties.setString("values.0.values.1", "2");
        properties.setString("values.0.values.2", "3");
        this.compoundSettingsRootAccessor.readFromBackingStore(properties,
                                                               settingsRoot);
        Assert.assertEquals(1, settingsRoot.getValues().size());
        Map<String, Integer> expected = new HashMap<>();
        expected.put("0", 1);
        expected.put("1", 2);
        expected.put("2", 3);
        Assert.assertEquals(expected, settingsRoot.getValues().get("0")
                .getValues());
    }

    @Test
    public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected()
            throws Exception
    {
        SimpleSettingsRoot settingsRoot = new SimpleSettingsRoot();
        settingsRoot.setValues(new HashMap<String, Integer>());
        settingsRoot.getValues().put("0", 1);
        settingsRoot.getValues().put("1", 2);
        settingsRoot.getValues().put("2", 3);
        this.simpleSettingsRootAccessor.writeToBackingStore(properties,
                                                            settingsRoot);
        Object[] sortedKeys = properties.keySet().toArray();
        Arrays.sort(sortedKeys);
        Assert.assertArrayEquals(new String[]
        {
            "values.0", "values.1", "values.2"
        }, sortedKeys);
        Assert.assertEquals("1", properties.getString("values.0"));
        Assert.assertEquals("2", properties.getString("values.1"));
        Assert.assertEquals("3", properties.getString("values.2"));
    }

    @Test
    public void writeToPropertiesMustPopulatePropertiesFromCompoundSettingsRootAsExpected()
            throws Exception
    {
        CompoundSettingsRoot settingsRoot = new CompoundSettingsRoot();
        SimpleSettingsRoot v1 = new SimpleSettingsRoot();
        v1.setValues(new HashMap<String, Integer>());
        v1.getValues().put("0", 1);
        v1.getValues().put("1", 2);
        v1.getValues().put("2", 3);
        settingsRoot.setValues(new HashMap<String, SimpleSettingsRoot>());
        settingsRoot.getValues().put("0", v1);
        this.compoundSettingsRootAccessor.writeToBackingStore(properties,
                                                              settingsRoot);
        Object[] sortedKeys = properties.keySet().toArray();
        Arrays.sort(sortedKeys);
        Assert.assertArrayEquals(new String[]
        {
            "values.0.values.0", "values.0.values.1", "values.0.values.2"
        }, sortedKeys);
        Assert.assertEquals("1", properties.getString("values.0.values.0"));
        Assert.assertEquals("2", properties.getString("values.0.values.1"));
        Assert.assertEquals("3", properties.getString("values.0.values.2"));
    }
}
