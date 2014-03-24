/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gc.faces.ws;

import com.gc.model.Console;
import java.io.StringWriter;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author 7oo
 */
public class ConsoleEncoder implements Encoder.Text<Console> {

    @Override
    public String encode(Console c) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonWriter jsonWrite = Json.createWriter(swriter)) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add(
                    "Console",
                    Json.createObjectBuilder()
                    .add("result", c.getResult())
                    .add("output", c.getOutput()).add("stackTrace", c.getStackTrace()));
            jsonWrite.writeObject(builder.build());

        }
        return swriter.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
