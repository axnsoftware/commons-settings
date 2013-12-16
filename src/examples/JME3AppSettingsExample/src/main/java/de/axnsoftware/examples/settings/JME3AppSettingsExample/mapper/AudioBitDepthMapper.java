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
package de.axnsoftware.examples.settings.JME3AppSettingsExample.mapper;

import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.EAudioBitDepth;
import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.ITypeMapper;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class AudioBitDepthMapper implements ITypeMapper {

    @Override
    public Object copyOf(final Object value) {
        return value;
    }

    @Override
    public Object readFromBackingStore(IBackingStore backingStore, String key, Class<?> type) throws BackingStoreException {
        Object result = null;

        try {
            this.valueOf(backingStore.getString(key), type);
        } catch (IllegalArgumentException e) {
            throw new BackingStoreException(e);
        }

        return result;
    }

    @Override
    public Object valueOf(final String value, final Class<?> type) {
        Object result = EAudioBitDepth.AUDIO_BIT_DEPTH_16BIT;

        if (type != EAudioBitDepth.class) {
            throw new IllegalArgumentException("unsupported type " + type.getName());
        }

        if ("24bit".equals(value)) {
            result = EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT;
        }

        return result;
    }

    @Override
    public void writeToBackingStore(IBackingStore backingStore, String key, Object value) throws BackingStoreException {
        String stringValue = "16bit";
        if (EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT.equals(value)) {
            stringValue = "24bit";
        }
        backingStore.setString(key, stringValue);
    }
}
