package com.sky.storage.influx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPropertyAnnotation {

    private Class<?> clazz;

    private Class<? extends Annotation> annotation;

}
