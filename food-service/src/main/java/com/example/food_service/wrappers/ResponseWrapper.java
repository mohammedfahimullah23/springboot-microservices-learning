package com.example.food_service.wrappers;


import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper<T> {
    private boolean success;
    private T result;
    private List<String> errors;

    // Constructor for success responses
    public ResponseWrapper(boolean success, T result) {
        this.success = success;
        this.result = result;
        this.errors = new ArrayList<>();
    }

    public ResponseWrapper(boolean success) {
        this.success = success;
        this.result = null;
        this.errors = new ArrayList<>();
    }

    // Constructor for error responses
    public ResponseWrapper(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
        this.result = null; // No result in case of error
    }

    public ResponseWrapper() {

    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
