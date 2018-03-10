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

package eu.coldrye.settings.util;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.BackingStoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccessorUtils {

  public static List<Integer> determineItemKeys(BackingStore backingStore, String key, AtomicInteger maxIndex)
    throws BackingStoreException {

    List<Integer> result = new ArrayList<>();
    backingStore.keySet().stream().filter(name -> name.startsWith(key)).forEachOrdered(name -> {

      String part = name.substring(key.length() + 1);
      int index = Integer.valueOf(part.substring(0, part.contains(".") ? part.indexOf(".") : part.length()));
      maxIndex.set(Math.max(maxIndex.get(), index + 1));
      if (!result.contains(index)) {
        result.add(index);
      }
    });

    return result;
  }

  public static List<String> determineItemKeys(BackingStore backingStore, String key)
    throws BackingStoreException {

    List<String> result = new ArrayList<>();
    backingStore.keySet().stream().filter(name -> name.startsWith(key)).forEachOrdered(name -> {

      String part = name.substring(key.length() + 1);
      String prop = part.substring(0, part.contains(".") ? part.indexOf(".") : part.length());
      if (!result.contains(prop)) {
        result.add(prop);
      }
    });

    return result;
  }

  /**
   * Must not be instantiated.
   */
  private AccessorUtils() {

  }
}
