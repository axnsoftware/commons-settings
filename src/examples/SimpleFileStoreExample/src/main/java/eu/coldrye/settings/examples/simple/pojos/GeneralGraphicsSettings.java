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

import java.util.Map;

/**
 *
 *
 */
@PropertyClass
public class GeneralGraphicsSettings {

  @Property(key = "resolution")
  private GraphicsResolution graphicsResolution;

  @Property
  private Map<String, GraphicsResolution> profiles;

  public Map<String, GraphicsResolution> getProfiles() {

    return profiles;
  }

  public void setProfiles(Map<String, GraphicsResolution> profiles) {

    this.profiles = profiles;
  }

  public GraphicsResolution getGraphicsResolution() {

    return graphicsResolution;
  }

  public void setGraphicsResolution(GraphicsResolution graphicsResolution) {

    this.graphicsResolution = graphicsResolution;
  }
}
