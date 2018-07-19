package speachtootext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class speachtootext_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
   
	    SpeechToText service = new SpeechToText();
	    service.setUsernameAndPassword("1821004", "1821004");

	    File audio = new File("audio/shop_oldman01_10-bukibougu.wav");
	    RecognizeOptions options = null;
		MySQL mysql = new MySQL();

		try {
			options = new RecognizeOptions.Builder()
					.model("ja-JP_BroadbandModel")
			        .audio(audio)
			        .contentType(RecognizeOptions.ContentType.AUDIO_WAV)
			        .build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        SpeechRecognitionResults transcript = service.recognize(options).execute();

	        //System.out.println(transcript);
	        String s = String.valueOf(transcript);
	        ObjectMapper mapper = new ObjectMapper();
	        
	        try {
				JsonNode node = mapper.readTree(s);
				String text  = node.get("results").get(0).get("alternatives").get(0).get("transcript").toString();
				System.out.println("transcript : " + text);
				double confidence = node.get("results").get(0).get("alternatives").get(0).get("confidence").asDouble();
				System.out.println("confidence : " + confidence);
				mysql.updateImage(text,confidence); //


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}

	      }
	}
