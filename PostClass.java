import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostClass {
    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            go("getfield", new String[] {"[1,2,3]"});
        }
    }

    public static void go(String page, String[] inputData){
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try{
            URL url = new URL("http://127.0.0.1:5000/"+page+"/"); //important to add the trailing slash after add
           for(String input: inputData){
                byte[] postData = input.getBytes(StandardCharsets.UTF_8);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(input.length()));
                os = new DataOutputStream(conn.getOutputStream());
                os.write(postData);
                os.flush();

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server ....");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                conn.disconnect();
            }
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }catch (IOException e){
        e.printStackTrace();
    }finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
        }
    }
}