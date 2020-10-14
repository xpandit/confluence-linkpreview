package com.xpandit.confluence.plugins.link.preview;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.confluence.util.velocity.VelocityUtils;

// TODO: Auto-generated Javadoc
/**
 * Servlet that provide a page for render pdf using pdf.js engine.
 * 
 * @author <a href="mailto:jlatino@sapo.pt">Joel Latino</a>
 * @version $Revision: 666 $
 * 
 */
public class PDFViewerServlet extends HttpServlet {

    /** The serial Version. */
    private static final long serialVersionUID = -4288976820070053823L;

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("req", req);
        final String html = VelocityUtils.getRenderedTemplate("templates/PDFViewer.vm", paramMap);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(html);
        resp.getWriter().close();
    }

}
