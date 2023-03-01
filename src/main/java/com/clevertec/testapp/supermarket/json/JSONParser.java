package com.clevertec.testapp.supermarket.json;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.*;

public class JSONParser {
    public String objectWrite(Object object) throws IllegalAccessException {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        Class<?> parent = object.getClass().getSuperclass();
        for (Field field : parent.getDeclaredFields()) {
            jsonBuilder.append(fieldWriter(object, field));
        }
        if (parent.getDeclaredFields().length > 0) {
            jsonBuilder.append(",");
        }
        if (jsonBuilder.lastIndexOf(",") == jsonBuilder.length() - 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            jsonBuilder.append(fieldWriter(object, field));
        }
        if (jsonBuilder.lastIndexOf(",") == jsonBuilder.length() - 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String fieldWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder fieldBuilder = new StringBuilder();
        field.setAccessible(true);
        if (field.getType().equals(String.class) ||
                field.getType().equals(Character.class)) {
            fieldBuilder.append(stringWriter(object, field));
        } else if (ClassUtils.isPrimitiveOrWrapper(field.getType())) {
            fieldBuilder.append(primitiveWriter(object, field));
        } else if (field.getType().isArray()) {
            fieldBuilder.append(arrayWriter(object, field));
        } else {
            boolean collectOrMap = false;
            for (Class<?> c : field.getType().getInterfaces()) {
                if (c.equals(Map.class)) {
                    fieldBuilder.append(mapWriter(object, field));
                    collectOrMap = true;
                    break;
                } else if (c.equals(List.class) || c.equals(Queue.class) ||
                        c.equals(Set.class)) {
                    fieldBuilder.append(collectionWriter(object, field));
                    collectOrMap = true;
                    break;
                }
            }
            if (!collectOrMap) {
                fieldBuilder.append("\"" + field.getName() + "\":{" +
                        objectWrite(field.get(object)));
            }
        }
        return fieldBuilder.toString();
    }

    private String arrayWriter(Object object, Field field) throws IllegalAccessException {
        Object[] array = (Object[]) field.get(object);
        StringBuilder arrayBuilder = new StringBuilder();
        arrayBuilder.append("\"" + field.getName() + "\":[");
        for (int i = 0; i < array.length; i++) {
            Object elem = array[i];
            if (elem.getClass().equals(String.class)) {
                arrayBuilder.append("\"" + elem + "\",");
            } else if (ClassUtils.isPrimitiveOrWrapper(elem.getClass())) {
                arrayBuilder.append(elem + ",");
            } else {
                arrayBuilder.append(objectWrite(elem));
            }
        }
        if (arrayBuilder.lastIndexOf(",") == arrayBuilder.length() - 1) {
            arrayBuilder.deleteCharAt(arrayBuilder.length() - 1);
        }
        arrayBuilder.append("]");
        return arrayBuilder.toString();
    }

    private String collectionWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder collectionBuilder = new StringBuilder();
        collectionBuilder.append("\"" + field.getName() + "\":{");
        for (Object obj : (Collection<?>) field.get(object)) {
            if (obj.getClass().equals(String.class)) {
                collectionBuilder.append("\"" + obj + "\",");
            } else if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
                collectionBuilder.append(obj + ",");
            } else {
                collectionBuilder.append(objectWrite(obj));
            }
        }
        if (collectionBuilder.lastIndexOf(",") == collectionBuilder.length() - 1) {
            collectionBuilder.deleteCharAt(collectionBuilder.length() - 1);
        }
        collectionBuilder.append("}");
        return collectionBuilder.toString();
    }

    private String mapWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder mapBuilder = new StringBuilder();
        mapBuilder.append("\"" + field.getName() + "\":{");
        Map<?, ?> map = (Map<?, ?>) field.get(object);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            mapBuilder.append("\"" + entry.getKey() + "\":");
            if (!ClassUtils.isPrimitiveOrWrapper(entry.getValue().getClass())) {
                mapBuilder.append("\"");
            }
            mapBuilder.append(entry.getValue());
            if (!ClassUtils.isPrimitiveOrWrapper(entry.getValue().getClass())) {
                mapBuilder.append("\"");
            }
            mapBuilder.append(",");
        }
        if (mapBuilder.lastIndexOf(",") == mapBuilder.length() - 1) {
            mapBuilder.deleteCharAt(mapBuilder.length() - 1);
        }
        mapBuilder.append("},");
        return mapBuilder.toString();
    }


    private String primitiveWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder primitiveField = new StringBuilder(
                "\"" + field.getName() + "\":" + field.get(object) + ",");
        return primitiveField.toString();
    }

    private String stringWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder strigField = new StringBuilder(
                "\"" + field.getName() + "\":\"" + field.get(object) + "\",");
        return strigField.toString();
    }

}
