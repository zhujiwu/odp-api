package com.bigfour.odp.api.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 错误信息相应
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
@Data
public class ErrorInfo  implements Serializable {

    private String code;
    private String msg;

    public ErrorInfo(String code) {
        this.code = code;
    }

    public ErrorInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
