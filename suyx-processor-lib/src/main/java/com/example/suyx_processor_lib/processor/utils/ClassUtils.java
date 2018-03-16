package com.example.suyx_processor_lib.processor.utils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * @author Created by suyxin on 2018/3/16 15:58.
 */


public class ClassUtils {
    public static String getClassName(Class<?> clazz) {
        String qualifiedSuperClassName = "";
        try {
            qualifiedSuperClassName = clazz.getCanonicalName();

        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
//            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
        return qualifiedSuperClassName;
    }
}
