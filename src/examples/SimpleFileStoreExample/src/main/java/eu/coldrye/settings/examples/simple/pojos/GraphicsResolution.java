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

package eu.coldrye.settings.examples.simple.pojos;

import eu.coldrye.settings.Property;
import eu.coldrye.settings.PropertyClass;

/**
 *
 *
 */
@PropertyClass
public class GraphicsResolution {

  @Property(key = "width", defaultValue = "640")
  private Integer theWidth;

  @Property(defaultValue = "480")
  private Integer height;

  @Property(defaultValue = "16")
  private Integer depth;

  public Integer getTheWidth() {

    return theWidth;
  }

  public void setTheWidth(Integer theWidth) {

    this.theWidth = theWidth;
  }

  public Integer getHeight() {

    return height;
  }

  public void setHeight(Integer height) {

    this.height = height;
  }

  public Integer getDepth() {

    return depth;
  }

  public void setDepth(Integer depth) {

    this.depth = depth;
  }
}
