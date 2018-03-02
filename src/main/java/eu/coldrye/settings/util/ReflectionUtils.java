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

import eu.coldrye.settings.impl.accessor.Accessor;
import eu.coldrye.settings.impl.accessor.PropertyAccessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ReflectionUtils {

  public static List<Field> collectAnnotatedFields(Class<?> type, Class<? extends Annotation> annotation) {

    List<Field> result = new ArrayList<>();

    Class<?> current = type;
    while (!Object.class.equals(current)) {

      Field[] fields = current.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(annotation)) {
          result.add(field);
        }
      }
      current = current.getSuperclass();
    }

    return result;
  }

  public static String getGetterName(Field field) {

    return "get" + capitalizeFieldName(field);
  }

  public static String getSetterName(Field field) {

    return "set" + capitalizeFieldName(field);
  }

  public static String capitalizeFieldName(Field field) {

    String fieldName = field.getName();
    return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  @SuppressWarnings("unchecked")
  public static <T> T newInstance(Class<? extends T> type) {

    try {
      Constructor constructor = type.getConstructor();
      return (T) constructor.newInstance();
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Unable to instantiate the specified type: " + type, e);
    }
  }

  /**
   * Gets the method specified by {@code methodName} for the optionally
   * specified {@code parameterTypes} from the specified {@code visitee}'s
   * declaring class.
   *
   * @param field
   * @param methodName
   * @param parameterTypes
   * @return the method
   */
  public static Method getMethod(Field field, String methodName, Class<?>... parameterTypes) {

    try {
      Method method = field.getDeclaringClass().getMethod(methodName, parameterTypes);
      if (!Modifier.isPublic(method.getModifiers())) {
        throw new RuntimeException("method must be public " + method);
      }
      return method;
    } catch (NoSuchMethodException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static boolean isMethodAvailable(Class<?> type, String methodName, Class<?>... parameterTypes) {

    try {
      type.getMethod(methodName, parameterTypes);
      return true;
    } catch (NoSuchMethodException ex) {
      return false;
    }
  }

  public static boolean isMutableField(Field field) {

    Class<?> type = field.getDeclaringClass();
    Class<?> fieldType = field.getType();
    return isMethodAvailable(type, getSetterName(field), fieldType);
  }

  public static Constructor getPublicDefaultConstructorWithNoArguments(Class<?> type) {

    try {
      Constructor constructor = type.getConstructor();
      if (!Modifier.isPublic(constructor.getModifiers())) {
        throw new RuntimeException("default constructor must be public on " + type);
      }
      return constructor;
    } catch (NoSuchMethodException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void invokeSetter(Method method, Object instance, Object value) {

    invokeMethod(method, instance, value);
  }

  public static Object invokeGetter(Method method, Object instance, DefaultValueHolder defaultValueHolder) {

    Object result = invokeMethod(method, instance);
    if (Objects.isNull(result) && !Objects.isNull(defaultValueHolder)) {
      result = defaultValueHolder.getValue();
    }
    return result;
  }

  public static Object invokeMethod(Method method, Object instance, Object... parameters) {

    try {
      return method.invoke(instance, parameters);
    } catch (IllegalAccessException | InvocationTargetException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static Object getValue(PropertyAccessor accessor, Object settingsRoot) {

    Accessor parentAccessor = accessor.getParentAccessor();
    Object valueHolder = parentAccessor.getValue(settingsRoot);
    Method getter = accessor.getGetter();
    if (Objects.isNull(getter)) {
      return valueHolder;
    }
    return invokeGetter(getter, valueHolder, accessor.getDefaultValueHolder());
  }

  public static void setValue(PropertyAccessor accessor, Object value, Object settingsRoot) {

    Accessor parentAccessor = accessor.getParentAccessor();
    Object valueHolder = parentAccessor.getValue(settingsRoot);
    Method setter = accessor.getSetter();
    if (Objects.isNull(setter)) {
      throw new IllegalStateException("no setter available");
    }
    invokeSetter(setter, valueHolder, value);
  }

  /**
   * Must not be instantiated.
   */
  private ReflectionUtils() {

  }
}
