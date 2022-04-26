package vip.lovek.arouter_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import vip.lovek.annotation.ARouter;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-31 17:50
 */
//@AutoService(Processor.class)
//@SupportedAnnotationTypes({"vip.lovek.arouter_anotation.ARouter"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ARouterProcessor extends AbstractProcessor {
    private Messager messager; // 打印日志
    private Elements elementTool; // 操作 Element 工具类
    //    private Types typeTool; // type（类信息）工具类
    private Filer filer; // 文件生成器
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementTool = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        moduleName = processingEnv.getOptions().get("moduleName");
        messager.printMessage(Diagnostic.Kind.NOTE, "init ARouterProcessor >>>>>>>>>>>>>>>>>>");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process >>>>>>>>>>>>>>>>>>");
        // 获取 ARouter 注解
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ARouter.class);
        if (elements.isEmpty()) return true;
        for (Element element : elements) {
            genClassFile(element);
        }
        return true; // false 不干活了 true 干完了
    }

    private void genClassFile(Element element) {
        // 类名
        String className = element.getSimpleName().toString();
        messager.printMessage(Diagnostic.Kind.NOTE, "className >>>>>>>>>>>>>>>>>>" + className);
        String finalClassName = className + "$$" + moduleName + "$$ARouter";
        /**
         public class MainActivity$$$$$ARouter {
         public static Class findTargetClass(String path) {
         return path.equals("/path/MainActivity") ? MainActivity : null;
         }
         }
         */
        ARouter aRouter = element.getAnnotation(ARouter.class);
        // 1. 方法
        MethodSpec methodSpec = MethodSpec.methodBuilder("findTargetClass")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, "path")
                .returns(Class.class)
                .addStatement("return path.equals($S) ? $T.class : null", aRouter.path(),
                        ClassName.get((TypeElement) element))
                .build();
        // 2. 类
        TypeSpec typeSpec = TypeSpec.classBuilder(finalClassName)
                .addMethod(methodSpec)
                .addModifiers(Modifier.PUBLIC)
                .build();
        // 3. 包
        String packageName = elementTool.getPackageOf(element).getQualifiedName().toString();
        JavaFile packageF = JavaFile.builder(packageName, typeSpec).build();
        try {
            packageF.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>> fail");
        }
    }

    private boolean checkRouterPath(RouterBean routerBean) {
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ARouter.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
