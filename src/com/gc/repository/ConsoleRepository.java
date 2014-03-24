/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gc.repository;

import com.gc.faces.web.GcMB;
import com.gc.model.Console;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 7oo
 */
@Named
public class ConsoleRepository {

    private GroovyShell shell;
    private Script script;
    private File fout;
    private PrintStream psout;

    public Console run(String expression) {

        String result = "";
        String stackTrace = "";
        String output = "";
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

        } catch (Exception e) {
            stackTrace = e.getMessage();
            if (stackTrace.length() > 0) {

            }
        }
        output = setOuts(output);
        Console console = new Console(expression, output, result, stackTrace);
        console.setExpression(expression);
        console.setOutput(output);
        console.setResult(result);
        console.setStackTrace(stackTrace);
        System.out.println("RESULT OF EXPRESSION : " + console.getResult());
        return console;
    }

    public String setOuts(String output) {
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
        return output;
    }

}
