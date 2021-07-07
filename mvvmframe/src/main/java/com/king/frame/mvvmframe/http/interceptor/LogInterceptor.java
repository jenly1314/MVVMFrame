package com.king.frame.mvvmframe.http.interceptor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import timber.log.Timber;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class LogInterceptor implements Interceptor {

    private static Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Timber.i(String.format("%1$s -> %2$s",request.method(),request.url()));

        if(request.headers() != null && request.headers().size() > 0){
            Timber.d("Headers:" + request.headers());
        }

        if(request.body() != null){
            RequestBody requestBody = request.body();
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            if(isPlaintext(buffer)){
                Timber.d("RequestBody:" + buffer.readString(getCharset(requestBody,UTF_8)));
            }else{
                Timber.d("RequestContentType:" + requestBody.contentType());
                Timber.d("RequestBody:(binary " + requestBody.contentLength() + "-byte body omitted)");
            }
        }

        Response response = chain.proceed(chain.request());
        ResponseBody responseBody = response.body();
        if(responseBody != null){
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();
            if ("gzip".equalsIgnoreCase(response.headers().get("Content-Encoding"))) {
                try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                }
            }
            if(isPlaintext(buffer)){
                Timber.d("ResponseBody:" + buffer.clone().readString(getCharset(responseBody,UTF_8)));
            }else{
                Timber.d("ResponseContentType:" + responseBody.contentType());
                Timber.d("ResponseBody:(binary " + buffer.size() + "-byte body omitted)");
            }
        }
        return response;
    }


    private Charset getCharset(RequestBody requestBody,Charset defaultCharset){
        return requestBody.contentType() != null ? requestBody.contentType().charset(defaultCharset) : defaultCharset;
    }

    private Charset getCharset(ResponseBody responseBody,Charset defaultCharset){
        Charset charset = responseBody.contentType() != null ? responseBody.contentType().charset(defaultCharset) : defaultCharset;
        try {
            return Util.bomAwareCharset(responseBody.source(),charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     *
     * @see <a href="https://github.com/square/okhttp">okhttp-logging-interceptor</a>
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
