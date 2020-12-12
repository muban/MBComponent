package com.muban.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

/**
 * 自定义分析插件
 */
class AnalysisPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("AnalysisPlugin...")
        //注册自定义的PageTransform
        def androidConfig = project.extensions.getByType(AppExtension)
        PageTransform transform = new PageTransform()
        androidConfig.registerTransform(transform)
    }
}