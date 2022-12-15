package no.hvl.dat152.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;


import java.io.IOException;
import java.io.StringWriter;

public class shorttext extends SimpleTagSupport {
    private int maxchars = 0;
    private String value = "";

    public final void setmaxchars(final int maxchars) {
        this.maxchars = maxchars;
    }
    public final void setvalue(final String value) {
        this.value = value;
    }

    public final void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        /*
        StringWriter stringWriter = new StringWriter();
        JspFragment body = getJspBody();
        body.invoke(stringWriter);
        String bodyText = stringWriter.getBuffer().toString();
        */
        String shorttxt = value;
        if(maxchars <= value.length()) {
            shorttxt = value.substring(0, maxchars) + "...";
        }
        out.println(shorttxt);
    }
}
