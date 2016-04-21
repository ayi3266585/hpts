package com.queqianme.hpt.utils;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * EditText非空验证工具类
 */
public class EmptyValidator {
    private ArrayList<EditText> list;

    public EmptyValidator() {
        list = new ArrayList<EditText>();
    }

    public void add(EditText editText) {
        list.add(editText);
    }

    public void remove(EditText editText) {
        list.remove(editText);
    }

    public void clear(){
        list.clear();
    }

    public Boolean isEmpty() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().toString().trim().length() < 1) {
                return false;
            }
        }
        return true;
    }
}
