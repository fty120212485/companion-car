package com.companioncar.dal.msg;

/**
 * @author fengtianyong
 * @date 2020/01/05
 */
public class ReturnMsgUtil<T> {

    private boolean success;

    private int code;

    private String msg;

    private T data;

    public ReturnMsgUtil(boolean success, int code, String msg, T data){
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ReturnMsgUtil success(Object data) {
        return new ReturnMsgUtil(Boolean.TRUE, ResponseCode.SUCCESS, "操作成功", data);
    }

    public static ReturnMsgUtil fail(int code, String msg){
        return new ReturnMsgUtil(Boolean.FALSE, code, msg, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
