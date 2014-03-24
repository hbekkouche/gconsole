/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gc.model;

/**
 *
 * @author 7oo
 */
public class Console {

    private String expression;
    private String result;
    private String output;
    private String stackTrace;

    public Console() {
    }

    public Console(String expression, String result, String output, String stackTrace) {
        this.expression = expression;
        this.result = result;
        this.output = output;
        this.stackTrace = stackTrace;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getAsJson() {
        StringBuffer val = new StringBuffer("");
        val.append("{");
        val.append("result:'");
        val.append(result);
        val.append("', output:'");
        val.append(output);
        val.append("', stackTrace:'");
        val.append(stackTrace);
        val.append("'}");
        return val.toString();
    }
}
