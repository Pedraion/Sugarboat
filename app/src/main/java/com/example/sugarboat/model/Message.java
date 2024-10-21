package com.example.sugarboat.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/***
 * Classe modelo de mensagem.
 * Define getters de cada valor recebido no construtor.
 */
public class Message {
    private String sender;
    private String message;
    private Integer image;
    private Date time;

    public Message(String sender, String message, Integer image, Date time) {
        this.sender = sender;
        this.message = message;
        this.image = image;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Integer getImage() {
        return image;
    }

    public String getTime() {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());

        return df.format(time);
    }
}
