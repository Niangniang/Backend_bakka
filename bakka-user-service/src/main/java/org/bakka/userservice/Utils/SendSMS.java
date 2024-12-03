package org.bakka.userservice.Utils;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.util.Base64;


public class SendSMS {
        public static void send_sms(String recipient, String subject, String content) throws Exception {
            String link= "https://api.ksmsplus.net/api";
            String key_private="50120c94287b5608a1431c35b183e456";
            String token="6006288cc96cc6de8438949794b1bbf2";
            String login = "dijany";
            String signature="dijany";
            String password = token;
            String authString = login + ":" + password;
            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
            try{
                content=URLEncoder.encode(content, "UTF8");
                subject=URLEncoder.encode(subject, "UTF8");
                signature=URLEncoder.encode(signature, "UTF8");
                long timestamp = System.currentTimeMillis()/1000;
                String msgToEncrypt=token+subject+signature+recipient+content+timestamp;
                String key=hmacSha(key_private, msgToEncrypt);
                System.setProperty("javax.net.ssl.trustStore", "clienttrust");

                String url=
                        link+"?token="+token+"&subject="+subject+"&signature="+signature+"&recipient="+recipient+"&content="
                                +content+"&timestamp="+timestamp+"&key="+key;
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestProperty("Authorization", "Basic " + authStringEnc);
                int responseCode = con.getResponseCode();
                if (responseCode == 401) {
                    throw new RuntimeException("LOGIN ou TOKEN incorrect: Acces non autorise HTTP error code : "+ responseCode);
                }
               //ystem.out.println("\nSending request to URL : " + url);
                BufferedReader in = new BufferedReader( new
                        InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            }
            catch(MalformedURLException mue) {
                mue.printStackTrace();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
}
        }

        public static String hmacSha(String SECRETKEY, String VALUE) {
            try {
                SecretKeySpec signingKey = new SecretKeySpec(SECRETKEY.getBytes("UTF-8"), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(signingKey);
                byte[] rawHmac = mac.doFinal(VALUE.getBytes("UTF-8"));
                byte[] hexArray = {
                        (byte)'0', (byte)'1', (byte)'2', (byte)'3',
                        (byte)'4', (byte)'5', (byte)'6', (byte)'7',
                        (byte)'8', (byte)'9', (byte)'a', (byte)'b',
                        (byte)'c', (byte)'d', (byte)'e', (byte)'f'
                };
                byte[] hexChars = new byte[rawHmac.length * 2];
                for ( int j = 0; j < rawHmac.length; j++ ) {
                    int v = rawHmac[j] & 0xFF;
                    hexChars[j * 2] = hexArray[v >>> 4];
                    hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                }
                return new String(hexChars);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
    }
}
