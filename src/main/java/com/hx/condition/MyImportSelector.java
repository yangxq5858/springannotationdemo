package com.hx.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yxqiang
 * @create 2018-09-24 16:28
 */
public class MyImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.hx.bean.Blue","com.hx.bean.Yellow"};
    }
}
