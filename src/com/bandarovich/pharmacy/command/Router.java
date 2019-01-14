package com.bandarovich.pharmacy.command;

public class Router {
    public enum Type {
        FORWARD, REDIRECT
    }
    private Type type = Type.REDIRECT;
    private String page = JspPath.START_PAGE;

    public Router(){}

    public Type getType() {
        return type;
    }

    public void setForward() { type = Type.FORWARD; }

    public void setRedirect() {
        type = Type.REDIRECT;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setForward(String page){
        setForward();
        setPage(page);
    }

    public void setRedirect(String page){
        setRedirect();
        setPage(page);
    }
}
