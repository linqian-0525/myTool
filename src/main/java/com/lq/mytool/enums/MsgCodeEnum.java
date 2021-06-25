package com.lq.mytool.enums;

import lombok.Getter;


public enum MsgCodeEnum {

    SUCCESS(0, "成功"),

    FAILED(1, "操作失败")
    ;

    @Getter
    private Integer code;
    @Getter
    private String msg;

    MsgCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

