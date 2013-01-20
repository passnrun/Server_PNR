package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.fm.dal.PlayerTemplate;
import com.fm.dao.DAO;
import com.fm.util.Parameter;

public class LoadPlayerTemplate {
	static List<String> countries = new ArrayList<String>();
	
	public static void loadPlayerNames() throws Exception {
		FileReader filereader = new FileReader(Parameter.getString("templatefile_path"));
		System.out.println("File Encoding:"+filereader.getEncoding());
		BufferedReader reader = new BufferedReader(filereader);
		String line = reader.readLine();
		DAO dao = new DAO();
		while (line != null){
			if (hasPlayerInfo(line)){
				PlayerTemplate player = extractPlayer(line);
//				System.out.println(player.getName() +"," + player.getSurname() + "," + player.getCountry());
				dao.save(player);
			}
			line = reader.readLine();
		}
	}

	private static PlayerTemplate extractPlayer(String line) {
		int firstIndex = line.indexOf("HYPERLINK \"") + "HYPERLINK \"".length();
		int lastIndex = line.indexOf("\"", firstIndex);
		String url = line.substring(firstIndex, lastIndex);
		String[] vals = url.split("/");
		String name = toUTF(vals[7]);
		String country = toUTF(vals[5]);
		System.out.println(toUTF(name) + "("+country+")");
		if (!countries.contains(country))
			countries.add(country);
		PlayerTemplate player = new PlayerTemplate();
		player.setCountry(country);
		String[] names = name.split("-");
		if (names.length==1){
			player.setName(names[0]);
			player.setSurname("");
		}else if (names.length==2){
			player.setName(names[0]);
			player.setSurname(names[1]);
		}else{
			player.setName(names[0]);
			player.setSurname(names[names.length-1]);
			for (int i = 1; i < names.length -1; i++)
				player.setName(player.getName() + " " + names[i]);
		}
		return player;
	}

	private static String toUTF(String val) {
		try {
			return URLDecoder.decode(val, "utf8");
		} catch (UnsupportedEncodingException e) {
			return URLDecoder.decode(val);
		}
	}

	private static boolean hasPlayerInfo(String line) {
		return  (line.indexOf("http://www.goal.com/") != -1 && line.indexOf("people") != -1);
	}
}
