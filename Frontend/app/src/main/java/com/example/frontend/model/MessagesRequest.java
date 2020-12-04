package com.example.frontend.model;

public class MessagesRequest {
    private String userName;
    private String dateMessage;
    private String content;

    public MessagesRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessagesRequest{" +
                "userName='" + userName + '\'' +
                ", dateMessage='" + dateMessage + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
