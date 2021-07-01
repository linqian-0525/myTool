package com.lq.mytool.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpClientUtil {
    private static Log log = LogFactory.getLog(HttpClientUtil.class);
    private static URL url;
    private static HttpURLConnection con;
    private static int state = -1;
    public static final String CHARSET_UTF_8 = "utf-8";

    public static final String STATUS_CODE_KEY = "statusCode";

    public static final String CONTENT_KEY = "content";

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {

        try {
            //System.out.println("初始化HttpClientTest~~~开始");
            org.apache.http.conn.ssl.SSLContextBuilder builder = new org.apache.http.conn.ssl.SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();

            //System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).build();
    }

    public static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();

        return httpClient;
    }


    public static Map<String, String> get(String url, Map<String, Object> params) throws Exception {
        StringBuilder requestUrl = new StringBuilder(url);
        requestUrl.append("?");
        for (Map.Entry entry : params.entrySet()) {
            requestUrl.append(entry.getKey()).append("=");
            requestUrl.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
            requestUrl.append("&");
        }
        requestUrl.deleteCharAt(requestUrl.length() - 1);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl.toString());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        int status = 0;
        String content = "";
        Map<String, String> result = new HashMap<>();
        status = response.getStatusLine().getStatusCode();
        content = EntityUtils.toString(entity);
        result.put(STATUS_CODE_KEY, status + "");
        result.put(CONTENT_KEY, content);
        return result;
    }


    public static Map<String, String> post(String url, Map<String, Object> parameters, String jwtToken) throws Exception {
        return post(url, parameters, null, jwtToken);
    }

    public static Map<String, String> post(String url, Map<String, Object> parameters, Map<String, List<ByteArrayBody>> streamBodyMap, String jwtToken) throws Exception {
        Map<String, String> result = new HashMap<>();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        int status = 0;
        String content = "";
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = null;
            if (streamBodyMap == null) {
                httpPost = postForm(url, parameters);
            } else {
                httpPost = postForm(url, parameters, streamBodyMap);
            }
            // 头部添加jwtToken
            if (jwtToken != null) {
                httpPost.addHeader("jwtToken", jwtToken);
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpPost.setConfig(requestConfig);
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            content = EntityUtils.toString(entity);
            result.put("statusCode", status + "");
            result.put("content", content);
            Header header = response.getFirstHeader("location");
            if (header != null) {
                result.put("location", header.getValue());
            }
            EntityUtils.consume(entity);
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpclient != null) {
                httpclient.close();
            }
        }
        return result;
    }

    private static HttpPost postForm(String url, Map<String, Object> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<>();
        if (Objects.nonNull(params)) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                urlParameters.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }

        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
        return httpPost;
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl 地址
     * @param maps    参数
     * @param file    单个附件
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, File file) {
        HttpPost httpPost = new HttpPost(httpUrl);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        FileBody fileBody = new FileBody(file);
        meBuilder.addPart("file", fileBody);
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }


    private static HttpPost postForm(String url, Map<String, Object> params,
                                     Map<String, List<ByteArrayBody>> streamBodyMap) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                .create()
                .setMode(HttpMultipartMode.RFC6532) // avoid chinese garbled
                .setCharset(Consts.UTF_8);

        if (params != null) {
            for (String key : params.keySet()) {
                multipartEntityBuilder.addTextBody(key, params.get(key).toString(), ContentType.create("text/plain", Consts.UTF_8));
            }
        }

        if (streamBodyMap != null) {
            for (String name : streamBodyMap.keySet()) {
                List<ByteArrayBody> list = streamBodyMap.get(name);
                for (ByteArrayBody item : list) {
                    multipartEntityBuilder.addPart(name, item);
                }
            }
        }

        httpPost.setEntity(multipartEntityBuilder.build());
        return httpPost;
    }


    public static URL isConnect(String urlStr) {
        int counts = 0;
        if (urlStr == null || urlStr.length() <= 0) {
            return null;
        }
        URL url = null;
        HttpURLConnection con = null;
        int state = 0;
        while (counts < 3) {
            try {
                url = new URL(urlStr);
                if (urlStr.contains("https")) {
                    trustAllHttpsCertificates();

                    HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
                    httpsCon.setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String urlHostName, SSLSession session) {
                            log.info("Warning: URL Host: " + urlHostName + " vs. "
                                    + session.getPeerHost());
                            return true;
                        }
                    });
                    System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                    httpsCon.connect();
                    state = httpsCon.getResponseCode();
                } else {
                    con = (HttpURLConnection) url.openConnection();
                    state = con.getResponseCode();
                }

                log.info(counts + "= " + state);
                if (state == 200) {
                    log.info(url + "URL is available！");
                }
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
                counts++;
                log.info("URL is not available，try " + counts + " times");
                url = null;
                continue;
            }
        }
        return url;
    }


    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(
                X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static Map<String, String> postJson(String url, String json, String sysName) throws Exception {
        Map<String, String> result = new HashMap<>();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        int status = 0;
        String content = "";
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            //解决中文乱码问题
            StringEntity reqEntity = new StringEntity(json, "utf-8");
            reqEntity.setContentEncoding("UTF-8");
            reqEntity.setContentType("application/json");
            httpPost.setEntity(reqEntity);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpPost.setConfig(requestConfig);
            if (StringUtils.isNotEmpty(sysName)) {
                httpPost.addHeader("x-line-code", sysName);
            }
            response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            content = EntityUtils.toString(entity);
            result.put(STATUS_CODE_KEY, status + "");
            result.put(CONTENT_KEY, content);
            Header header = response.getFirstHeader("location");
            if (header != null) {
                result.put("location", header.getValue());
            }
            EntityUtils.consume(entity);
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpclient != null) {
                httpclient.close();
            }
        }
        return result;
    }

