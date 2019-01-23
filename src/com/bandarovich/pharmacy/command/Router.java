/*
 * 
 */
package com.bandarovich.pharmacy.command;

/**
 * The Class Router.
 */
public class Router {
    
    /**
     * The Enum Type.
     */
    public enum Type {
        
        /** The forward. */
        FORWARD, 
        /** The redirect. */
        REDIRECT
    }
    
    /** The type. */
    private Type type = Type.REDIRECT;
    
    /** The page. */
    private String page = JspPath.START_PAGE;

    /**
     * Instantiates a new router.
     */
    public Router(){}

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the forward.
     */
    public void setForward() { type = Type.FORWARD; }

    /**
     * Sets the redirect.
     */
    public void setRedirect() {
        type = Type.REDIRECT;
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets the page.
     *
     * @param page the new page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Sets the forward.
     *
     * @param page the new forward
     */
    public void setForward(String page){
        setForward();
        setPage(page);
    }

    /**
     * Sets the redirect.
     *
     * @param page the new redirect
     */
    public void setRedirect(String page){
        setRedirect();
        setPage(page);
    }
}
