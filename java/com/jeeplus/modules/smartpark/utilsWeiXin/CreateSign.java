/**
 * 
 */
package com.jeeplus.modules.smartpark.utilsWeiXin;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.RandomStringUtils;

 
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

@Repository
public class CreateSign {
	
	
	
	
	
    public  String getToken(String appid,String prepay_id) throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        String randomOnce = RandomStringUtils.randomAlphanumeric(32);
        //随机字符串
        String nonceStr = randomOnce;//真！随机字符串
        //时间戳
        long timestamp = System.currentTimeMillis() / 1000;
        //从下往上依次生成
        String message = buildMessage(appid, timestamp, nonceStr, prepay_id);
        //签名
        String signature = sign(message.getBytes("utf-8"));

        JSONObject param = new JSONObject();
        param.put("appId",appid);
        param.put("timeStamp",timestamp);
        param.put("nonceStr",randomOnce);
        param.put("package",prepay_id);
        param.put("signType","RSA");
        param.put("paySign",signature);

        return  param.toString() ;
    }
    public String sign(byte[] message) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        //签名方式
        Signature sign = Signature.getInstance("SHA256withRSA");
        //在本地环境运行使用 私钥，通过MyPrivateKey来获取，这是个静态类可以接调用方法 ，需要的是_key.pem文件的绝对路径配上文件名
		sign.initSign(MyPrivatekey.getPrivateKey("E:\\微信支付\\Cert\\apiclient_key.pem"));
		//在服务器中使用这种方式
        //FileReader fileReader = new FileReader("\\usr\\local\\WXCertUtil\\cert\\1621641850_20220614_cert\\apiclient_key.pem");
      //  sign.initSign(MyPrivatekey.getPrivateKey(fileReader.readString()));
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     *  按照前端签名文档规范进行排序，\n是换行
     * @param appid
     * @param timestamp
     * @param nonceStr
     * @param prepay_id
     * @return
     */
    public String buildMessage(String appid, long timestamp,String nonceStr,String prepay_id) {
        return appid + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + "prepay_id="+prepay_id + "\n";
    }
} 