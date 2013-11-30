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
        long startMillis = System.currentTimeMillis();
        long start = System.nanoTime();
        settings = app.settingsStore.loadSettings();
        long endMillis = System.currentTimeMillis();
        long end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("getting properties");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        SimpleSettings properties = (SimpleSettings) settings.getProperties();
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("setting properties");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        properties.getGeneralAudioSettings().setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        Map<String, GraphicsResolution> profiles = properties.getGeneralGraphicsSettings().getProfiles();
        GraphicsResolution resolution = new GraphicsResolution();
        resolution.setTheWidth(1024);
        resolution.setHeight(800);
        profiles.put("default", resolution);
        properties.getGeneralGraphicsSettings().setProfiles(profiles);
        settings.setProperties(properties);
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("getting properties");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        settings.getProperties();
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("accessing leaflist property");
        List<Integer> leaflist = new ArrayList<>();
        leaflist.add(100);
        leaflist.add(200);
        leaflist.add(400);
        properties.setLeaflist(leaflist);
        settings.setProperties(properties);

        System.out.println("accessing leafarray property");
        Integer[] leafArray = new Integer[]{1, 2, 3};
        properties.setLeafarray(leafArray);
        settings.setProperties(properties);

        System.out.println("accessing leafmap property");
        properties.getLeafmap().put("a", 1);
        properties.getLeafmap().put("b", 2);
        properties.getLeafmap().put("c", 3);
        settings.setProperties(properties);

        System.out.println("accessing branchlist property");
        List<GeneralAudioSettings> branchList = new ArrayList<>();
        GeneralAudioSettings setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchList.add(setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchList.add(setting);
        properties.setBranchlist(branchList);
        settings.setProperties(properties);

        System.out.println("accessing brancharray property");
        GeneralAudioSettings[] branchArray = new GeneralAudioSettings[2];
        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        branchArray[0] = setting;
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        branchArray[1] = setting;
        properties.setBrancharray(branchArray);
        settings.setProperties(properties);

        System.out.println("accessing branchmap property");
        setting = new GeneralAudioSettings();
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
        properties.getBranchmap().put("a", setting);
        setting = new GeneralAudioSettings();
        setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
        setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ);
        properties.getBranchmap().put("b", setting);
        settings.setProperties(properties);

        System.out.println("finalizing changes");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        settings.finalizeChanges();
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("getting properties");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        settings.getProperties();
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("storing properties");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        settings.getStore().storeSettings(settings);
        endMillis = System.currentTimeMillis();
        end = System.nanoTime();
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));

        System.out.println("getting and printing properties from backing store");
        startMillis = System.currentTimeMillis();
        start = System.nanoTime();
        IBackingStore wrapper = app.settingsStore.getBackingStoreWrapper();
        Properties props = (Properties) wrapper.getProperties();
        end = System.nanoTime();
        System.out.println(props);
        System.out.println("Nanoseconds: " + ((end - start) / 1000));
        System.out.println("Microseconds: " + ((end - start) / 1000));
        System.out.println("Milliseconds: " + ((end - start) / 1000 / 1000));
        System.out.println("Milliseconds: " + (endMillis - startMillis));
        System.exit(0);
    }
}
