package dev.barbz.httpserver.core.util;

/**
 * Available HTTP Status enumerator.
 */
public enum HttpStatus {
    /**
     * The request has succeeded. The information returned with the response
     * is dependent on the method used in the request, for example:
     * <p>
     * GET    an entity corresponding to the requested resource is sent in
     * the response;
     * <p>
     * HEAD   the entity-header fields corresponding to the requested
     * resource are sent in the response without any message-body;
     * <p>
     * POST   an entity describing or containing the result of the action;
     * <p>
     * TRACE  an entity containing the request message as received by the
     * end server.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.2.1">RFC2616 - 200</a>
     */
    OK(200),
    /**
     * The request has been fulfilled and resulted in a new resource being
     * created. The newly created resource can be referenced by the URI(s)
     * returned in the entity of the response, with the most specific URI
     * for the resource given by a Location header field. The response
     * SHOULD include an entity containing a list of resource
     * characteristics and location(s) from which the user or user agent can
     * choose the one most appropriate. The entity format is specified by
     * the media type given in the Content-Type header field. The origin
     * server MUST create the resource before returning the 201 status code.
     * If the action cannot be carried out immediately, the server SHOULD
     * respond with 202 (Accepted) response instead.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.2.2">RFC2616 - 201</a>
     */
    CREATED(201),
    /**
     * The server has fulfilled the request but does not need to return an
     * entity-body, and might want to return updated metainformation. The
     * response MAY include new or updated metainformation in the form of
     * entity-headers, which if present SHOULD be associated with the
     * requested variant.
     * <p>
     * If the client is a user agent, it SHOULD NOT change its document view
     * from that which caused the request to be sent. This response is
     * primarily intended to allow input for actions to take place without
     * causing a change to the user agent's active document view, although
     * any new or updated metainformation SHOULD be applied to the document
     * currently in the user agent's active view.
     * <p>
     * The 204 response MUST NOT include a message-body, and thus is always
     * terminated by the first empty line after the header fields.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.2.5">RFC2616 - 204</a>
     */
    NO_CONTENT(204),
    /**
     * The request requires user authentication. The response MUST include a
     * WWW-Authenticate header field containing a challenge
     * applicable to the requested resource. The client MAY repeat the
     * request with a suitable Authorization header field. If
     * the request already included Authorization credentials, then the 401
     * response indicates that authorization has been refused for those
     * credentials. If the 401 response contains the same challenge as the
     * prior response, and the user agent has already attempted
     * authentication at least once, then the user SHOULD be presented the
     * entity that was given in the response, since that entity might
     * include relevant diagnostic information. HTTP access authentication
     * is explained in "HTTP Authentication: Basic and Digest Access
     * Authentication"
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.2>RFC2616 - 401</a>
     */
    UNAUTHORIZED(401),
    /**
     * The server understood the request, but is refusing to fulfill it.
     * Authorization will not help and the request SHOULD NOT be repeated.
     * If the request method was not HEAD and the server wishes to make
     * public why the request has not been fulfilled, it SHOULD describe the
     * reason for the refusal in the entity.  If the server does not wish to
     * make this information available to the client, the status code 404
     * (Not Found) can be used instead.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.4">RFC2616 - 403</a>
     */
    FORBIDDEN(403),
    /**
     * The server has not found anything matching the Request-URI. No
     * indication is given of whether the condition is temporary or
     * permanent. The 410 (Gone) status code SHOULD be used if the server
     * knows, through some internally configurable mechanism, that an old
     * resource is permanently unavailable and has no forwarding address.
     * This status code is commonly used when the server does not wish to
     * reveal exactly why the request has been refused, or when no other
     * response is applicable.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.5">RFC2616 - 404</a>
     */
    NOT_FOUND(404),
    /**
     * The server encountered an unexpected condition which prevented it
     * from fulfilling the request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.5.1">RFC2616 - 500</a>
     */
    INTERNAL_SERVER_ERROR(500);

    /**
     * Status
     */
    public final Integer status;

    /**
     * Enum constructor with status code.
     *
     * @param status status number value
     */
    HttpStatus(Integer status) {
        this.status = status;
    }

    /**
     * Retrieve the HTTP Status from the status
     * code value.
     *
     * @param status status value (200)
     * @return HTTP Status
     */
    public static HttpStatus statusOfValue(Integer status) {
        for (HttpStatus httpStatus : values()) {
            if (httpStatus.status.equals(status)) {
                return httpStatus;
            }
        }
        return null;
    }
}
