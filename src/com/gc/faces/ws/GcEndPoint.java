/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gc.faces.ws;

import com.gc.model.Console;
import com.gc.repository.ConsoleRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author 7oo
 */
@ServerEndpoint(value = "/gcEndPoint", encoders = {ConsoleEncoder.class})
public class GcEndPoint implements Serializable {

    static ArrayList<Session> sessions = new ArrayList<>();

    @Inject
    private ConsoleRepository consoleRepository;

    @OnMessage
    public void messageReceiver(final Session session, String message) {
        try {
            Console console = consoleRepository.run(message);
            session.getBasicRemote().sendObject(console);
        } catch (IOException ex) {
            Logger.getLogger(GcEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncodeException ex) {
            Logger.getLogger(GcEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnOpen
    public void onOpen(Session session) {

    }

    @OnClose
    public void onClose(Session session) {

    }

    public static ArrayList<Session> getSessions() {
        return sessions;
    }

    public static void setSessions(ArrayList<Session> sessions) {
        GcEndPoint.sessions = sessions;
    }

}
