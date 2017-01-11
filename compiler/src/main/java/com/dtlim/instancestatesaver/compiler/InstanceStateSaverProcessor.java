package com.dtlim.instancestatesaver.compiler;

import com.dtlim.instancestatesaver.SaveInstanceState;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Created by amw on 1/11/2017.
 */


public class InstanceStateSaverProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(SaveInstanceState.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Collection<? extends Element> annotatedElements =
                roundEnv.getElementsAnnotatedWith(SaveInstanceState.class);

        StringBuilder builder = new StringBuilder()
                .append("package com.dtlim.instancestatesaver.generated;\n\n")
                .append("public class GeneratedClass { \n\n")
                .append("\tpublic String getMessage() {\n\n")
                .append("\t\treturn \"");


        for(Element element : annotatedElements) {
            if(element.getKind() != ElementKind.FIELD) {
                System.out.println("Element is not a field. " + element.getSimpleName().toString());
            }
            else {
                System.out.println("Element is a field. " + element.getSimpleName().toString());
                builder.append(element.getSimpleName() + "\\n");
            }
        }

        builder.append("\";\n");
        builder.append("\t}\n");
        builder.append("}\n");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.dtlim.instancestatesaver.generated.GeneratedClass");

            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
