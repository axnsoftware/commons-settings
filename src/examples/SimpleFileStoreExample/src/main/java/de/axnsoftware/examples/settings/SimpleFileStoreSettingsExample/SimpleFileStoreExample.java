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
package de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample;

import de.axnsoftware.settings.IBackingStore;
import de.axnsoftware.settings.SettingsStoreFactory;
import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.EAudioBitDepth;
import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.SimpleSettings;
import de.axnsoftware.settings.ISettings;
import de.axnsoftware.settings.ISettingsStore;
import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.EAudioSampleRate;
import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.GeneralAudioSettings;
import de.axnsoftware.examples.settings.SimpleFileStoreSettingsExample.pojos.GraphicsResolution;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 */
public class SimpleFileStoreExample {

    private ISettingsStore settingsStore;

    public SimpleFileStoreExample() {
        try {
            File storagePath = File.createTempFile("testSettings", ".properties");
            storagePath.deleteOnExit();
            this.settingsStore = SettingsStoreFactory.newInstance().newFileStore(storagePath, SimpleSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) throws BackingStoreException {
        SimpleFileStoreExample app = new SimpleFileStoreExample();
        ISettings settings;
        System.out.println("loading properties");
        // the properties file does not exist yet, of course, but we will reload
        // it later
        settings = app.settingsStore.loadSettings();

        System.out.println("setting properties");
        SimpleSettings settingsRoot = (SimpleSettings) settings.getProperties();
        settingsRoot.getGeneralAudioSettings().setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        Map<String, GraphicsResolution> profiles = settingsRoot.getGeneralGraphicsSettings().getProfiles();
        GraphicsResolution resolution = new GraphicsResolution();
        resolution.setTheWidth(1024);
        resolution.setHeight(800);
        profiles.put("default", resolution);
        settingsRoot.getGeneralGraphicsSettings().setProfiles(profiles);

        List<Integer> leaflist = new ArrayList<>();
        leaflist.add(100);
        leaflist.add(200);
        leaflist.add(400);
        settingsRoot.setLeaflist(leaflist);

        Integer[] leafArray = new Integer[]{1, 2, 3};
        settingsRoot.setLeafarray(leafArray);

        settingsRoot.getLeafmap().put("a", 1);
        settingsRoot.getLeafmap().put("b", 2);
        settingsRoot.getLeafmap().put("c", 3);

        List<GeneralAudioSettings> branchList = new ArrayList<>();
        GeneralAudioSettings setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchList.add(setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchList.add(setting);
        settingsRoot.setBranchlist(branchList);

        GeneralAudioSettings[] branchArray = new GeneralAudioSettings[2];
        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchArray[0] = setting;
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchArray[1] = setting;
        settingsRoot.setBrancharray(branchArray);

        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        settingsRoot.getBranchmap().put("a", setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ);
        settingsRoot.getBranchmap().put("b", setting);

        settings.setProperties(settingsRoot);

        System.out.println("finalizing changes");
        settings.finalizeChanges();

        System.out.println("storing properties");
        settings.getStore().storeSettings(settings);

        System.out.println("and loading them back again");
        settings = settings.getStore().loadSettings();

        IBackingStore backingStore = settings.getStore().getBackingStore();
        String[] keys = (String[]) backingStore.keySet().toArray(new String[]{});
        Arrays.sort(keys);
        Properties properties = (Properties) backingStore.getProperties();
        for (String key : keys) {
            System.out.println(String.format("%s=%s", key, properties.getProperty(key)));
        }
        System.exit(0);
    }
}
