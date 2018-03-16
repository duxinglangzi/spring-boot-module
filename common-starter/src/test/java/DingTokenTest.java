import com.andy.common.https.LocalHttpClient;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * <p>Description: </p>
 * <p>@author wuqiong  2018/1/31 20:00 </p>
 */
@Slf4j
public class DingTokenTest extends TestCase {
    public static final String corpid = "ding0fbecc7ad8833f17";
    public static final String corpsecret = "sGBDXzcMYIba-bFu4XBI2vvxlHzLlBgzNrY0Hb5Sr75IGcWmIHyBePjZoho6c3Kbu";

    public static final String accessToken = "6c5f0c4e65673d42badfd506810897f4";

    //1、获取token
    public void test_get_token() {
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri("https://oapi.dingtalk.com/gettoken")
                .addParameter("corpid", corpid)
                .addParameter("corpsecret", corpsecret)
                .build();
        try {
            HttpResponse httpResponse = LocalHttpClient.execute(httpUriRequest);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = httpResponse.getEntity();
                String str = EntityUtils.toString(entity);
                System.out.println(str);
                log.info(str);
                //{"errcode":0,"access_token":"744f9e4196363957ba1692d0f3f06717","errmsg":"ok","expires_in":7200}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //2、获取部门列表
    public void test_get_scopes() {
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri("https://oapi.dingtalk.com/department/list")
                .addParameter("access_token", accessToken)
                .addParameter("id", "1")
                .build();
        try {
            HttpResponse response = LocalHttpClient.execute(httpUriRequest);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String string = EntityUtils.toString(entity);
                log.info(string);
                /**
                 * 只是 把自己部门拿出来了，
                 *  {"createDeptGroup":true,"name":"专家系统研发部","id":57649362,"autoAddUser":true,"parentid":57591434}
                 */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
