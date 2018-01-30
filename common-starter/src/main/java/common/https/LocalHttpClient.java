package common.https;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

public class LocalHttpClient {
	
    protected static HttpClient httpClient = HttpClientFactory.createHttpClient();

    /**
     * 数据返回:JSON对象解析
     * @param request  request请求信息
     * @param clazz    Class类对象
     * @param <T>      泛指
     * @return         Class类对象
     */
    public static <T> T executeJSONResult(HttpUriRequest request, Class<T> clazz){
        return execute(request,JSONResponseHandler.createResponseHandler(clazz));
    }

    /**
     * 数据返回:XML对象解析
     * @param request  request请求信息
     * @param clazz    Class类对象
     * @param <T>      泛指
     * @return         Class类对象
     */
    public static <T> T executeXmlResult(HttpUriRequest request, Class<T> clazz){
        return execute(request, XmlResponseHandler.createResponseHandler(clazz));
    }
    

    /**
     * 执行Http请求
     * @param request  请求参数
     * @param responseHandler  返回值解析
     * @param <T>   泛指Class类
     * @return T
     */
    public static <T> T execute(HttpUriRequest request, ResponseHandler<T> responseHandler){
        try {
            return httpClient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Http请求
     * @param request 请求参数
     * @return HttpResponse对象
     */
    public static HttpResponse execute(HttpUriRequest request) {
        try {
            return httpClient.execute(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
