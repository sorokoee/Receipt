package com.clevertec.testapp.supermarket.json;

import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.Field;
import java.util.*;

public class JSONParser {
    public String objectWrite(Object object) throws IllegalAccessException {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                jsonBuilder.append(stringWriter(object, field));
            } else if (ClassUtils.isPrimitiveOrWrapper(field.getType())) {
                jsonBuilder.append(primitiveWriter(object, field));
            } else if (field.getType().isArray()) {
                jsonBuilder.append(arrayWriter(object, field));
            } else {
                boolean collectOrMap=false;
                for (Class<?> c : field.getType().getInterfaces()) {
                    if (c.equals(Map.class)) {
                        jsonBuilder.append(mapWriter(object, field));
                        collectOrMap=true;
                        break;
                    } else if (c.equals(List.class)||c.equals(Queue.class)||
                            c.equals(Set.class)) {
                        jsonBuilder.append(collectionWriter(object, field));
                        collectOrMap=true;
                        break;
                    }
                }
                if (!collectOrMap){
                    jsonBuilder.append("\"" + field.getName() + "\": {\n" +
                            objectWrite(field.get(object)));
                }
            }
        }
        jsonBuilder.append("}\n");
        return jsonBuilder.toString();
    }

    private String arrayWriter(Object object, Field field) throws IllegalAccessException {
        Object[] array = (Object[]) field.get(object);
        StringBuilder arrayBuilder = new StringBuilder();
        arrayBuilder.append("\"" + field.getName() + "\": [\n");
        for (int i = 0; i < array.length; i++) {
            Object elem = array[i];
            if (elem.getClass().equals(String.class)) {
                arrayBuilder.append("\"" + elem + "\",\n");
            } else if (ClassUtils.isPrimitiveOrWrapper(elem.getClass())) {
                arrayBuilder.append(elem + ",\n");
            } else {
                arrayBuilder.append(objectWrite(elem));
            }
        }
        if (arrayBuilder.lastIndexOf(",") == arrayBuilder.length() - 2) {
            arrayBuilder.deleteCharAt(arrayBuilder.length() - 2);}
        arrayBuilder.append("]\n");
        return arrayBuilder.toString();
    }

    private String collectionWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder collectionBuilder = new StringBuilder();
        collectionBuilder.append("\"" + field.getName() + "\": {\n");
        for (Object obj : (Collection<?>) field.get(object)) {
            if (obj.getClass().equals(String.class)) {
                collectionBuilder.append("\"" + obj + "\",\n");
            } else if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
                collectionBuilder.append(obj + ",\n");
            } else {
                collectionBuilder.append(objectWrite(obj));
            }
        }
        if (collectionBuilder.lastIndexOf(",") == collectionBuilder.length() - 2) {
            collectionBuilder.deleteCharAt(collectionBuilder.length() - 2);}
        collectionBuilder.append("}\n");
        return collectionBuilder.toString();
    }

    private String mapWriter(Object object, Field field) throws IllegalAccessException {
        StringBuilder mapBuilder = new StringBuilder();
        mapBuilder.append("\"" + field.getName() + "\": {\n");
        Map<?, ?> map = (Map<?, ?>) field.get(object);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            mapBuilder.append("\"" + entry.getKey() + "\": ");
            if (!ClassUtils.isPrimitiveOrWrapper(entry.getValue().getClass())) {
                mapBuilder.append("\"");
            }
            mapBuilder.append(entry.getValue());
            if (!ClassUtils.isPrimitiveOrWrapper(entry.getValue().getClass())) {
                mapBuilder.append("\"");
            }

            mapBuilder.append(",\n");
        }

        if (mapBuilder.lastIndexOf(",") == mapBuilder.length() - 2) {
            mapBuilder.deleteCharAt(mapBuilder.length() - 2);}
        mapBuilder.append("},\n");
        return mapBuilder.toString();
    }


    private String primitiveWriter(Object object, Field field) throws IllegalAccessException {
        String stringField = "\"" + field.getName() + "\": " + field.get(object) + ",\n";
        return stringField;
    }

    private String stringWriter(Object object, Field field) throws IllegalAccessException {
        String strigField = "\"" + field.getName() + "\": \"" + field.get(object) + "\",\n";
        return strigField;
    }

}
