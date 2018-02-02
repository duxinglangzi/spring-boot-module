package com.andy.common.https;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;


public final class HttpClientFactory {

    private static final Integer MAX_TOTAL = 300;             //连接池最大连接数
    private static final Integer MAX_PER_ROUTE = 50;          //单个路由默认最大连接数
    private static final Integer REQ_TIMEOUT =  5 * 1000;     //请求超时时间ms
    private static final Integer CONN_TIMEOUT = 5 * 1000;     //连接超时时间ms
    private static final Integer SOCK_TIMEOUT = 10 * 1000;    //读取超时时间ms
    private static HttpClientConnectionMonitorThread thread;  //HTTP链接管理器线程

    public static HttpClientConnectionMonitorThread getThread() {
        return thread;
    }
    public static void setThread(HttpClientConnectionMonitorThread thread) {
        HttpClientFactory.thread = thread;
    }

    public static HttpClient createSimpleHttpClient(){
        SSLConnectionSocketFactory sf = SSLConnectionSocketFactory.getSocketFactory();
        return HttpClientBuilder.create()
                .setSSLSocketFactory(sf)
                .build();
    }

    public static HttpClient createHttpClient() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQ_TIMEOUT)
                .setConnectTimeout(CONN_TIMEOUT).setSocketTimeout(SOCK_TIMEOUT)
                .build();
        HttpClientFactory.thread = new HttpClientConnectionMonitorThread(poolingHttpClientConnectionManager); //管理 http连接池


        ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new ServiceUnavailableRetryStrategy(){

            /**
             * Determines if a method should be retried given the response from the target server.
             *
             * @param response       the response from the target server
             * @param executionCount the number of times this method has been
             *                       unsuccessfully executed
             * @param context        the context for the request execution
             * @return {@code true} if the method should be retried, {@code false}
             * otherwise
             */
            @Override
            public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
                if (executionCount < 3){
                    return false;//重试3次
                }
                return false;
            }

            /**
             * @return The interval between the subsequent auto-retries.
             */
            @Override
            public long getRetryInterval() {
                return 100;//每次重试间隔
            }
        };

        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {

            /**
             * Determines if a method should be retried after an IOException
             * occurs during execution.
             *
             * @param exception      the exception that occurred
             * @param executionCount the number of times this method has been
             *                       unsuccessfully executed
             * @param context        the context for the request execution
             * @return {@code true} if the method should be retried, {@code false}
             * otherwise
             */
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount < 3){
                    return false;//重试3次
                }
                return false;
            }
        };


        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
//                .setServiceUnavailableRetryStrategy(serviceUnavailableRetryStrategy)    //可设置间隔时间的重试机制
//                .setRetryHandler(httpRequestRetryHandler)   //失败重试, 即时重试，无间隔
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}
