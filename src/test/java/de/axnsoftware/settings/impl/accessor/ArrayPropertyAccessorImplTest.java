/*
 * (C) 2013 axn software UG
 * TODO:LICENSE All Rights Reserved.
 */
package de.axnsoftware.settings.impl.accessor;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.PropertyClass;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class ArrayPropertyAccessorImplTest {

    private IAccessor simpleSettingsRootAccessor;
    private IAccessor compoundSettingsRootAccessor;
    private IBackingStore properties;

    @PropertyClass
    public static class SimpleSettingsRoot {

        @Property
        private Integer[] values;

        public Integer[] getValues() {
            return values;
        }

        public void setValues(Integer[] values) {
            this.values = values;
        }
    }

    @PropertyClass
    public static class CompoundSettingsRoot {

        @Property
        private SimpleSettingsRoot[] values;

        public SimpleSettingsRoot[] getValues() {
            return values;
        }

        public void setValues(SimpleSettingsRoot[] values) {
            this.values = values;
        }
    }

    @Before
    public void setup() {
        this.simpleSettingsRootAccessor = RootAccessorFactory.newInstance().buildRootAccessor(SimpleSettingsRoot.class);
        this.compoundSettingsRootAccessor = RootAccessorFactory.newInstance().buildRootAccessor(CompoundSettingsRoot.class);
        this.properties = new DummyBackingStoreWrapper();
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot() {
        SimpleSettingsRoot source = new SimpleSettingsRoot();
        SimpleSettingsRoot target = new SimpleSettingsRoot();
        source.setValues(new Integer[]{1, 2, 3});
        this.simpleSettingsRootAccessor.copyValue(source, target);
        Assert.assertArrayEquals(source.getValues(), target.getValues());
    }

    @Test
    public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot() {
        CompoundSettingsRoot source = new CompoundSettingsRoot();
        CompoundSettingsRoot target = new CompoundSettingsRoot();
        SimpleSettingsRoot v1 = new SimpleSettingsRoot();
        v1.setValues(new Integer[]{1, 2, 3});
        source.setValues(new SimpleSettingsRoot[]{v1});
        this.compoundSettingsRootAccessor.copyValue(source, target);
        Assert.assertEquals(source.getValues().length, target.getValues().length);
        Assert.assertArrayEquals(source.getValues()[0].getValues(), target.getValues()[0].getValues());
    }

    @Test
    public void readFromPropertiesMustPopulateSimpleSettingsRootAsExpected() {
        SimpleSettingsRoot settingsRoot = new SimpleSettingsRoot();
        properties.setProperty("values.0", "1");
        properties.setProperty("values.1", "2");
        properties.setProperty("values.2", "3");
        this.simpleSettingsRootAccessor.readFromProperties(properties, settingsRoot);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, settingsRoot.getValues());
    }

    @Test
    public void readFromPropertiesMustPopulateCompoundSettingsRootAsExpected() {
        CompoundSettingsRoot settingsRoot = new CompoundSettingsRoot();
        properties.setProperty("values.0.values.0", "1");
        properties.setProperty("values.0.values.1", "2");
        properties.setProperty("values.0.values.2", "3");
        this.compoundSettingsRootAccessor.readFromProperties(properties, settingsRoot);
        Assert.assertEquals(1, settingsRoot.getValues().length);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, settingsRoot.getValues()[0].getValues());
    }

    @Test
    public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected() throws Exception {
        SimpleSettingsRoot settingsRoot = new SimpleSettingsRoot();
        settingsRoot.setValues(new Integer[]{1, 2, 3});
        this.simpleSettingsRootAccessor.writeToProperties(properties, settingsRoot);
        Object[] sortedKeys = properties.keySet().toArray();
        Arrays.sort(sortedKeys);
        Assert.assertArrayEquals(new String[]{"values.0", "values.1", "values.2"}, sortedKeys);
        Assert.assertEquals("1", properties.getProperty("values.0"));
        Assert.assertEquals("2", properties.getProperty("values.1"));
        Assert.assertEquals("3", properties.getProperty("values.2"));
    }

    @Test
    public void writeToPropertiesMustPopulatePropertiesFromCompoundSettingsRootAsExpected() throws Exception {
        CompoundSettingsRoot settingsRoot = new CompoundSettingsRoot();
        SimpleSettingsRoot v1 = new SimpleSettingsRoot();
        v1.setValues(new Integer[]{1, 2, 3});
        settingsRoot.setValues(new SimpleSettingsRoot[]{v1});
        this.compoundSettingsRootAccessor.writeToProperties(properties, settingsRoot);
        Object[] sortedKeys = properties.keySet().toArray();
        Arrays.sort(sortedKeys);
        Assert.assertArrayEquals(new String[]{"values.0.values.0", "values.0.values.1", "values.0.values.2"}, sortedKeys);
        Assert.assertEquals("1", properties.getProperty("values.0.values.0"));
        Assert.assertEquals("2", properties.getProperty("values.0.values.1"));
        Assert.assertEquals("3", properties.getProperty("values.0.values.2"));
    }
}
