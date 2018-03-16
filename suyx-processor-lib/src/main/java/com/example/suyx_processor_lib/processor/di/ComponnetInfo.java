package com.example.suyx_processor_lib.processor.di;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @author Created by suyxin on 2018/3/16 11:30.
 */


public class ComponnetInfo {
    //Componnet 元素
    private TypeElement typeElement;


//    //依赖提供者
//    private String[] moduleNames;

//    //key是modules注解的类名
//    private Map<String,ModulesInfo> modules;

    //key是modules注解的类名
    private Map<String,TypeElement> modules;

    //key是注入对象的类名，值是要注入的变量列名
    private Map<String,List<VariableElement>> injectFieldMap;

    //key是提供对象的类名，值对应的方法
    private Map<String,ExecutableElement>  providerMap;




    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public Map<String, ExecutableElement> getProviderMap() {
        return providerMap;
    }

    public void setProviderMap(Map<String, ExecutableElement> providerMap) {
        this.providerMap = providerMap;
    }

    public Map<String, List<VariableElement>> getInjectFieldMap() {
        return injectFieldMap;
    }

    public void setInjectFieldMap(Map<String, List<VariableElement>> injectFieldMap) {
        this.injectFieldMap = injectFieldMap;
    }
}
