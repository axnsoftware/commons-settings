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
package de.axnsoftware.settings;

import de.axnsoftware.settings.impl.SettingsStoreImpl;
import de.axnsoftware.settings.impl.IAccessor;
import de.axnsoftware.settings.impl.IMutableBackingStoreWrapper;
import de.axnsoftware.settings.impl.PropertiesFileBackingStoreWrapperImpl;
import de.axnsoftware.settings.impl.accessor.RootAccessorFactory;
import java.io.File;
import java.util.Properties;

/**
 * TODO:document
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class SettingsStoreFactory {

    /**
     * TODO:document
     *
     * @param storagePath
     * @param type
     * @return
     */
    public static ISettingsStore newFileStore(final File storagePath, final Class<?> type) {
        return createNewFileStore(EFileFormat.FILE_FORMAT_PLAIN_TEXT, storagePath, type);
    }

    /**
     * TODO:document
     *
     * @param storagePath
     * @param type
     * @return
     */
    public static ISettingsStore newXMLFileStore(final File storagePath, final Class<?> type) {
        return createNewFileStore(EFileFormat.FILE_FORMAT_XML, storagePath, type);
    }

    private static ISettingsStore createNewFileStore(final EFileFormat fileStoreType, final File storagePath, final Class<?> type) {
        IAccessor rootAccessor = RootAccessorFactory.newInstance().buildRootAccessor(type);
        IMutableBackingStoreWrapper backingStoreWrapper = new PropertiesFileBackingStoreWrapperImpl(fileStoreType, storagePath);
        return new SettingsStoreImpl(backingStoreWrapper, rootAccessor, type);
    }
}
