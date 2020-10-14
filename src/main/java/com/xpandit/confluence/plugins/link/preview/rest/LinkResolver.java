package com.xpandit.confluence.plugins.link.preview.rest;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.ApplicationProperties;
import com.xpandit.confluence.plugins.link.preview.rest.bean.LinkResolverResponseBean;
import com.xpandit.confluence.plugins.link.preview.util.Util;

/**
 * The Class LinkResolver.
 */
@Path("/link")
public class LinkResolver {

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkResolver.class);

    /** The Constant HTTP_HEAD. */
    private static final String HTTP_HEAD = "HEAD";

    /** The application properties. */
    private ApplicationProperties applicationProperties;

    /** The servlet request. */
    @Context
    private HttpServletRequest servletRequest;

    /**
     * Instantiates a new link resolver.
     *
     * @param applicationProperties the application properties
     */
    public LinkResolver(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Searches the project for issues of type <code>issueType</code>.
     * 
     * @param url the url
     * @return the response
     */
    @GET
    @Path("resolve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveType(@QueryParam("urlSource") String url) {
        LOGGER.info("Input data: l: " + url);
        Response response = null;
        final Cookie cookie = extractCookie(servletRequest);
        HttpURLConnection.setFollowRedirects(true);
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            if (cookie != null) {
                con.setRequestProperty("Cookie", "JSESSIONID=" + cookie.getValue());
            }
            con.setRequestMethod(HTTP_HEAD);
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final String objType = con.getContentType();
                final String baseUrl = applicationProperties.getBaseUrl();
                LOGGER.info("BaseURL: " + baseUrl);
                if (con.getHeaderField(Util.X_FRAME_OPTION_HEADER_PARAM) != null
                        && !url.startsWith(baseUrl.substring(0, baseUrl.indexOf('/')))) {
                    LOGGER.info(Util.X_FRAME_OPTION_HEADER_PARAM + " :"
                            + con.getHeaderField(Util.X_FRAME_OPTION_HEADER_PARAM));
                    response = Response.ok(new LinkResolverResponseBean(url, "-1")).build();
                } else {
                    response = Response.ok(new LinkResolverResponseBean(url, objType)).build();
                }
                LOGGER.info("Object type: " + objType);

            } else {
                response = Response.status(Status.NOT_FOUND).build();
                LOGGER.info("Returning a 404 Http status code");
            }

        } catch (Exception e) {
            response = Response.status(Status.NOT_FOUND).build();
            LOGGER.info("Returning a 404 Http status code");
            LOGGER.error("An error has occurred while requesting url: " + url, e);
        } finally {
            LOGGER.info("Done.");
        }
        return response;
    }

    /**
     * Extract cookie.
     *
     * @param request the request
     * @return the cookie
     */
    private Cookie extractCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie != null && "JSESSIONID".equalsIgnoreCase(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
