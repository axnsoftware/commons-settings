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
package de.axnsoftware.settings.impl;

import de.axnsoftware.settings.ITypeMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Carsten Klein "cklein" <carsten.klein@axn-software.de>
 * @since 1.0.0
 */
public class DefaultTypeMapperImpl implements ITypeMapper {

    private static Map<Class<?>, ITypeMapper> preparedDefaultTypeMappings;

    @Override
    public Object valueOf(final String value, final Class<?> type) {
        Object result = value;
        if (value != null && value.length() > 0) {
            if (BigDecimal.class.equals(type)) {
                result = new BigDecimal(value);
            } else if (BigInteger.class.equals(type)) {
                result = new BigInteger(value);
            } else if (Boolean.class.equals(type)) {
                result = Boolean.valueOf(value);
            } else if (Byte.class.equals(type)) {
                result = Byte.valueOf(value);
            } else if (Character.class.equals(type)) {
                result = Character.valueOf(value.charAt(0));
            } else if (Double.class.equals(type)) {
                result = Double.valueOf(value);
            } else if (type.isEnum()) {
                result = Enum.valueOf((Class<? extends Enum>) type, value);
            } else if (Float.class.equals(type)) {
                result = Float.valueOf(value);
            } else if (Integer.class.equals(type)) {
                result = Integer.valueOf(value);
            } else if (Long.class.equals(type)) {
                result = Long.valueOf(value);
            } else if (Short.class.equals(type)) {
                result = Short.valueOf(value);
            } else if (UUID.class.equals(type)) {
                result = UUID.fromString(value);
            }
        }
        return result;
    }

    @Override
    public String valueOf(final Object value) {
        String result = null;
        if (value != null) {
            result = value.toString();
        }
        return result;
    }

    @Override
    public Object copyOf(final Object value) {
        return value;
    }

    public static Map<Class<?>, ITypeMapper> getPreparedDefaultTypeMappings() {
        if (null == preparedDefaultTypeMappings) {
            ITypeMapper mapper = new DefaultTypeMapperImpl();
            preparedDefaultTypeMappings = new HashMap<>();
            preparedDefaultTypeMappings.put(BigDecimal.class, mapper);
            preparedDefaultTypeMappings.put(BigInteger.class, mapper);
            preparedDefaultTypeMappings.put(Boolean.class, mapper);
            preparedDefaultTypeMappings.put(Byte.class, mapper);
            preparedDefaultTypeMappings.put(Character.class, mapper);
            preparedDefaultTypeMappings.put(Double.class, mapper);
            preparedDefaultTypeMappings.put(Enum.class, mapper);
            preparedDefaultTypeMappings.put(Float.class, mapper);
            preparedDefaultTypeMappings.put(Integer.class, mapper);
            preparedDefaultTypeMappings.put(Long.class, mapper);
            preparedDefaultTypeMappings.put(Short.class, mapper);
            preparedDefaultTypeMappings.put(String.class, mapper);
            preparedDefaultTypeMappings.put(UUID.class, mapper);
        }
        return Collections.unmodifiableMap(preparedDefaultTypeMappings);
    }
}
