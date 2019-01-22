package com.bandarovich.pharmacy.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FooterTag extends TagSupport {
    private static final String FOOTER_MESSAGE = "verabond © 2019";
    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(FOOTER_MESSAGE);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

}
