package com.muban.plugin

import org.objectweb.asm.MethodVisitor
import groovyjarjarasm.asm.Opcodes

class PageMethodVisitor extends MethodVisitor {

    //当前的类名
    private String className

    //当前的方法名
    private String methodName

    PageMethodVisitor(MethodVisitor methodVisitor) {
        super(Opcodes.ASM5, methodVisitor)
        this.className = className
        this.methodName = methodName
    }

    @Override
    void visitCode() {
        super.visitCode()
        println("visitCode")
        //在这里插入自己的字节码指令
        //可以插入页面打开关闭的打点上报代码，这里用log打印代替了
//        mv.visitLdcInsn("Page_TAG");//可以用来过滤log日志的tag
//        mv.visitLdcInsn(className + "--->" + methodName);//插入要打印的内容
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(Opcodes.POP);
    }
}