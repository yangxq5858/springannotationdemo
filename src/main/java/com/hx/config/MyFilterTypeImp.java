package com.hx.config;

import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author yxqiang
 * @create 2018-09-24 12:57
 */

public class MyFilterTypeImp implements TypeFilter {

    /**
     *
     * @param metadataReader 表示当前正在读取的类信息
     * @param metadataReaderFactory 可以读取到其他类信息的工厂
     * @return
     * @throws IOException
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源信息（比如类的path）
        Resource resource = metadataReader.getResource();

        //这里使用当前类的类信息来处理
        String className = classMetadata.getClassName();
        if (className.contains("er")){
            return true;
        }

        return false;
    }
}
