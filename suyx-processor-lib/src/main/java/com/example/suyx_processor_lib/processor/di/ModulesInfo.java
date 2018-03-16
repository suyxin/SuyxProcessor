package com.example.suyx_processor_lib.processor.di;

import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;


/**
 * @author Created by suyxin on 2018/3/16 15:13.
 */


public class ModulesInfo {
    //@modules标注的类
    private TypeElement modulesElement;
    //@modules标注的类中@provider标识的方法,key 是方法返回值的类型字符串
    private Map<String,ExecutableElement> methodElements;

    public TypeElement getModulesElement() {
        return modulesElement;
    }

    public void setModulesElement(TypeElement modulesElement) {
        this.modulesElement = modulesElement;
    }

    public Map<String, ExecutableElement> getMethodElements() {
        return methodElements;
    }

    public void setMethodElements(Map<String, ExecutableElement> methodElements) {
        this.methodElements = methodElements;
    }
}
