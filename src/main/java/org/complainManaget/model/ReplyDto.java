package org.complainManaget.model;

public class ReplyDto {
    private int id;
    private String response;
    private String reply;

    public ReplyDto() {
    }

    public ReplyDto(int id, String response, String reply) {
        this.id = id;
        this.response = response;
        this.reply = reply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
