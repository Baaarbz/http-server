package dev.barbz.httpserver.core.common;

/**
 * HTTP Method available to process in the
 * HTTP Server.
 */
public enum HttpMethod {
    /**
     * The GET method means retrieve whatever information (in the form of an
     * entity) is identified by the Request-URI. If the Request-URI refers
     * to a data-producing process, it is the produced data which shall be
     * returned as the entity in the response and not the source text of the
     * process, unless that text happens to be the output of the process.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-9.3">RFC2616 - GET</a>
     */
    GET,
    /**
     * The POST method is used to request that the origin server accept the
     * entity enclosed in the request as a new subordinate of the resource
     * identified by the Request-URI in the Request-Line. POST is designed
     * to allow a uniform method to cover the following functions:
     * <p>
     * - Annotation of existing resources;
     * <p>
     * - Posting a message to a bulletin board, newsgroup, mailing list,
     * or similar group of articles;
     * <p>
     * - Providing a block of data, such as the result of submitting a
     * form, to a data-handling process;
     * <p>
     * - Extending a database through an append operation.
     * <p>
     * The actual function performed by the POST method is determined by the
     * server and is usually dependent on the Request-URI. The posted entity
     * is subordinate to that URI in the same way that a file is subordinate
     * to a directory containing it, a news article is subordinate to a
     * newsgroup to which it is posted, or a record is subordinate to a
     * database.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-9.5">RFC2616 - POST</a>
     */
    POST,
    /**
     * The PUT method requests that the enclosed entity be stored under the
     * supplied Request-URI. If the Request-URI refers to an already
     * existing resource, the enclosed entity SHOULD be considered as a
     * modified version of the one residing on the origin server. If the
     * Request-URI does not point to an existing resource, and that URI is
     * capable of being defined as a new resource by the requesting user
     * agent, the origin server can create the resource with that URI. If a
     * new resource is created, the origin server MUST inform the user agent
     * via the 201 (Created) response. If an existing resource is modified,
     * either the 200 (OK) or 204 (No Content) response codes SHOULD be sent
     * to indicate successful completion of the request. If the resource
     * could not be created or modified with the Request-URI, an appropriate
     * error response SHOULD be given that reflects the nature of the
     * problem. The recipient of the entity MUST NOT ignore any Content-*
     * (e.g. Content-Range) headers that it does not understand or implement
     * and MUST return a 501 (Not Implemented) response in such cases.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-9.6">RFC2616 - PUT</a>
     */
    PUT,
    /**
     * The DELETE method requests that the origin server delete the resource
     * identified by the Request-URI. This method MAY be overridden by human
     * intervention (or other means) on the origin server. The client cannot
     * be guaranteed that the operation has been carried out, even if the
     * status code returned from the origin server indicates that the action
     * has been completed successfully. However, the server SHOULD NOT
     * indicate success unless, at the time the response is given, it
     * intends to delete the resource or move it to an inaccessible
     * location.
     * <p>
     * A successful response SHOULD be 200 (OK) if the response includes an
     * entity describing the status, 202 (Accepted) if the action has not
     * yet been enacted, or 204 (No Content) if the action has been enacted
     * but the response does not include an entity.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-9.7">RFC2616 - DELETE</a>
     */
    DELETE;
}
