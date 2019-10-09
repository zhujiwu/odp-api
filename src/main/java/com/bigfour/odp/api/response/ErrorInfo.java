package com.bigfour.odp.api.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 错误信息相应
 *
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
@Data
@Builder
public class ErrorInfo implements Serializable {

    private String errorCode = "error";
    private String msg;

    public ErrorInfo(String msg) {
        this.msg = msg;
    }

    public ErrorInfo(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
