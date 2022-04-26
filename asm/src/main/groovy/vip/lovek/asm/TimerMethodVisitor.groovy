package vip.lovek.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * author： yuzhirui@douban.com
 * date: 2022-04-25 09:56
 */
class TimerMethodVisitor extends AdviceAdapter {
    boolean hasTraceLog = false
    String name
    String className
    int startId

    TimerMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String className) {
        super(api, methodVisitor, access, name, descriptor)
        this.name = name
        this.className = className
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        if (name == "<init>()") {
            return
        }
        if (hasTraceLog) {
            println("visit start: " + name)
            startId = newLocal(Type.LONG_TYPE)
            visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            visitVarInsn(LSTORE, startId)
        }
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        println("TimerMethodVisitor descriptor: " + descriptor)
        if (descriptor == "Lvip/lovek/annotation/AsmTimer;") {
            println("AsmTimer descriptor: " + descriptor)
            hasTraceLog = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        if (name == "<init>") {
            return
        }
        if (hasTraceLog) {
            println("end: ")
            def endId = newLocal(Type.LONG_TYPE)
            visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            visitVarInsn(LSTORE, endId)
            visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
            visitTypeInsn(NEW, "java/lang/StringBuilder")
            visitInsn(DUP)
            visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
            visitLdcInsn("[" + className + "] " + name + " executed time: ")
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            visitVarInsn(LLOAD, endId)
            visitVarInsn(LLOAD, startId)
            visitInsn(LSUB);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
            visitLdcInsn(" ms");
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
            visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
        }
    }

}
