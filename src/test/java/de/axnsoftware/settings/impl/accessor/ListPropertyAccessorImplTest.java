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
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.PropertyClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class ListPropertyAccessorImplTest
{

    private IAccessor simpleSettingsRootAccessor;
    private IAccessor compoundSettingsRootAccessor;
    private IBackingStore properties;

    @PropertyClass
    public static class SimpleSettingsRoot
    {

        @Property
        private List<Integer> values;

        public List<Integer> getValues()
        {
            return values;
        }

        public void setValues(List<Integer> values)
        {
            this.values = values;
        }
    }

    @PropertyClass
    public static class CompoundSettingsRoot
    {

        @Property
        private List<SimpleSettingsRoot> values;

        public List<SimpleSettingsRoot> getValues()
        {
            return values;
        }

        public void setValues(List<SimpleSettingsRoot> values)
        {
            this.values = values;
        }
    }

    @Before
    public void setup()
    {
        this.simpleSettingsRootAccessor = RootAccessorBuilder.newInstance()
                .buildRootAccessor(SimpleSettingsRoot.class);
        this.compoundSettingsRootAccessor = RootAccessorBuilder.newInstance()
                .buildRootAccessor(CompoundSettingsRoot.class);
        this.properties = new DummyBackingStore();
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot()
    {
        SimpleSettingsRoot source = new SimpleSettingsRoot();
        SimpleSettingsRoot target = new SimpleSettingsRoot();
        source.setValues(new ArrayList<Integer>());
        source.getValues().add(1);
        source.getValues().add(2);
        source.getValues().add(3);
        this.simpleSettingsRootAccessor.copyValue(source, target);
        Assert.assertEquals(source.getValues(), target.getValues());
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot()
    {
        CompoundSettingsRoot source = new CompoundSettingsRoot();
        CompoundSettingsRoot target = new CompoundSettingsRoot();
        SimpleSettingsRoot v1 = new SimpleSettingsRoot();
        v1.setValues(new ArrayList<Integer>());
        v1.getValues().add(1);
        v1.getValues().add(2);
        v1.getValues().add(3);
        source.setValues(new ArrayList<SimpleSettingsRoot>());
        source.getValues().add(v1);
        this.compoundSettingsRootAccessor.copyValue(source, target);
        Assert
                .assertEquals(source.getValues().size(), target.getValues()
                .size());
        Assert.assertEquals(source.getValues().get(0).getValues(), target
                .getValues().get(0).getValues());
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
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
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
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        Assert.assertEquals(expected, settingsRoot.getValues().get(0)
                .getValues());
    }

    @Test
    public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected()
            throws Exception
    {
        SimpleSettingsRoot settingsRoot = new SimpleSettingsRoot();
        settingsRoot.setValues(new ArrayList<Integer>());
        settingsRoot.getValues().add(1);
        settingsRoot.getValues().add(2);
        settingsRoot.getValues().add(3);
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
        v1.setValues(new ArrayList<Integer>());
        v1.getValues().add(1);
        v1.getValues().add(2);
        v1.getValues().add(3);
        settingsRoot.setValues(new ArrayList<SimpleSettingsRoot>());
        settingsRoot.getValues().add(v1);
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
