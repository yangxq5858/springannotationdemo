package com.hx.AOP;


import org.springframework.stereotype.Component;

/**
 * @author yangxinqiang
 * @content
 * @create 2018-09-26 15:59
 */

@Component
public class MulCalcuator {
    public int div(int i,int j){
        return i / j;
    }
}
