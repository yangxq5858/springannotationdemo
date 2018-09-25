package com.hx.Repository;

import org.springframework.stereotype.Repository;

/**
 * @author yxqiang
 * @create 2018-09-23 21:57
 */

@Repository
public class BookDao {

    private String label = "default 1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public BookDao() {
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
