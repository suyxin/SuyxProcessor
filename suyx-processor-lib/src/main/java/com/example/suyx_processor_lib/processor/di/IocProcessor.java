package com.example.suyx_processor_lib.processor.di;

import com.example.suyx_annotation_lib.annotation.dependency_inject.Component;
import com.example.suyx_annotation_lib.annotation.dependency_inject.Inject;
import com.example.suyx_annotation_lib.annotation.dependency_inject.Module;
import com.example.suyx_annotation_lib.annotation.dependency_inject.Provider;
import com.example.suyx_processor_lib.processor.BaseProcessor;
import com.example.suyx_processor_lib.processor.utils.JavaFileBuilder;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;

/**
 * @author Created by suyxin on 2018/3/16 10:47.
 */

@AutoService(Processor.class)
public class IocProcessor extends BaseProcessor  {
    Set<TypeElement> mComponentElements = new HashSet<>();
    Set<TypeElement> mModuleElements = new HashSet<>();
    Set<ExecutableElement> mProviderElements = new HashSet<>();
    Set<VariableElement> mInjectElements = new HashSet<>();



    private List<ComponnetInfo> componnetInfoList = new ArrayList<>();

    @Override
    public void processCollectInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<TypeElement> componentElements = (Set<TypeElement>) roundEnvironment.getElementsAnnotatedWith(Component.class);
        Set<TypeElement> moduleElements = (Set<TypeElement>) roundEnvironment.getElementsAnnotatedWith(Module.class);
        Set<ExecutableElement> providerElements = (Set<ExecutableElement>) roundEnvironment.getElementsAnnotatedWith(Provider.class);
        Set<VariableElement> injectElements = (Set<VariableElement>) roundEnvironment.getElementsAnnotatedWith(Inject.class);

        if (componentElements != null && componentElements.size() > 0) {
            mComponentElements.addAll(componentElements);
        }
        if (moduleElements!= null && moduleElements.size() > 0) {
            mModuleElements.addAll(moduleElements);
        }
        if (providerElements!= null && providerElements.size() > 0) {
            mProviderElements.addAll(providerElements);
        }
        if (injectElements != null && injectElements.size() > 0) {
            mInjectElements.addAll(injectElements);
        }


    }

    @Override
    public void processWriteToFile() {

        Iterator<TypeElement>  componnetInfoIterator = mComponentElements.iterator();
        while (componnetInfoIterator.hasNext()) {

            TypeElement componnetElement = componnetInfoIterator.next();


            Component component = componnetElement.getAnnotation(Component.class);
            if (component == null) {
                info("Component 为空"+componnetElement.asType().toString());
                continue;
            }
            String[] moduleNames = component.modules();
            if (moduleNames == null) {
                info("ModuleNames 为空" + componnetElement.asType().toString());
                continue;
            }
//            //key为方法返回类名,值为提供对象的方法
            Map<String, ExecutableElement> providerMap = new HashMap<>();
            for (int i = 0; i < moduleNames.length; i++) {
                String moduleName = moduleNames[i];
                for (ExecutableElement executableElement:mProviderElements) {
                    //所属类
                    String tempModuleName = executableElement.getEnclosingElement().asType().toString();
                    if (tempModuleName.equals(moduleName)) {
                        info("找到一个提供方法 key =" + executableElement.getReturnType().toString() + "value = " + executableElement.asType().toString());
                        providerMap.put(executableElement.getReturnType().toString(), executableElement);
                    }
                }
            }

            //是方法体或者构造体
            JavaFileBuilder fileBuilder = new JavaFileBuilder();
            String pckName = componnetElement.asType().toString().
                    replace("." + componnetElement.getSimpleName(), "");
            info("包名是 " + pckName);
            fileBuilder.append("package %s;\n",pckName);
            fileBuilder.append("public class %s%s implements %s{\n", Consts.COMPONNET_IMPL_PREFIX,
                    componnetElement.getSimpleName(), componnetElement.getSimpleName());


            List<? extends Element> list = componnetElement.getEnclosedElements();
            for (Element e : list) {

                if (e.asType().getKind().compareTo(TypeKind.EXECUTABLE) == 0) {

                    //key是注入对象的类名，值是要注入的变量列名
                    Map<String, List<VariableElement>> injectFieldMap = new HashMap<>();


                    info("组件下的每个执行元素 " + e.asType().toString());
                    ExecutableElement executableElement = (ExecutableElement) e;

                    List<VariableElement> params = (List<VariableElement>) executableElement.getParameters();
                    String returnTypeName = executableElement.getReturnType().toString();
                    VariableElement param = params.get(0);

                    info("参数:" + param.asType().toString());

                    fileBuilder.append("\t\t@Override\n");
                    fileBuilder.append("\t\tpublic void %s(%s %s){\n", executableElement.getSimpleName(),
                            param.asType().toString(), param.getSimpleName());
//
                    String hostName = param.asType().toString();

                    for (VariableElement injectElement : mInjectElements) {
                        //查找在这个对象里面的注入变量
                        String tempHostName = injectElement.getEnclosingElement().asType().toString();
                        if (tempHostName.equals(hostName)) {

                            List<VariableElement> listInject = injectFieldMap.get(hostName);
                            if (listInject == null) {
                                listInject = new ArrayList<>();

                            }
                            listInject.add(injectElement);
                            injectFieldMap.put(hostName, listInject);
                        }
                    }

                    //遍历在这个对象里面的注入变量，完成注入 instance.f = new modules().provider();
                    for (VariableElement injectE : injectFieldMap.get(hostName)) {
                        if (injectE == null) {
                            continue;
                        }
                        fileBuilder.append("\t\t%s.%s = new %s().%s();\n",
                                param.getSimpleName(),
                                injectE.getSimpleName(),
                                providerMap.get(injectE.asType().toString()).getEnclosingElement().asType().toString(),
                                providerMap.get(injectE.asType().toString()).getSimpleName()
                        );

                    }

                    fileBuilder.append("\t\t}\n");



                }


            }







            fileBuilder.append("}\n");
            try { // write the file
                JavaFileObject source = mFiler.createSourceFile(pckName+ "." + Consts.COMPONNET_IMPL_PREFIX+
                        componnetElement.getSimpleName());
                Writer writer = source.openWriter();
                writer.write(fileBuilder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }
        }




    }


    @Override
    public Set<String> supportedAnnotationTypes() {
        Set<String> sets = new HashSet<>();
        sets.add(Module.class.getName());
        sets.add(Component.class.getName());
        sets.add(Provider.class.getName());
        sets.add(Inject.class.getName());
        return sets;
    }
}
