
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
	
	public Input() {
		
	}
	
	public void read() {
		String line;
		BufferedReader inputData = Files.newBufferedReader(Paths.get("tutorial.txt"));
		while((line = inputData.readLine()) != null) {
			//vyresit komentare
			//vyresit odstraneni vsech bilych znaku
			//vyresit vytvoreni objektu z dat
		}
	}
}
