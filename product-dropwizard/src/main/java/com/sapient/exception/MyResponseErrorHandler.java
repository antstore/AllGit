package com.sapient.exception;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.couchbase.client.core.endpoint.kv.AuthenticationException;

public class MyResponseErrorHandler implements ResponseErrorHandler {

	private static final Log logger = LogFactory.getLog(MyResponseErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {

		if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
			logger.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
			throw new AuthenticationException();
		}
	}

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

		if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
			logger.debug("Status code: " + clienthttpresponse.getStatusCode());
			logger.debug("Response" + clienthttpresponse.getStatusText());
			logger.debug(clienthttpresponse.getBody());

			if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
				logger.debug("Call returned a error 403 forbidden resposne ");
				return true;
			}
		}
		return false;
	}
}