//    public static Map<String, String> postJson(String url, String json, String jwtToken) throws Exception {
//        Map<String, String> result = new HashMap<>();
//        CloseableHttpResponse response = null;
//        CloseableHttpClient httpclient = null;
//        int status = 0;
//        String content = "";
//        try {
//            httpclient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//            //解决中文乱码问题
//            StringEntity reqEntity = new StringEntity(json, "utf-8");
//            reqEntity.setContentEncoding("UTF-8");
//            reqEntity.setContentType("application/json");
//            httpPost.setEntity(reqEntity);
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
//                    .setSocketTimeout(10000).build();
//            httpPost.setConfig(requestConfig);
//            // 头部添加jwtToken
//            if (jwtToken != null) {
//                httpPost.addHeader("jwtToken", jwtToken);
//            }
//            response = httpclient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            status = response.getStatusLine().getStatusCode();
//            content = EntityUtils.toString(entity);
//            result.put(STATUS_CODE_KEY, status + "");
//            result.put(CONTENT_KEY, content);
//            Header header = response.getFirstHeader("location");
//            if (header != null) {
//                result.put("location", header.getValue());
//            }
//            EntityUtils.consume(entity);
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//            if (httpclient != null) {
//                httpclient.close();
//            }
//        }
//        return result;
//    }

    public static Map<String, String> get(String url, Map<String, Object> params, String jwtToken) throws Exception {
        StringBuilder requestUrl = new StringBuilder(url);
        requestUrl.append("?");
        for (Map.Entry entry : params.entrySet()) {
            requestUrl.append(entry.getKey()).append("=");
            requestUrl.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
            requestUrl.append("&");
        }
        requestUrl.deleteCharAt(requestUrl.length() - 1);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl.toString());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);
        if (jwtToken != null) {
            httpGet.addHeader("jwtToken", jwtToken);
        }
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        int status = 0;
        String content = "";
        Map<String, String> result = new HashMap<>();
        status = response.getStatusLine().getStatusCode();
        content = EntityUtils.toString(entity);
        result.put(STATUS_CODE_KEY, status + "");
        result.put(CONTENT_KEY, content);
        return result;
    }
}
