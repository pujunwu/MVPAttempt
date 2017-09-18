package com.junwu.mvplibrary.http;

import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * ===============================
 * 描    述：OkHttp请求和结果打印
 * 作    者：
 * 创建日期：
 * ===============================
 */
public class LoggerInterceptor implements Interceptor {

    public LoggerInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求前的log
        logRequest(request);
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception exception) {
            throw exception;
        }
        //打印响应结果
        printResult(request, originalResponse);
        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param request
     * @param originalResponse
     * @return
     * @throws IOException
     */
    @Nullable
    private String printResult(Request request, Response originalResponse) throws IOException {
        ResponseBody responseBody = originalResponse.body();
        String bodyString = null;
        boolean hasRequestBody = request.body() != null;
        Buffer requestbuffer = new Buffer();
        if (hasRequestBody) {
            request.body().writeTo(requestbuffer);
        }

        if (isParseable(responseBody)) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            String encoding = originalResponse.headers().get("Content-Encoding");
            Buffer clone = buffer.clone();
            bodyString = parseContent(responseBody, encoding, clone);

            Logger.json("{\"Url\":\"" + request.url().toString() + "\","
                    + "\"Method\":\"" + request.method() + "\","
                    + "\"Params\":\"" + (hasRequestBody ? parseParams(request.body(), requestbuffer).replaceAll("&", "  ") : "Null") + "\","
                    + "\"Headers\":\"" + request.headers().toString() + "\","
                    + "\"Response\":" + (isJson(responseBody) ? bodyString + "}" : bodyString + "\"}"));
        } else {
            Logger.e("Url: " + request.url().toString() + "\n"
                    + "Method: " + request.method() + "\n"
                    + "Params: " + (hasRequestBody ? parseParams(request.body(), requestbuffer).replaceAll("&", "  ") : "null") + "\n"
                    + "Headers: " + request.headers().toString()
                    + "Error: " + ((responseBody.contentLength() == 0) ? "ResponseBody ContentLength is Zero" : ""));
        }
        return bodyString;
    }

    private void logRequest(Request request) throws IOException {
        Buffer requestbuffer = new Buffer();
        boolean hasRequestBody = request.body() != null;
        if (hasRequestBody) {
            request.body().writeTo(requestbuffer);
        }
        Logger.e("Url: " + request.url().toString() + "\n"
                + "Method: " + request.method() + "\n"
                + "Params: " + (hasRequestBody ? parseParams(request.body(), requestbuffer).replaceAll("&", "  ") : "null") + "\n"
                + "Headers: " + request.headers().toString());
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            return ZipUtil.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipUtil.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content没有被压缩
            return clone.readString(charset);
        }
    }

    private String parseParams(RequestBody body, Buffer requestbuffer) throws UnsupportedEncodingException {
        if (body.contentType() == null) return "Unknown";
        if (!body.contentType().toString().contains("multipart")) {
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset));
        }
        return "This Params isn't Text";
    }

    private boolean isParseable(ResponseBody responseBody) {
        if (responseBody.contentLength() == 0) return false;
        return responseBody.contentType().toString().contains("text") || isJson(responseBody);
    }

    private boolean isJson(ResponseBody responseBody) {
        return responseBody.contentType().toString().contains("json");
    }

    private String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }
}
