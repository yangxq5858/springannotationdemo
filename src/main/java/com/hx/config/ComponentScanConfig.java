package com.hx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

/**
 * @author yxqiang
 * @create 2018-09-24 12:03
 */

//@ComponentScan //��@Controller @Repository @Service @Component ����ɨ�����
@ComponentScan(value = "com.hx",
//        excludeFilters = {
//        //��ʾ���˹���Ϊ���ų�����Ϊע�����͵ģ��� = Controller��
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//},
        //��ʾ ֻ����������
        includeFilters = {
                //��ʾ���˹���Ϊ������Ϊע�����͵ģ��� = Controller��
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class}),
                //��ʾ��������Ϊ ָ������
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.hx.com.hx.controller.BookController.class}),
                //���Զ������ ����
                @ComponentScan.Filter(type= FilterType.CUSTOM,classes = {MyFilterTypeImp.class})

        },
        useDefaultFilters = false //���������Ĭ��Ϊtrue����ʾȫ��ɨ�裬����ֻ����ʱ��Ҫ�رմ˲�������Ч
)
public class ComponentScanConfig {
}
