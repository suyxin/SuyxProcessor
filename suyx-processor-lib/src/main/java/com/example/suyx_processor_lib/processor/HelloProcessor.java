package com.example.suyx_processor_lib.processor;

import com.example.suyx_annotation_lib.annotation.Hello;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Created by suyxin on 2018/3/3.
 */
//@AutoService(Processor.class)
public class HelloProcessor extends BaseProcessor {

    private List<Element> annotationElements = new ArrayList<>();

    @Override
    public void processCollectInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Hello.class)) {

            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        Hello.class.getSimpleName());
                return;

            }
            annotationElements.add(annotatedElement);

        }
    }

    @Override
    public void processWriteToFile() {
        for (Element classElement : annotationElements) {
            genarateClassFile(classElement);
        }
    }

    private static final String SUFFIX = "$$TestHello";
    private static final String PACKAGE = "com.example.suyxin.suyxprocessor";

    private void genarateClassFile(Element classElement) {
        Hello annotation = classElement.getAnnotation(Hello.class);
        String name = annotation.name();
        String text = annotation.text();

//        TypeElement superClassName = mElementUtils.getTypeElement(name);
        String newClassName = name + SUFFIX;

        StringBuilder builder = new StringBuilder()
                .append("package " + PACKAGE + ";\n\n")
                .append("public class ")
                .append(newClassName)
                .append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append(text).append(name).append(" !\\n");


        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class


        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }

        info(">>> genarateClassFile is finish... <<<");


    }

    @Override
    public Set<String> supportedAnnotationTypes() {
        Set<String> sets = new LinkedHashSet<>();
        sets.add(Hello.class.getCanonicalName());
        return sets;
    }
}
