/*
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
package de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.mapper;

import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.EAudioSampleRate;
import de.axnsoftware.settings.ITypeMapper;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class AudioSampleRateMapper implements ITypeMapper {

    @Override
    public String valueOf(final Object value) {
        String result = "24kHz";

        if (value == EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ) {
            result = "48kHz";
        }

        return result;
    }

    @Override
    public Object valueOf(final String value, final Class<?> type) {
        Object result = EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ;

        if (type != EAudioSampleRate.class) {
            throw new IllegalArgumentException();
        }

        if ("48kHz".equals(value)) {
            result = EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ;
        }
        return result;
    }

    @Override
    public Object copyOf(final Object value) {
        return value;
    }
}
