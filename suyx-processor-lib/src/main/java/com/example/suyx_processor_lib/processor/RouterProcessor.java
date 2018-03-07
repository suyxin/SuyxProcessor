package com.example.suyx_processor_lib.processor;

import com.example.suyx_annotation_lib.annotation.Path;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author Created by suyxin on 2018/3/7 14:27.
 */

@AutoService(Processor.class)
public class RouterProcessor extends BaseProcessor {
    private Map<String, String> map = new HashMap<>();

    @Override
    public void processCollectInfo(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements=  roundEnvironment.getElementsAnnotatedWith(Path.class);
        for (Element element : elements) {
            String path = element.getAnnotation(Path.class).value();
            if (path != null && path != "") {
                TypeElement typeElement = (TypeElement) element;
                map.put(path, typeElement.asType().toString());
            }
        }
   
    }

    private static final String PACKAGE = "com.example.suyxin.router";
    private static final String CLASS_SIMPLE_NAME = "Router$$Group";
    @Override
    public void processWriteToFile() {
        String classFullName = PACKAGE + CLASS_SIMPLE_NAME;

        StringBuilder builder = new StringBuilder()
                .append("package " + PACKAGE + ";\n\n")
                .append("import java.util.HashMap;\n" +
                        "import java.util.Map;\n")
                .append("public class ")
                .append(CLASS_SIMPLE_NAME)
                .append(" {\n\n") // open class
                .append("\tpublic Map<String,String> buildMap() {\n") // open method
                .append("\t\tMap<String, String> map = new HashMap<>();\n");

        for (Map.Entry<String, String> entry :map.entrySet()){
            String str = "\t\tmap.put(\"%s\",\"%s\");\n";
            builder.append(String.format(str, entry.getKey(), entry.getValue()));
        }
          builder.append("\t\treturn map;\n")
                .append("\t}\n") // close method
                .append("}\n"); // close class


        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(PACKAGE + "." + CLASS_SIMPLE_NAME);
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

    @Override
    public Set<String> supportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Path.class.getName());
        return set;
    }
}
