package com.example.frontend.model;

public class Chat {
    private Integer chatId;
    private Integer user1Id;
    private Integer user2Id;
    private Integer status;



    // Setters Y Getters generados para los demas atributos de la clase Chat
    public Integer getChatId() {
        return chatId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Integer user1Id) {
        this.user1Id = user1Id;
    }

    public Integer getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Integer user2Id) {
        this.user2Id = user2Id;
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
        return "Chat{" +
                "chatId=" + chatId +
                ", profile1Id=" + user1Id +
                ", profile2Id=" + user2Id +
                ", status=" + status +
                '}';
    }
}
