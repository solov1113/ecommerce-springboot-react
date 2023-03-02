package h1r0ku.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import h1r0ku.exceptions.RestApiException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {

    private String getStringFromStream(InputStream stream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(stream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        return buf.toString(StandardCharsets.UTF_8);
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        String message;
        Response.Body responseBody = response.body();
        try {
            message = getStringFromStream(responseBody.asInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new RestApiException(message);
    }
}
