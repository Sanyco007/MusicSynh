package network;

import javafx.application.Platform;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class VkConnection {

    private String accessToken;
    private String request = "https://api.vk.com/method/%s?%s&access_token=%s";
    private IVkResponse callback;

    public VkConnection(String token) {
        accessToken = token;
        callback = null;
    }

    public void setOnVkResponse(IVkResponse response) {
        callback = response;
    }

    public void sendApiRequest(String method, Map parameters) {
        Set<Map.Entry> set = parameters.entrySet();
        StringBuilder parametersBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry item : set) {
            if (!first) {
                parametersBuilder.append("&");
            }
            if (first) {
                first = false;
            }
            parametersBuilder.append((String)item.getKey())
                    .append("=")
                    .append((String) item.getValue());
        }
        String params = parametersBuilder.toString();
        params = encodeURLComponent(params);
        String uri = String.format(request, method, params, accessToken);
        new RequestThread(this, uri).start();
    }

    public static String encodeURLComponent(final String s)
    {
        if (s == null)
        {
            return "";
        }

        final StringBuilder sb = new StringBuilder();

        try
        {
            for (int i = 0; i < s.length(); i++)
            {
                final char c = s.charAt(i);

                if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) ||
                        ((c >= '0') && (c <= '9')) ||
                        (c == '-') ||  (c == '.')  || (c == '_') ||
                        (c == '~') || (c == '=') || (c == '&'))
                {
                    sb.append(c);
                }
                else
                {
                    final byte[] bytes = ("" + c).getBytes("UTF-8");

                    for (byte b : bytes)
                    {
                        sb.append('%');

                        int upper = (((int) b) >> 4) & 0xf;
                        sb.append(Integer.toHexString(upper).toUpperCase(Locale.US));

                        int lower = ((int) b) & 0xf;
                        sb.append(Integer.toHexString(lower).toUpperCase(Locale.US));
                    }
                }
            }

            return sb.toString();
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new RuntimeException("UTF-8 unsupported!?", uee);
        }
    }

    void finishRequest(String response) {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final JSONObject json = (JSONObject) obj;
        if (callback != null) {
            //run in ui thread
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    callback.OnVkResponse(json);
                }
            });
        }
    }

}
