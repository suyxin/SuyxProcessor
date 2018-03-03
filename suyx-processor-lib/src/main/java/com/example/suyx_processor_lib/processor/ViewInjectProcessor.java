package com.example.suyx_processor_lib.processor;

import com.example.suyx_annotation_lib.annotation.Hello;
import com.example.suyx_annotation_lib.annotation.ViewInject;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

/**
 * Created by suyxin on 2018/3/3.
 */


@AutoService(Processor.class)
public class ViewInjectProcessor extends BaseProcessor {

    Map<String, List<VariableInfo>> classMap = new HashMap<>();

    Map<String, TypeElement> classTypeElement = new HashMap<>();

    @Override
    public void processCollectInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        classMap.clear();
        classTypeElement.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ViewInject.class);
        for (Element e : elements) {
            int viewId = e.getAnnotation(ViewInject.class).value();
            VariableElement variableElement = (VariableElement) e;
            TypeElement hostClassType = (TypeElement) e.getEnclosingElement();
            String hostClassFullName = hostClassType.getQualifiedName().toString();

            classTypeElement.put(hostClassFullName, hostClassType);

            List<VariableInfo> variableInfos = classMap.get(hostClassFullName);
            if (variableInfos == null) {
                variableInfos = new ArrayList<>();
                classMap.put(hostClassFullName, variableInfos);
            }

            VariableInfo info = new VariableInfo();
            info.viewId = viewId;
            info.variableElement = variableElement;
            variableInfos.add(info);
        }

    }


    private static final String SUFFIX = "$$Injector";


    @Override
    public void processWriteToFile() {

        for (Map.Entry<String, TypeElement> entry :
                classTypeElement.entrySet()) {
            TypeElement typeElement = entry.getValue();
            //遍历每一个类
            String simpleName = typeElement.getSimpleName().toString();
            String fullName = typeElement.asType().toString();

            String PACKAGE = fullName.replace("." + simpleName, "");
            String newSimpleClassName = entry.getValue().getSimpleName() + SUFFIX;

            /**
             * package com.example.suyxin.suyxprocessor;

             import android.app.Activity;

             public class MainActivity$$Injector {

             public MainActivity$$Injector (com.example.suyxin.suyxprocessor.MainActivity activity) {

             activity.name = (android.widget.TextView)activity.findViewById(2131165253);
             activity.value = (android.widget.TextView)activity.findViewById(2131165311);

             }
             }
             */
            StringBuilder builder = new StringBuilder()
                    .append("package " + PACKAGE + ";\n\n")
                    .append("public class ")
                    .append(newSimpleClassName)
                    .append(" {\n\n");

            String constorStr = "\tpublic %s(%s activity) {\n";
            builder.append(String.format(constorStr, newSimpleClassName, fullName));// open method

            String findViewByIdStr = "\tactivity.%s = (%s)activity.findViewById(%s);\n";
            for (VariableInfo info : classMap.get(entry.getKey())) {

                builder.append(String.format(findViewByIdStr, info.variableElement.getSimpleName(), info.variableElement.asType().toString(), info.viewId));

            }


            builder.append("\t}\n") // close method
                    .append("}\n"); // close class


            try { // write the file
                JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + newSimpleClassName);
                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }

            info(">>> findViewById is finish... <<<");


        }


    }

    @Override
    public Set<String> supportedAnnotationTypes() {
        Set<String> sets = new LinkedHashSet<>();
        sets.add(Hello.class.getCanonicalName());
        return sets;
    }

    public class VariableInfo {
        int viewId;
        VariableElement variableElement;
    }

}
