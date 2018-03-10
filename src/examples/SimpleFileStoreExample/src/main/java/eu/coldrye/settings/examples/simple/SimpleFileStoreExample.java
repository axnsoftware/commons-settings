/*
 * Copyright 2018 coldrye.eu, Carsten Klein
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

package eu.coldrye.settings.examples.simple;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.BackingStoreException;
import eu.coldrye.settings.Settings;
import eu.coldrye.settings.SettingsStore;
import eu.coldrye.settings.SettingsStoreFactory;
import eu.coldrye.settings.examples.simple.pojos.EAudioBitDepth;
import eu.coldrye.settings.examples.simple.pojos.EAudioSampleRate;
import eu.coldrye.settings.examples.simple.pojos.GeneralAudioSettings;
import eu.coldrye.settings.examples.simple.pojos.GeneralGraphicsSettings;
import eu.coldrye.settings.examples.simple.pojos.GraphicsResolution;
import eu.coldrye.settings.examples.simple.pojos.SimpleSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 *
 */
public class SimpleFileStoreExample {

  private SettingsStore<SimpleSettings> settingsStore;

  public SimpleFileStoreExample() {

    try {
      File storagePath = File.createTempFile("testSettings", ".properties");
      System.out.println(storagePath);
      //storagePath.deleteOnExit();
      settingsStore = SettingsStoreFactory.newInstance().newFileStore(storagePath, SimpleSettings.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String... args) throws BackingStoreException {

    System.out.println(System.currentTimeMillis());
    SimpleFileStoreExample app = new SimpleFileStoreExample();
    Settings<SimpleSettings> settings;
    System.out.println("loading properties");
    // the properties file does not exist yet, of course, but we will reload
    // it later
    settings = app.settingsStore.loadSettings();

    System.out.println("setting properties");
    SimpleSettings settingsRoot = settings.getProperties();
    GeneralAudioSettings gas = new GeneralAudioSettings();
    gas.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
    settingsRoot.setGeneralAudioSettings(gas);

    Map<String, GraphicsResolution> profiles = new HashMap<>();
    GraphicsResolution resolution = new GraphicsResolution();
    resolution.setTheWidth(1024);
    resolution.setHeight(800);
    profiles.put("default", resolution);
    GeneralGraphicsSettings ggs = new GeneralGraphicsSettings();
    ggs.setProfiles(profiles);
    settingsRoot.setGeneralGraphicsSettings(ggs);

    List<Integer> leaflist = new ArrayList<>();
    leaflist.add(100);
    leaflist.add(200);
    leaflist.add(400);
    settingsRoot.setLeaflist(leaflist);

    Integer[] leafArray = new Integer[]{1, 2, 3};
    settingsRoot.setLeafarray(leafArray);

    Map<String, Integer> leafmap = new HashMap<>();
    leafmap.put("a", 1);
    leafmap.put("b", 2);
    leafmap.put("c", 3);
    settingsRoot.setLeafmap(leafmap);

    List<GeneralAudioSettings> branchList = new ArrayList<>();
    GeneralAudioSettings setting = new GeneralAudioSettings();
    setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_24KHZ);
    branchList.add(setting);
    setting = new GeneralAudioSettings();
    setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_24BIT);
    branchList.add(setting);
    setting = new GeneralAudioSettings();
    setting.setSampleRate(EAudioSampleRate.AUDIO_SAMPLE_RATE_48KHZ);
    branchList.add(setting);
    setting = new GeneralAudioSettings();
    setting.setBitDepth(EAudioBitDepth.AUDIO_BIT_DEPTH_16BIT);
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
    Map<String, GeneralAudioSettings> branchmap = new HashMap<>();
    branchmap.put("b", setting);
    settingsRoot.setBranchmap(branchmap);

    settings.setProperties(settingsRoot);

    System.out.println("finalizing changes");
    settings.finalizeChanges();

    System.out.println("storing properties");
    // FIXME:ERROR gas and ggs have not been stored
    settings.getStore().storeSettings(settings);

    System.out.println("and loading them back again");
    settings = settings.getStore().loadSettings();

    BackingStore backingStore = settings.getStore().getBackingStore();
    String[] keys = backingStore.keySet().toArray(new String[]{});
    Arrays.sort(keys);
    Properties properties = (Properties) backingStore.getProperties();
    for (String key : keys) {
      System.out.println(String.format("%s=%s", key, properties.getProperty(key)));
    }

    SimpleSettings simpleSettings = settings.getProperties();
    System.out.println(simpleSettings.getLeafmap());
    System.out.println(simpleSettings.getBranchmap());
    // FIXME:ERROR results in null
    System.out.println(simpleSettings.getGeneralGraphicsSettings());
    System.out.println(simpleSettings.getGeneralAudioSettings());

    System.out.println(System.currentTimeMillis());

    System.exit(0);
  }
}
