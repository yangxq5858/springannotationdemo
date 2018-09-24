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
     * @param metadataReader ��ʾ��ǰ���ڶ�ȡ������Ϣ
     * @param metadataReaderFactory ���Զ�ȡ����������Ϣ�Ĺ���
     * @return
     * @throws IOException
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //��ȡ��ǰ���ע����Ϣ
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //��ȡ��ǰ�������Ϣ
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //��ȡ��ǰ�����Դ��Ϣ���������path��
        Resource resource = metadataReader.getResource();

        //����ʹ�õ�ǰ�������Ϣ������
        String className = classMetadata.getClassName();
        if (className.contains("er")){
            return true;
        }

        return false;
    }
}
