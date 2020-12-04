package com.example.frontend.model;

import java.util.Date;

public class Messages {
    private Integer messageId;
    private Integer chatId;
    private String date;
    private String content;
    private Integer receiverUserId;
    private Integer transmitterUserId;
    private Integer status;

    // Setters Y Getters generados para los demas atributos de la clase Messages
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Integer getTransmitterUserId() {
        return transmitterUserId;
    }

    public void setTransmitterUserId(Integer transmitterUserId) {
        this.transmitterUserId = transmitterUserId;
    }

    public Integer getChatId() {
        return chatId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    // Funcion toString generado para imprimir el objeto en una cadena String

    @Override
    public String toString() {
        return "Messages{" +
                "messageId=" + messageId +
                ", chatId=" + chatId +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", receiverUserId=" + receiverUserId +
                ", transmitterUserId=" + transmitterUserId +
                ", status=" + status +
                '}';
    }
}
