package com.muban.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager

import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class PageTransform extends Transform {

    /**
     * 执行这个Transform的task时，会以这个名字为基础生成task名称
     * (比如这里的任务是Task :app:transformClassesWithPageTransformForDebug)
     */
    @Override
    String getName() {
        return "PageTransform"
    }

    /**
     * 表示要处理的数据类型是什么，CLASSES 表示要处理编译后的字节码(可能是 jar 包也可能是目录)，
     * RESOURCES 表示要处理的是标准的 java 资源
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 表示Transform 的作用域，这里设置的SCOPE_FULL_PROJECT代表作用域是全工程
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 表示是否支持增量编译，false不支持
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 在这个方法中获取到文件输入源，对里面的class文件做一些自定义的修改后，
     * 将文件复制到指定目录中做为下一个Transform的输入源，多个Transform之间是一条执行链，
     * 上一个Transform的输出作为下一个Transform的输入，由于transform方法中的父类是空实现，
     * 所以当自定义一个Transform时，就算对class不进行任何操作，
     * 在这个方法中仍然要将文件复制到指定目录作为下一个Transform的输入，
     * 如果不做复制操作直接调用super方法，那整个执行链将断开而导致程序异常
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        //
        Collection<TransformInput> inputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        //
        inputs.each {
            TransformInput transformInput ->

                //Gradle3.6.0之后需要单独复制jar包
                transformInput.jarInputs.each { JarInput jarInput ->
                    File jarFile = jarInput.file
                    def destJar = outputProvider.getContentLocation(jarInput.name,
                            jarInput.contentTypes,
                            jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(jarFile, destJar)
                }

                //操作class文件
                transformInput.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        File dir = directoryInput.file
                        if (dir) {
                            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->

                                //读取和解析class文件
                                ClassReader classReader = new ClassReader(file.bytes)
                                //对class文件的写入
                                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                //访问class文件的内容
                                ClassVisitor classVisitor = new PageClassVisitor(classWriter)
                                //调用classVisitor的各个方法
                                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)

                                //将修改的字节码以byte数组返回
                                byte[] bytes = classWriter.toByteArray()
                                //通过文件流写入方式覆盖原先的内容，完成class文件的修改
                                FileOutputStream outputStream = new FileOutputStream(file.path)
                                outputStream.write(bytes)
                                outputStream.close()
                            }
                        }

                        //处理完输入文件后，把输出传递给下一个transform
                        def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                        FileUtils.copyDirectory(directoryInput.file, destDir)
                }
        }
    }
}