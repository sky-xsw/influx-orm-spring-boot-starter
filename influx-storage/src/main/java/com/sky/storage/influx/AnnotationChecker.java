package com.sky.storage.influx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationChecker {


    private static final Map<ClassPropertyAnnotation, Field> CLASS_CHECK_MAP = new ConcurrentHashMap<>();

    public static void checkClassForAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (!clazz.isAnnotationPresent(annotationClass)) {
            throw new RuntimeException("In class " + clazz.getName() + ", must be annotated with @" + annotationClass.getName());
        }
    }

    public static Field checkFieldForAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {

        ClassPropertyAnnotation classPropertyAnnotation = new ClassPropertyAnnotation(clazz, annotationClass);

        Field targetField = CLASS_CHECK_MAP.computeIfAbsent(classPropertyAnnotation, k -> {

            Class<?> targetClass = k.getClazz();

            do {
                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(k.getAnnotation())) {
                        return field;
                    }
                }
                targetClass = targetClass.getSuperclass();
            }
            while (targetClass != null && targetClass != Object.class);

            return null;
        });

        if (targetField == null) {
            throw new RuntimeException("In class " + clazz.getName() + ", at least one field must be annotated with @" + annotationClass.getName());
        }
        return targetField;
    }


}
