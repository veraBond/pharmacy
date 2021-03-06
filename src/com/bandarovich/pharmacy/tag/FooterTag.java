package com.bandarovich.pharmacy.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The Class FooterTag.
 */
public class FooterTag extends TagSupport {
    
    /** The Constant FOOTER_MESSAGE. */
    private static final String FOOTER_MESSAGE = "verabond © 2019";
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
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
