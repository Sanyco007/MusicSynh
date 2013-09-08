package audio;

import network.IVkResponse;
import network.VkConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AudioMethods implements IVkResponse {

    private VkConnection vk;
    private IAudioResult audioResult;

    public AudioMethods(VkConnection vk) {
        this.vk = vk;
    }

    public void search(String text, int count) {
        Map parameters = new HashMap<String, String>();
        parameters.put("q", text);
        parameters.put("count", "" + count);
        vk.setOnVkResponse(this);
        vk.sendApiRequest("audio.search", parameters);
    }

    public void setOnAudioResult(IAudioResult audioResult) {
        this.audioResult = audioResult;
    }

    @Override
    public void OnVkResponse(JSONObject json) {
        JSONArray arr = (JSONArray)json.get("response");
        AudioObject[] audios = new AudioObject[arr.size() - 1];
        for (int i = 1; i < arr.size(); i++) {
            AudioObject audio = new AudioObject();
            JSONObject obj = (JSONObject) arr.get(i);
            for (Map.Entry item : (Set<Map.Entry>)obj.entrySet()) {
                String key = (String)item.getKey();

                if (key.equals("artist")) {
                    audio.artist = (String)item.getValue();
                }
                if (key.equals("title")) {
                    audio.title =  (String)item.getValue();
                }
                if (key.equals("url")) {
                    audio.url =  (String)item.getValue();
                }

            }
            audios[i - 1] = audio;
        }
        if (audioResult != null) {
            audioResult.OnAudioResult(audios);
        }
    }
}
