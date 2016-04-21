package com.queqianme.hpt.bean;

/**
 * EventBus非空验证事件
 * Created by zhaojiayu on 16/3/9.
 */
public class ValidatorEvent {
    private Boolean AllValid;

    public ValidatorEvent(Boolean AllValid) {
        this.AllValid = AllValid;
    }
    public Boolean getMsg(){
        return AllValid;
    }
}
