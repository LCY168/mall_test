package com.example.mall.domain;

import java.io.Serializable;


public class ResponseResult<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String DEFAULT_FAIL_MESSAGE = "fail";
    private static final int DEFAULT_SUCCESS_CODE = 200;
    private static final int DEFAULT_FAIL_CODE = 500;

    public static ResponseResult okResult() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(DEFAULT_SUCCESS_CODE);
        responseResult.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return responseResult;
    }

    public static ResponseResult okResult(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(DEFAULT_SUCCESS_CODE);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(DEFAULT_SUCCESS_CODE);
        responseResult.setMessage(DEFAULT_SUCCESS_MESSAGE);
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult failResult(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(DEFAULT_FAIL_CODE);
        responseResult.setMessage(DEFAULT_FAIL_MESSAGE);
        return responseResult;
    }

    public static ResponseResult failResult(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(DEFAULT_FAIL_CODE);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult failResult(int code, String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    public ResponseResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}