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
package de.axnsoftware.settings.integration.customtype;

import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.PropertyClass;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
@PropertyClass
public class SettingsRoot
{

    @Property(typeMapper =
              "de.axnsoftware.settings.integration.customtype.CustomTypeTypeMapperImpl")
    private CustomType customType;
    @Property(typeMapper =
              "de.axnsoftware.settings.integration.customtype.CustomTypeTypeMapperImpl")
    private List<CustomType> customTypeList;
    @Property(typeMapper =
              "de.axnsoftware.settings.integration.customtype.CustomTypeTypeMapperImpl")
    private Map<String, CustomType> customTypeMap;

    public List<CustomType> getCustomTypeList()
    {
        return customTypeList;
    }

    public void setCustomTypeList(
            List<CustomType> customTypeList)
    {
        this.customTypeList = customTypeList;
    }

    public Map<String, CustomType> getCustomTypeMap()
    {
        return customTypeMap;
    }

    public void setCustomTypeMap(
            Map<String, CustomType> customTypeMap)
    {
        this.customTypeMap = customTypeMap;
    }

    public CustomType[] getCustomTypeArray()
    {
        return customTypeArray;
    }

    public void setCustomTypeArray(CustomType[] customTypeArray)
    {
        this.customTypeArray = customTypeArray;
    }
    @Property(typeMapper =
              "de.axnsoftware.settings.integration.customtype.CustomTypeTypeMapperImpl")
    private CustomType[] customTypeArray;

    public CustomType getCustomType()
    {
        return customType;
    }

    public void setCustomType(CustomType customType)
    {
        this.customType = customType;
    }
}
