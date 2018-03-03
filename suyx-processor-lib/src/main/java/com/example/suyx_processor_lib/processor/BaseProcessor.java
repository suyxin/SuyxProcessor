package com.example.suyx_processor_lib.processor;


import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by suyxin on 2018/3/3.
 */

public abstract class BaseProcessor extends AbstractProcessor {

    protected Elements mElementUtils;

    protected Types mTypeUtils;
    /**
     * 创建文件
     */
    protected Filer mFiler;
    /**
     * 注解特殊时期专有的日记打印类
     */
    protected Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        collectInfo(set, roundEnvironment);
        writeToFile();
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {


        return supportedAnnotationTypes();
    }

   public abstract void collectInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment);
    public abstract void  writeToFile();

    public abstract  Set<String> supportedAnnotationTypes();


    protected void info(String s) {
        mMessager.printMessage(
                Diagnostic.Kind.NOTE,//日志等级
                s);
    }

    protected void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,//日志等级
                String.format(msg, args),
                e);
    }
}
