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
package de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos;

import de.axnsoftware.settings.Property;
import de.axnsoftware.settings.PropertyClass;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
@PropertyClass
public class SimpleSettings {

    @Property(key = "graphics.general")
    private GeneralGraphicsSettings generalGraphicsSettings;
    @Property(key = "audio.general")
    private GeneralAudioSettings generalAudioSettings;
    @Property(key = "profile.audio.general")
    private GeneralAudioSettings profileGeneralAudioSettings;
    @Property
    private List<Integer> leaflist;
    @Property
    private Integer[] leafarray;
    @Property
    private Map<String, Integer> leafmap;
    @Property
    private List<GeneralAudioSettings> branchlist;

    public List<GeneralAudioSettings> getBranchlist() {
        return branchlist;
    }

    public void setBranchlist(List<GeneralAudioSettings> branchlist) {
        this.branchlist = branchlist;
    }

    public GeneralAudioSettings[] getBrancharray() {
        return brancharray;
    }

    public void setBrancharray(GeneralAudioSettings[] brancharray) {
        this.brancharray = brancharray;
    }

    public Map<String, GeneralAudioSettings> getBranchmap() {
        return branchmap;
    }

    public void setBranchmap(Map<String, GeneralAudioSettings> branchmap) {
        this.branchmap = branchmap;
    }
    @Property
    private GeneralAudioSettings[] brancharray;
    @Property
    private Map<String, GeneralAudioSettings> branchmap;

    public Map<String, Integer> getLeafmap() {
        return leafmap;
    }

    public void setLeafmap(Map<String, Integer> leafmap) {
        this.leafmap = leafmap;
    }

    public Integer[] getLeafarray() {
        return leafarray;
    }

    public void setLeafarray(Integer[] leafarray) {
        this.leafarray = leafarray;
    }

    public List<Integer> getLeaflist() {
        return leaflist;
    }

    public void setLeaflist(List<Integer> leaflist) {
        this.leaflist = leaflist;
    }

    public GeneralGraphicsSettings getGeneralGraphicsSettings() {
        return this.generalGraphicsSettings;
    }

    public void setGeneralGraphicsSettings(final GeneralGraphicsSettings generalGraphicsSettings) {
        this.generalGraphicsSettings = generalGraphicsSettings;
    }

    public GeneralAudioSettings getGeneralAudioSettings() {
        return this.generalAudioSettings;
    }

    public void setGeneralAudioSettings(final GeneralAudioSettings generalAudioSettings) {
        this.generalAudioSettings = generalAudioSettings;
    }

    public GeneralAudioSettings getProfileGeneralAudioSettings() {
        return this.profileGeneralAudioSettings;
    }

    public void setProfileGeneralAudioSettings(final GeneralAudioSettings profileGeneralAudioSettings) {
        this.profileGeneralAudioSettings = profileGeneralAudioSettings;
    }
}
