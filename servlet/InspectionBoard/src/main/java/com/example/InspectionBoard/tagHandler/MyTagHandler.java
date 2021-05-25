package com.example.InspectionBoard.tagHandler;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MyTagHandler extends TagSupport {

    private String langEn;
    private String langUa;

    public void setLangEn(String langEn) {
        this.langEn = langEn;
    }

    public void setLangUa(String langUa) {
        this.langUa = langUa;
    }

    @Override
    public int doStartTag() throws JspException {
        try{
            pageContext.getOut().write("<div>\n" +
                    "        <input type=\"radio\" id=\"langUa\"\n" +
                    "               name=\"lang\" value=\"UA\" checked>\n" +
                    "        <label for=\"langUa\">" + langUa + "</label>\n" +
                    "\n" +
                    "\n" +
                    "        <input type=\"radio\" id=\"langEn\"\n" +
                    "               name=\"lang\" value=\"EN\">\n" +
                    "        <label for=\"langEn\">" + langEn + "</label>\n" +
                    "    </div>");
        }catch (IOException ex){
            throw new JspException(ex);
        }
        return 0;
    }

}