package com.xdsty.api.config.annotation;

import com.xdsty.api.common.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PackageResultCollection implements ApplicationRunner {

    private static Set<String> packageResultClazz;

    private ApplicationContextHolder applicationContextHolder;

    @Autowired
    public void setApplicationContextHolder(ApplicationContextHolder applicationContextHolder) {
        this.applicationContextHolder = applicationContextHolder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        // 项目启动后寻找带有PackageResult注解的类
        String[] clazzArr = applicationContext.getBeanNamesForAnnotation(PackageResult.class);
        packageResultClazz = Stream.of(clazzArr).map(String::toLowerCase).collect(Collectors.toSet());
    }

    public static boolean isPackageResultClazz(String clazz) {
        return !CollectionUtils.isEmpty(packageResultClazz) && packageResultClazz.contains(clazz.toLowerCase());
    }


}