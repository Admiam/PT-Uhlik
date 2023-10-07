
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
	
	String line;
	BufferedReader inputData;
	BufferedWriter output;
	String[] sLine;
	CharSequence start = "❄";
	CharSequence end = "⛏";
	
	public Input() {
		
	}
	
	public void read() {		
		try {
			inputData = Files.newBufferedReader(Paths.get("crazy_user_input.txt"));
			//inputData = Files.newBufferedReader(Paths.get("strangeL.txt"));
			output = new BufferedWriter(new FileWriter("output.txt"));
			while((line = inputData.readLine()) != null) {
				//vyresit komentare
				//vyresit odstraneni vsech bilych znaku	
				line = line.replaceAll("\\s+", " ");
				sLine = line.split(" ");
				for(int i = 0 ; i < sLine.length ; i++) {
					if(sLine[i].contains(start)) {
						i = comment(i)+1;
					}
					if(i < sLine.length) {
						if(!sLine[i].isEmpty()) {
							output.write(sLine[i]);
							output.newLine();
						}
					}
				}
				//vyresit vytvoreni objektu z dat
				
			}
			output.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int comment(int index) {
		if(!(index >= (sLine.length-1))) {
			index++; 
		}
		while(!sLine[index].contains(end)) {
			if(sLine[index].contains(start)) {
				index = comment(index)+1;
			}
			//sLine[index] = "##";
			index++;
			if(index > (sLine.length-1)) {
				try {
					if((line = inputData.readLine()) != null) {
						sLine = line.replaceAll("\\s+", " ").split(" ");
						index = 0;
					}
					else {
						return index;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//System.out.println("Koment nasel");
		return index;
	}
}

