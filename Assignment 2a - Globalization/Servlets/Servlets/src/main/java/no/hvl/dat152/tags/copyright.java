package no.hvl.dat152.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

public class copyright extends SimpleTagSupport {
    private int since = 0;

    /**
     * Sets the tag attribute decimalNumber.
     *
     * @param since number given with radix 10
     */
    public final void setsince(final int since) {
        this.since = since;
    }

    @Override
    public final void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(since >= values[i]) {
                since -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        StringBuilder roman2 = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(year >= values[i]) {
                year -= values[i];
                roman2.append(romanLiterals[i]);
            }
        }

        StringWriter stringWriter = new StringWriter();
        JspFragment body = getJspBody();
        body.invoke(stringWriter);
        String bodyText = stringWriter.getBuffer().toString();


        out.println("\u00a9"+ roman.toString() + "-" + roman2.toString() + bodyText);
    }

}
