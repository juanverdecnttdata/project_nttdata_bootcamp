package com.nttdata.entity;
/**
 * Entidad mensaje, usado para mostrarse en las peticiones rest
 */
public class Message {
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Message(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Message() {
    }
}
