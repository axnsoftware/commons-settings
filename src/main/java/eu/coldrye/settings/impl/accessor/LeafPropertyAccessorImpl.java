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

package eu.coldrye.settings.impl.accessor;

import eu.coldrye.settings.BackingStore;
import eu.coldrye.settings.ProblemReporter;
import eu.coldrye.settings.TypeMapper;

import java.util.prefs.BackingStoreException;

/**
 * The class LeafPropertyAccessorImpl models a concrete implementation of
 * the {@code Accessor} interface, responsible for accessing simple type
 * fields, such as {@code Integer} or {@code Enum}S.
 *
 * @since 1.0.0
 */
public class LeafPropertyAccessorImpl extends AbstractPropertyAccessorImpl {

  @Override
  public void copyValue(Object source, Object target) {

    Object value = getValue(source);
    if (value != null) {
      TypeMapper mapper = getTypeMappings().get(value.getClass());
      Object copy = mapper.copyOf(value);
      setValue(copy, target);
    }
  }

  @Override
  public void readFromBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Object value = getTypeMappings().get(getType()).readFromBackingStore(backingStore, getQualifiedKey(), getType());
    if (value != null) {
      setValue(value, settingsRoot);
    }
  }

  @Override
  public void writeToBackingStore(BackingStore backingStore, Object settingsRoot) throws BackingStoreException {

    Object value = getValue(settingsRoot);
    if (value != null) {
      getTypeMappings().get(getType()).writeToBackingStore(
        backingStore, getQualifiedKey(), value);
    }
  }

  @Override
  public void validate(ProblemReporter problemReporter) {

    // TODO: might want to chain multiple validators here, i.e. min/max (length/cardinality), regex
    if (getMandatory()) {
      Object value = getValue(problemReporter.getSettings().getProperties());
    }
  }
}
