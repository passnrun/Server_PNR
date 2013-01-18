package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.fm.util.JSONParser;

public class TestMobileClient {

	public static void main(String[] args) throws Exception {
		URLConnection connection = new URL("http://localhost:8080/PassNRun_v1/Mobile").openConnection();
//		URLConnection connection = new URL("http://pickledphotos.com/passnrun/Mobile").openConnection();
	    // Let the run-time system (RTS) know that we want input.
	    connection.setDoInput (true);
	    // Let the RTS know that we want to do output.
	    connection.setDoOutput (true);
	    // No caching, we want the real thing.
	    connection.setUseCaches (false);
	    // Specify the content type.
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    // Send POST output.
	    DataOutputStream printout = new DataOutputStream(connection.getOutputStream ());
	    String content ="{\"service\" : \"register\",\"manager\" : {\"firstName\" : \"Fatih\",\"team\" : \"Belhanda\",\"lastName\" : \"Terim\",\"nationality\" : \"turkey\",\"language\" : \"turkish\",\"birthdate\" : \"22081982\",\"device\" : \"iphone\",\"deviceId\" : \"dmskfmdsjfnskhfdskfzbndshkfbsdhkfbs\"}}";
//	    String content = "{\"service\" : \"createLeague\", \"name\" : \"Super Leage\", \"country\":\"turkey\"}";
//	    String content = "{\"service\" : \"initiate\", \"leagueId\" : 1}";
//	    String content = "{\"service\" : \"resign\", \"id\" : 3}";
//	    String content = "{\"service\" : \"createPlayers\", \"manager\" : {\"name\" : \"Super Leage\", \"country\":\"turkey\"}}";
//	    String content = "{\"service\" : \"gameResult\", \"managerId\" : 31, \"minGames\" =0\"}";
//	    String content = "{\"service\" : \"gameDetails\", \"gameId\" : 1, \"logLevel\" : 4}";
//	    String content = "{\"service\" : \"squad\", \"teamId\" : 1}";
//	    String content = "{\"service\" : \"schedule\", \"leagueId\" : 1}";
//	    String content = "{\"service\" : \"fixture\", \"leagueId\" : 1, \"seasonId\" : 1}";
	    printout.writeBytes (content);
	    printout.flush ();
	    printout.close ();
	    // Get response data.
	    DataInputStream input = new DataInputStream (connection.getInputStream ());
	    String str;
	    String out = "";
	    while (null != ((str = input.readLine())))
	    		out +=str;
	    input.close ();
	    System.out.println(out);
	    Map<String, Object> map = JSONParser.parse(out);
	    System.out.println(map);
		
	}
}
