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

package eu.coldrye.settings.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 *
 */
public class OrderedPropertiesTest {

  private Properties properties;

  private final String[] expected = new String[]{"a", "f", "x", "z"};

  @BeforeEach
  public void setup() {

    properties = new OrderedProperties();
    properties.setProperty("x", "x");
    properties.setProperty("a", "x");
    properties.setProperty("z", "x");
    properties.setProperty("f", "x");
  }

  @Test
  public void stringPropertyNamesMustReturnKeysInProperOrder() {

    Set<String> keys = properties.stringPropertyNames();
    String[] actual = keys.toArray(new String[]{});
    Assertions.assertArrayEquals(expected, actual);
  }

  @Test
  public void keySetMustReturnKeysInProperOrder() {

    List<String> keyList = new ArrayList<>();
    for (Object key : properties.keySet()) {
      keyList.add((String) key);
    }
    String[] actual = keyList.toArray(new String[]{});
    Assertions.assertArrayEquals(expected, actual);
  }

  @Test
  public void keysMustEnumerateKeysInProperOrder() {

    List<String> keyList = new ArrayList<>();
    Enumeration<Object> keys = properties.keys();
    while (keys.hasMoreElements()) {
      keyList.add((String) keys.nextElement());
    }
    String[] actual = keyList.toArray(new String[]{});
    Assertions.assertArrayEquals(expected, actual);
  }
}
