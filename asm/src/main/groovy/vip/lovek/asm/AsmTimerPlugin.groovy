package vip.lovek.asm

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AsmTimerPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================")
        System.out.println("hello gradle plugin!")
        System.out.println("========================")
        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new TimerTransform())
    }
}
