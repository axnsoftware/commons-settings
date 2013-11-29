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
 * TODO:document
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public interface ISettings {

    /**
     * TODO:document
     *
     * @return
     */
    public Class<?> getType();

    /**
     * TODO:document
     */
    public void discardChanges();

    /**
     * TODO:document
     */
    public void finalizeChanges();

    /**
     * TODO:document
     *
     * @return
     */
    public Boolean getHasUncommittedChanges();

    /**
     * TODO:document
     *
     * @return
     */
    public Object getProperties();

    /**
     * TODO:document
     *
     * @return
     */
    public ISettingsStore getStore();

    // properties must be an instance of #getType()
    /**
     * TODO:document
     *
     * @param properties
     */
    public void setProperties(final Object properties);
}
