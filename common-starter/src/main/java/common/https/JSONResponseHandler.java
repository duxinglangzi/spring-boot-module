package common.https;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONResponseHandler {
	
	private static final Log log = LogFactory.getLog(JSONResponseHandler.class);
	
	private static Map<String, ResponseHandler<?>> map = new HashMap<String, ResponseHandler<?>>();
	
	@SuppressWarnings("unchecked")
	public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz){

		if(map.containsKey(clazz.getName())){
			return (ResponseHandler<T>)map.get(clazz.getName());
		}else{
			ResponseHandler<T> responseHandler = new ResponseHandler<T>() {
				@Override
				public T handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    String str = EntityUtils.toString(entity);
	                    log.info("JSONResponseHandler_Access_to_server_data 获取数据为(转换后)："+new String(str.getBytes("iso-8859-1"),"utf-8"));
	                    return JSON.parseObject(new String(str.getBytes("iso-8859-1"),"utf-8"), clazz);
	                } else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
				}
			};
			map.put(clazz.getName(), responseHandler);
			return responseHandler;
		}
	}
}
