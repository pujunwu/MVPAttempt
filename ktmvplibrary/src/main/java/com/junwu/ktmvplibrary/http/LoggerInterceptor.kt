package com.junwu.ktmvplibrary.http

import com.orhanobut.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 10:55
 * ===============================
 */
class LoggerInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //打印请求前的log
        logRequest(request)
        val originalResponse: Response
        try {
            originalResponse = chain.proceed(request)
        } catch (exception: Exception) {
            throw exception
        }

        //打印响应结果
        printResult(request, originalResponse)
        return originalResponse
    }

    /**
     * 打印响应结果
     *
     * @param request
     * @param originalResponse
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun printResult(request: Request, originalResponse: Response): String? {
        val responseBody = originalResponse.body()
        var bodyString: String? = null
        val hasRequestBody = request.body() != null
        val requestbuffer = Buffer()
        if (hasRequestBody) {
            request.body().writeTo(requestbuffer)
        }

        if (isParseable(responseBody)) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()
            val encoding = originalResponse.headers().get("Content-Encoding")
            val clone = buffer.clone()
            bodyString = parseContent(responseBody, encoding, clone)

            Logger.json("{\"Url\":\"" + request.url().toString() + "\","
                    + "\"Method\":\"" + request.method() + "\","
                    + "\"Params\":\"" + (if (hasRequestBody) parseParams(request.body(), requestbuffer).replace("&".toRegex(), "  ") else "Null") + "\","
                    + "\"Headers\":\"" + request.headers().toString() + "\","
                    + "\"Response\":" + if (isJson(responseBody)) bodyString!! + "}" else bodyString!! + "\"}")
        } else {
            Logger.e("Url: " + request.url().toString() + "\n"
                    + "Method: " + request.method() + "\n"
                    + "Params: " + (if (hasRequestBody) parseParams(request.body(), requestbuffer).replace("&".toRegex(), "  ") else "null") + "\n"
                    + "Headers: " + request.headers().toString()
                    + "Error: " + if (responseBody.contentLength() == 0L) "ResponseBody ContentLength is Zero" else "")
        }
        return bodyString
    }

    @Throws(IOException::class)
    private fun logRequest(request: Request) {
        val requestbuffer = Buffer()
        val hasRequestBody = request.body() != null
        if (hasRequestBody) {
            request.body().writeTo(requestbuffer)
        }
        Logger.e("Url: " + request.url().toString() + "\n"
                + "Method: " + request.method() + "\n"
                + "Params: " + (if (hasRequestBody) parseParams(request.body(), requestbuffer).replace("&".toRegex(), "  ") else "null") + "\n"
                + "Headers: " + request.headers().toString())
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private fun parseContent(responseBody: ResponseBody, encoding: String?, clone: Buffer): String? {
        var charset = Charset.forName("UTF-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        return if (encoding != null && encoding.equals("gzip", ignoreCase = true)) {//content使用gzip压缩
            ZipUtil.decompressForGzip(clone.readByteArray(), convertCharset(charset))//解压
        } else if (encoding != null && encoding.equals("zlib", ignoreCase = true)) {//content使用zlib压缩
            ZipUtil.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset))//解压
        } else {//content没有被压缩
            clone.readString(charset)
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseParams(body: RequestBody, requestbuffer: Buffer): String {
        if (body.contentType() == null) return "Unknown"
        if (!body.contentType().toString().contains("multipart")) {
            var charset = Charset.forName("UTF-8")
            val contentType = body.contentType()
            if (contentType != null) {
                charset = contentType.charset(charset)
            }
            return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset))
        }
        return "This Params isn't Text"
    }

    private fun isParseable(responseBody: ResponseBody): Boolean {
        return if (responseBody.contentLength() == 0L) false else responseBody.contentType().toString().contains("text") || isJson(responseBody)
    }

    private fun isJson(responseBody: ResponseBody): Boolean {
        return responseBody.contentType().toString().contains("json")
    }

    private fun convertCharset(charset: Charset): String {
        val s = charset.toString()
        val i = s.indexOf("[")
        return if (i == -1) s else s.substring(i + 1, s.length - 1)
    }

}