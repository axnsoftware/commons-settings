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

package eu.coldrye.settings.examples.simple.mapper;

import eu.coldrye.settings.BackingStoreException;
import eu.coldrye.settings.examples.simple.pojos.EAudioSampleRate;
import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.TypeMapper;

/**
 *
 *
 */
public class AudioSampleRateMapper implements TypeMapper {

  @Override
  public Object copyOf(Object value) {

    return value;
  }

  @Override
  public Object readFromBackingStore(BackingStore backingStore, String key, Class<?> type)
    throws BackingStoreException {

    Object result = null;

    try {
      this.valueOf(backingStore.getString(key), type);
    } catch (IllegalArgumentException e) {
      throw new BackingStoreException(e);
    }

    return result;
  }

  @Override
  public Object valueOf(String value, Class<?> type) {

    Object result = EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ;

    if (type != EAudioSampleRate.class) {
      throw new IllegalArgumentException(type.getName());
    }

    if ("48kHz".equals(value)) {
      result = EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ;
    }

    return result;
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, String key, Object value) throws BackingStoreException {

    String stringValue = "24kHz";
    if (EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ.equals(value)) {
      stringValue = "48kHz";
    }
    backingStore.setString(key, stringValue);
  }
}
