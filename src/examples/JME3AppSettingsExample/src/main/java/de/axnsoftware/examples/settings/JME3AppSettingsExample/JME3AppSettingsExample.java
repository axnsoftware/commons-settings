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
package de.axnsoftware.examples.settings.JME3AppSettingsExample;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.SettingsStoreFactory;
import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.EAudioBitDepth;
import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.SimpleSettings;
import de.axnsoftware.settings.ISettings;
import de.axnsoftware.settings.ISettingsStore;
import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.EAudioSampleRate;
import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.GeneralAudioSettings;
import de.axnsoftware.examples.settings.JME3AppSettingsExample.pojos.GraphicsResolution;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class JME3AppSettingsExample {

    private ISettingsStore settingsStore;

    public JME3AppSettingsExample() {
        try {
            File storagePath = File.createTempFile("testSettings", ".properties");
            storagePath.deleteOnExit();
            this.settingsStore = SettingsStoreFactory.newInstance().newFileStore(storagePath, SimpleSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) throws BackingStoreException {
        JME3AppSettingsExample app = new JME3AppSettingsExample();
        ISettings settings;
        System.out.println("loading properties");
        // the properties file does not exist yet, of course, but we will reload
        // it later
        settings = app.settingsStore.loadSettings();

        System.out.println("setting properties");
        SimpleSettings properties = (SimpleSettings) settings.getProperties();
        properties.getGeneralAudioSettings().setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        Map<String, GraphicsResolution> profiles = properties.getGeneralGraphicsSettings().getProfiles();
        GraphicsResolution resolution = new GraphicsResolution();
        resolution.setTheWidth(1024);
        resolution.setHeight(800);
        profiles.put("default", resolution);
        properties.getGeneralGraphicsSettings().setProfiles(profiles);

        List<Integer> leaflist = new ArrayList<>();
        leaflist.add(100);
        leaflist.add(200);
        leaflist.add(400);
        properties.setLeaflist(leaflist);

        Integer[] leafArray = new Integer[]{1, 2, 3};
        properties.setLeafarray(leafArray);

        properties.getLeafmap().put("a", 1);
        properties.getLeafmap().put("b", 2);
        properties.getLeafmap().put("c", 3);

        List<GeneralAudioSettings> branchList = new ArrayList<>();
        GeneralAudioSettings setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchList.add(setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchList.add(setting);
        properties.setBranchlist(branchList);

        GeneralAudioSettings[] branchArray = new GeneralAudioSettings[2];
        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchArray[0] = setting;
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchArray[1] = setting;
        properties.setBrancharray(branchArray);

        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        properties.getBranchmap().put("a", setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ);
        properties.getBranchmap().put("b", setting);

        settings.setProperties(properties);

        System.out.println("finalizing changes");
        settings.finalizeChanges();

        System.out.println("storing properties");
        settings.getStore().storeSettings(settings);

        System.out.println("and loading them back again");
        settings = settings.getStore().loadSettings();

        IBackingStore backingStore = settings.getStore().getBackingStoreWrapper();
        String[] keys = (String[]) backingStore.keySet().toArray(new String[]{});
        Arrays.sort(keys);
        for (String key : keys) {
            System.out.println(String.format("%s=%s", key, backingStore.getString(key)));
        }
        System.exit(0);
    }
}
