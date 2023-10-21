
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
	/**
	 * aktualni ctena radka
	 */
	private String line;
	/**
	 * reader na vstup dat
	 */
	private BufferedReader inputData;
	/**
	 * writer na vystup dat
	 */
	private BufferedWriter outputData;
	/**
	 * aktualni radka rozdelena na jednotlive retezce
	 */
	private String[] sLine;
	/**
	 * znakova sekvence oznacujici zacatek retezce
	 */
	private CharSequence start = "❄";
	/**
	 * znakova sekvence oznacujici konec retezce
	 */
	private CharSequence end = "⛏";
	/**
	 * nazev vstupniho souboru
	 */
	private String input = "crazy_user_input.txt";
	/**
	 * nazev vystupniho souboru
	 */
	private String output = "output.txt";
	
	
	public Input() {
		
	}
	
	/**
	 * Metoda resici nacteni vstupniho souboru a jeho predypracovani pro 
	 * ziskani dat
	 */
	public void read() {		
		try {
			inputData = Files.newBufferedReader(Paths.get(input));
			outputData = new BufferedWriter(new FileWriter(output));
			while((line = inputData.readLine()) != null) {
				//resi komentare
				//resi odstraneni bilych znaku
				line = line.replaceAll("\\s+", " ");
				line = line.replaceAll("❄", " ❄ ");
				line = line.replaceAll("⛏", " ⛏ ");
				sLine = line.split(" ");
				for(int i = 0 ; i < sLine.length ; i++) {
					if(sLine[i].contains(start)) {
						i = comment(i)+1;
					}
					if(i < sLine.length) {
						if(!sLine[i].isEmpty()) {
							outputData.write(sLine[i]);
							outputData.newLine();
						}
					}
				}	
			}
			outputData.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 	Metoda hledajici konce komentaru, aby mohli byt ignorovany
	 * @param index index na kterem se se aktualne nachazi cteni souboru
	 * @return vraci index na kterem skoncil komentar
	 */
	public int comment(int index) {
		if(!(index >= (sLine.length-1))) {
			index++; 
		}
		while(!sLine[index].contains(end)) {
			if(sLine[index].contains(start)) {
				index = comment(index)+1;
			}
			index++;
			if(index > (sLine.length-1)) {
				try {
					if((line = inputData.readLine()) != null) {
						line =  line.replaceAll("\\s+", " ");
						line = line.replaceAll("❄", " ❄ ");
						line = line.replaceAll("⛏", " ⛏ ");
						sLine = line.split(" ");
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
		return index;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getOutput() {
		return this.output;
	}
}
