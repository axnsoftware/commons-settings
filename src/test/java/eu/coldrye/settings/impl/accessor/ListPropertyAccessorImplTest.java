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
import eu.coldrye.settings.fixtures.CompoundListFieldSettingsRoot;
import eu.coldrye.settings.fixtures.DummyBackingStore;
import eu.coldrye.settings.fixtures.SimpleListFieldSettingsRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 */
public class ListPropertyAccessorImplTest {

  private Accessor simpleSettingsRootAccessor;

  private Accessor compoundSettingsRootAccessor;

  private BackingStore properties;

  @BeforeEach
  public void setup() {

    this.simpleSettingsRootAccessor = RootAccessorBuilder.newInstance().buildRootAccessor(
      SimpleListFieldSettingsRoot.class);
    this.compoundSettingsRootAccessor = RootAccessorBuilder.newInstance().buildRootAccessor(
      CompoundListFieldSettingsRoot.class);
    this.properties = new DummyBackingStore();
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForSimpleSettingsRoot() {

    SimpleListFieldSettingsRoot source = new SimpleListFieldSettingsRoot();
    SimpleListFieldSettingsRoot target = new SimpleListFieldSettingsRoot();
    source.setValues(new ArrayList<>());
    source.getValues().add(1);
    source.getValues().add(2);
    source.getValues().add(3);
    this.simpleSettingsRootAccessor.copyValue(source, target);
    Assertions.assertEquals(source.getValues(), target.getValues());
  }

  @Test
  public void copyValueMustPopulateTargetAsExpectedForCompoundSettingsRoot() {

    CompoundListFieldSettingsRoot source = new CompoundListFieldSettingsRoot();
    CompoundListFieldSettingsRoot target = new CompoundListFieldSettingsRoot();
    SimpleListFieldSettingsRoot v1 = new SimpleListFieldSettingsRoot();
    v1.setValues(new ArrayList<>());
    v1.getValues().add(1);
    v1.getValues().add(2);
    v1.getValues().add(3);
    source.setValues(new ArrayList<>());
    source.getValues().add(v1);
    this.compoundSettingsRootAccessor.copyValue(source, target);
    Assertions.assertEquals(source.getValues().size(), target.getValues().size());
    Assertions.assertEquals(source.getValues().get(0).getValues(), target.getValues().get(0).getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateSimpleSettingsRootAsExpected() throws Exception {

    SimpleListFieldSettingsRoot settingsRoot = new SimpleListFieldSettingsRoot();
    properties.setString("values.0", "1");
    properties.setString("values.1", "2");
    properties.setString("values.2", "3");
    this.simpleSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    List<Integer> expected = new ArrayList<>();
    expected.add(1);
    expected.add(2);
    expected.add(3);
    Assertions.assertEquals(expected, settingsRoot.getValues());
  }

  @Test
  public void readFromPropertiesMustPopulateCompoundSettingsRootAsExpected() throws Exception {

    CompoundListFieldSettingsRoot settingsRoot = new CompoundListFieldSettingsRoot();
    properties.setString("values.0.values.0", "1");
    properties.setString("values.0.values.1", "2");
    properties.setString("values.0.values.2", "3");
    this.compoundSettingsRootAccessor.readFromBackingStore(properties, settingsRoot);
    Assertions.assertEquals(1, settingsRoot.getValues().size());
    List<Integer> expected = new ArrayList<>();
    expected.add(1);
    expected.add(2);
    expected.add(3);
    Assertions.assertEquals(expected, settingsRoot.getValues().get(0).getValues());
  }

  @Test
  public void writeToPropertiesMustPopulatePropertiesFromSimpleSettingsRootAsExpected() throws Exception {

    SimpleListFieldSettingsRoot settingsRoot = new SimpleListFieldSettingsRoot();
    settingsRoot.setValues(new ArrayList<>());
    settingsRoot.getValues().add(1);
    settingsRoot.getValues().add(2);
    settingsRoot.getValues().add(3);
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

    CompoundListFieldSettingsRoot settingsRoot = new CompoundListFieldSettingsRoot();
    SimpleListFieldSettingsRoot v1 = new SimpleListFieldSettingsRoot();
    v1.setValues(new ArrayList<>());
    v1.getValues().add(1);
    v1.getValues().add(2);
    v1.getValues().add(3);
    settingsRoot.setValues(new ArrayList<>());
    settingsRoot.getValues().add(v1);
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
