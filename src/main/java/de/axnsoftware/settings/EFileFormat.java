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

/**
 * The enum EFileFormat defines the various file formats that can be read or
 * written out by file based implementations of the {@link IBackingStore}
 * interface.
 */
public enum EFileFormat
{

    /**
     * Configuration files are stored as plain text files. The actual format is
     * subject to the backing store implementation.
     */
    PLAIN_TEXT,
    /**
     * Configuration files are stored as XML files. The actual format is subject
     * to the backing store implementation.
     */
    XML;
}
