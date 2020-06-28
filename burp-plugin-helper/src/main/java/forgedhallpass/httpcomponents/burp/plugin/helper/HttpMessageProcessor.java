package forgedhallpass.httpcomponents.burp.plugin.helper;

import forgedhallpass.httpcomponents.burp.plugin.helper.request.model.BurpHttpRequest;

import java.util.List;

/**
 * Interface facilitating the communication between a Burp Plugin and a business logic that describes how or which HTTP messages should be processed
 */
public interface HttpMessageProcessor {

    /**
     * Process an HTTP message by applying different transformations, generating new HTTP message templates and marking dynamic content
     *
     * @param httpMessage an HTTP message that is either a request or a response
     * @return a list of {@link BurpHttpRequest} objects composed from newly generated HTTP messages and their payload insertion markers
     */
    List<BurpHttpRequest> process(final HttpMessage httpMessage) throws Exception;
}
