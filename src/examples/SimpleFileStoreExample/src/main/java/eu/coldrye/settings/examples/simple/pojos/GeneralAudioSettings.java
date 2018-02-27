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
import eu.coldrye.settings.examples.simple.mapper.AudioBitDepthMapper;
import eu.coldrye.settings.examples.simple.mapper.AudioSampleRateMapper;

/**
 *
 *
 */
@PropertyClass
public class GeneralAudioSettings {

  @Property(defaultValue = "16bit", typeMapper = AudioBitDepthMapper.class)
  private EAudioBitDepth bitDepth;

  @Property(defaultValue = "24kHz", typeMapper = AudioSampleRateMapper.class)
  private EAudioSampleRate sampleRate;

  public EAudioSampleRate getSampleRate() {

    return sampleRate;
  }

  public void setSampleRate(EAudioSampleRate sampleRate) {

    this.sampleRate = sampleRate;
  }

  public EAudioBitDepth getBitDepth() {

    return bitDepth;
  }

  public void setBitDepth(EAudioBitDepth bitDepth) {

    this.bitDepth = bitDepth;
  }
}
