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

package eu.coldrye.settings.fixtures;

import eu.coldrye.settings.Property;

import java.util.Objects;

/**
 *
 *
 */
@Property(typeMapper = CustomTypeTypeMapperImpl.class)
public class CustomType implements Comparable<CustomType> {

  private Integer intValue;

  private Float floatValue;

  public CustomType(Integer intValue, Float floatValue) {

    this.intValue = intValue;
    this.floatValue = floatValue;
  }

  public Integer getIntValue() {

    return intValue;
  }

  public Float getFloatValue() {

    return floatValue;
  }

  @Override
  public boolean equals(Object o) {

    boolean result = false;
    if (o instanceof CustomType) {
      result = 0 == this.compareTo((CustomType) o);
    }
    return result;
  }

  @Override
  public int hashCode() {

    int hash = 7;
    hash = 41 * hash + Objects.hashCode(this.intValue);
    hash = 41 * hash + Objects.hashCode(this.floatValue);
    return hash;
  }

  @Override
  public int compareTo(CustomType o) {

    int result = this.intValue.compareTo(o.intValue);
    if (0 == result) {
      result = this.floatValue.compareTo(o.floatValue);
    }
    return result;
  }
}
