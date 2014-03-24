/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gc.faces.web;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author bek.hocine@gmail.com
 */
@Named
@ViewScoped
public class GcMB implements Serializable {

    private static final long serialVersionUID = 42L;

    private String expression;
    private String result;
    private String output;
    private String stackTrace;
    private GroovyShell shell;
    private Script script;
    private File fout;
    private PrintStream psout;

    @PostConstruct
    public void init() {

    }

    public void run() {
        result ="";
        stackTrace="";
        output="";
        try {
            fout = File.createTempFile("out", ".out");
            psout = new PrintStream(fout);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GcMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GcMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.setErr(pserr);
        System.setOut(psout);
        shell = new GroovyShell();
        if (expression == null) {
            expression = "";
        }
        RequestContext rc = RequestContext.getCurrentInstance();
        try {
            // Chargement du script groovy
            script = shell.parse(expression);
            Object r = script.run();
            if (r == null) {
                result = "";
            } else {
                result = r.toString();
            }
            rc.execute("$('li').removeClass('active')");
            rc.execute("$('#p2').removeClass('active')");
            rc.execute("$('#hp1').addClass('active')");
            rc.execute("$('#p1').addClass('active')");

        } catch (Exception e) {
            stackTrace = e.getMessage();
            if (stackTrace.length() > 0) {
                rc.execute("$('li').removeClass('active')");
                rc.execute("$('#p1').removeClass('active')");
                rc.execute("$('#hp2').addClass('active')");
                rc.execute("$('#p2').addClass('active')");
            }
        }
        setOuts();
    }

    public void setOuts() {
        psout.flush();
        psout.close();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fout));
            String ln;
            while ((ln = br.readLine()) != null) {
                output += "> " + ln + "<br/>";
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GcMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GcMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        fout = null;
        psout = null;
        System.setOut(System.out);
    }

    /////////////////GETTERS & SETTERS//////////////////////
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

}
