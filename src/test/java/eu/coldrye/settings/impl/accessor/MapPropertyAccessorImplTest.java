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
import eu.coldrye.settings.fixtures.CompoundMapFieldSettingsRoot;
import eu.coldrye.settings.fixtures.DummyBackingStore;
import eu.coldrye.settings.fixtures.SimpleMapFieldSettingsRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class MapPropertyAccessorImplTest {

  private Accessor simpleSettingsRootAccessor;

  private Accessor compoundSettingsRootAccessor;

  private BackingStore properties;

  @BeforeEach
  public void setup() {

    this.simpleSettingsRootAccessor = new RootAccessorBuilder().buildRootAccessor(
      SimpleMapFieldSettingsRoot.class);
    this.compoundSettingsRootAccessor = new RootAccessorBuilder().buildRootAccessor(
      CompoundMapFieldSettingsRoot.class);
    this.properties = new DummyBackingStore();
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot() {

    SimpleMapFieldSettingsRoot source = new SimpleMapFieldSettingsRoot();
    SimpleMapFieldSettingsRoot target = new SimpleMapFieldSettingsRoot();
    source.setValues(new HashMap<>());
    source.getValues().put("0", 1);
    source.getValues().put("1", 1);
    source.getValues().put("2", 1);
    this.simpleSettingsRootAccessor.copyValue(source, target);
    Assertions.assertEquals(source.getValues(), target.getValues());
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot() {

    CompoundMapFieldSettingsRoot source = new CompoundMapFieldSettingsRoot();
    CompoundMapFieldSettingsRoot target = new CompoundMapFieldSettingsRoot();
    SimpleMapFieldSettingsRoot v1 = new SimpleMapFieldSettingsRoot();
    v1.setValues(new HashMap<>());
    v1.getValues().put("0", 1);
    v1.getValues().put("1", 2);
    v1.getValues().put("2", 3);
    source.setValues(new HashMap<>());
    source.getValues().put("0", v1);
    this.compoundSettingsRootAccessor.copyValue(source, target);
    Assertions.assertEquals(source.getValues().size(), target.getValues().size());
    Assertions.assertEquals(source.getValues().get("0").getValues(), target.getValues().get("0").getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateSimpleSettingsRootAsExpected() throws Exception {

    SimpleMapFieldSettingsRoot settingsRoot = new SimpleMapFieldSettingsRoot();
    properties.setString("values.0", "1");
    properties.setString("values.1", "2");
    properties.setString("values.2", "3");
    this.simpleSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    Map<String, Integer> expected = new HashMap<>();
    expected.put("0", 1);
    expected.put("1", 2);
    expected.put("2", 3);
    Assertions.assertEquals(expected, settingsRoot.getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateCompoundSettingsRootAsExpected() throws Exception {

    CompoundMapFieldSettingsRoot settingsRoot = new CompoundMapFieldSettingsRoot();
    properties.setString("values.0.values.0", "1");
    properties.setString("values.0.values.1", "2");
    properties.setString("values.0.values.2", "3");
    this.compoundSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    Assertions.assertEquals(1, settingsRoot.getValues().size());
    Map<String, Integer> expected = new HashMap<>();
    expected.put("0", 1);
    expected.put("1", 2);
    expected.put("2", 3);
    Assertions.assertEquals(expected, settingsRoot.getValues().get("0").getValues());
  }

  @Test
  public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected() throws Exception {

    SimpleMapFieldSettingsRoot settingsRoot = new SimpleMapFieldSettingsRoot();
    settingsRoot.setValues(new HashMap<>());
    settingsRoot.getValues().put("0", 1);
    settingsRoot.getValues().put("1", 2);
    settingsRoot.getValues().put("2", 3);
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

    CompoundMapFieldSettingsRoot settingsRoot = new CompoundMapFieldSettingsRoot();
    SimpleMapFieldSettingsRoot v1 = new SimpleMapFieldSettingsRoot();
    v1.setValues(new HashMap<>());
    v1.getValues().put("0", 1);
    v1.getValues().put("1", 2);
    v1.getValues().put("2", 3);
    settingsRoot.setValues(new HashMap<>());
    settingsRoot.getValues().put("0", v1);
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
