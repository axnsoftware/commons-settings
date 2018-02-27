/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.impl.accessor;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.fixtures.CompoundArrayFieldSettingsRoot;
import eu.coldrye.settings.fixtures.DummyBackingStore;
import eu.coldrye.settings.fixtures.SimpleArrayFieldSettingsRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 *
 *
 */
public class ArrayPropertyAccessorImplTest {

  private Accessor simpleSettingsRootAccessor;

  private Accessor compoundSettingsRootAccessor;

  private BackingStore properties;

  @BeforeEach
  public void setup() {

    this.simpleSettingsRootAccessor = RootAccessorBuilder.newInstance().buildRootAccessor(
      SimpleArrayFieldSettingsRoot.class);
    this.compoundSettingsRootAccessor = RootAccessorBuilder.newInstance().buildRootAccessor(
      CompoundArrayFieldSettingsRoot.class);
    this.properties = new DummyBackingStore();
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot() {

    SimpleArrayFieldSettingsRoot source = new SimpleArrayFieldSettingsRoot();
    SimpleArrayFieldSettingsRoot target = new SimpleArrayFieldSettingsRoot();
    source.setValues(new Integer[]{1, 2, 3});
    this.simpleSettingsRootAccessor.copyValue(source, target);
    Assertions.assertArrayEquals(source.getValues(), target.getValues());
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot() {

    CompoundArrayFieldSettingsRoot source = new CompoundArrayFieldSettingsRoot();
    CompoundArrayFieldSettingsRoot target = new CompoundArrayFieldSettingsRoot();
    SimpleArrayFieldSettingsRoot v1 = new SimpleArrayFieldSettingsRoot();
    v1.setValues(new Integer[]{1, 2, 3});
    source.setValues(new SimpleArrayFieldSettingsRoot[]{v1});
    this.compoundSettingsRootAccessor.copyValue(source, target);
    Assertions.assertEquals(source.getValues().length, target.getValues().length);
    Assertions.assertArrayEquals(source.getValues()[0].getValues(), target.getValues()[0].getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateSimpleSettingsRootAsExpected() throws Exception {

    SimpleArrayFieldSettingsRoot settingsRoot = new SimpleArrayFieldSettingsRoot();
    properties.setString("values.0", "1");
    properties.setString("values.1", "2");
    properties.setString("values.2", "3");
    this.simpleSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, settingsRoot.getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateCompoundSettingsRootAsExpected() throws Exception {

    CompoundArrayFieldSettingsRoot settingsRoot = new CompoundArrayFieldSettingsRoot();
    properties.setString("values.0.values.0", "1");
    properties.setString("values.0.values.1", "2");
    properties.setString("values.0.values.2", "3");
    this.compoundSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    Assertions.assertEquals(1, settingsRoot.getValues().length);
    Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, settingsRoot.getValues()[0].getValues());
  }

  @Test
  public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected() throws Exception {

    SimpleArrayFieldSettingsRoot settingsRoot = new SimpleArrayFieldSettingsRoot();
    settingsRoot.setValues(new Integer[]{1, 2, 3});
    this.simpleSettingsRootAccessor.writeToBackingStore(properties, settingsRoot);
    Object[] sortedKeys = properties.keySet().toArray();
    Arrays.sort(sortedKeys);
    Assertions.assertArrayEquals(new String[]{"values.0", "values.1", "values.2"}, sortedKeys);
    Assertions.assertEquals("1", properties.getString("values.0"));
    Assertions.assertEquals("2", properties.getString("values.1"));
    Assertions.assertEquals("3", properties.getString("values.2"));
  }

  @Test
  public void writeToPropertiesMustPopulatePropertiesFromCompoundSettingsRootAsExpected() throws Exception {

    CompoundArrayFieldSettingsRoot settingsRoot = new CompoundArrayFieldSettingsRoot();
    SimpleArrayFieldSettingsRoot v1 = new SimpleArrayFieldSettingsRoot();
    v1.setValues(new Integer[]{1, 2, 3});
    settingsRoot.setValues(new SimpleArrayFieldSettingsRoot[]{v1});
    this.compoundSettingsRootAccessor.writeToBackingStore(properties, settingsRoot);
    Object[] sortedKeys = properties.keySet().toArray();
    Arrays.sort(sortedKeys);
    Assertions.assertArrayEquals(new String[]{"values.0.values.0", "values.0.values.1", "values.0.values.2"},
      sortedKeys);
    Assertions.assertEquals("1", properties.getString("values.0.values.0"));
    Assertions.assertEquals("2", properties.getString("values.0.values.1"));
    Assertions.assertEquals("3", properties.getString("values.0.values.2"));
  }
}
