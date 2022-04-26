package vip.lovek.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor

/**
 * author： yuzhirui@douban.com
 * date: 2022-04-24 10:15
 */
class TimerClassVisitor extends ClassVisitor {
    ClassWriter writer
    String className

    TimerClassVisitor(int i, ClassWriter writer) {
        super(i, writer)
        this.writer = writer
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name
        super.visit(version, access, name, signature, superName, interfaces)
//        println("visit start: " + name)
    }

    @Override
    void visitEnd() {
        super.visitEnd()
//        println("visit end: ")
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        println("method name: " + name + " descriptor: " + descriptor + " signature: " + signature)
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return new TimerMethodVisitor(api, methodVisitor, access, name, descriptor, className)
    }

//    @Override
//    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//        println("visitAnnotation: " + descriptor)
//        if ("Lvip/lovek/annotation/AsmTimer;" == descriptor) {
//            AnnotationVisitor av = cv.visitAnnotation(descriptor, visible)
//            return new TimerAnnotationVisitor(av)
//        }
//        return super.visitAnnotation(descriptor, visible)
//    }

}
