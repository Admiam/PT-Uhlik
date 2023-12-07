package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Trida spracovavajici vstupni soubor
 * @author TR
 *
 */
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
	 * aktualni radka rozdelena na jednotlive retezce
	 */
	private String[] sLine;
	/**
	 * nazev vstupniho souboru
	 */
	private String input = "tutorial.txt";
	/**
	 * nazev vystupniho souboru
	 */
	private String output = "output.txt";
	
	/**
	 * Metoda resici nacteni vstupniho souboru a jeho predzpracovani pro
	 * ziskani dat
	 */
	public void read() {		
		try {
			inputData = Files.newBufferedReader(Paths.get(input));
			BufferedWriter outputData = new BufferedWriter(new FileWriter(output));
			while((line = inputData.readLine()) != null) {
				//resi komentare
				//resi odstraneni bilych znaku
				line = line.replaceAll("\\s+", " ");
				line = line.replaceAll("❄", " ❄ ");
				line = line.replaceAll("⛏", " ⛏ ");
				sLine = line.split(" ");
				for(int i = 0 ; i < sLine.length ; i++) {
					if(sLine[i].contains("❄")) {
						i = comment(i)+1;
					}
					if(i < sLine.length && !sLine[i].isEmpty()) {
							outputData.write(sLine[i]);
							outputData.newLine();
					}
				}	
			}
			outputData.close();
			inputData.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	Metoda hledajici konce komentaru, aby mohli byt ignorovany
	 * @param index index na kterem se se aktualne nachazi cteni souboru
	 * @return vraci index na kterem skoncil komentar
	 */
	public int comment(int index) {
		
		int curInd = index;
		
		CharSequence start = "❄";
		CharSequence end = "⛏";
		
		if(!(curInd >= (sLine.length-1))) {
			curInd++; 
		}
		while(!sLine[curInd].contains(end)) {
			if(sLine[curInd].contains(start)) {
				curInd = comment(curInd)+1;
			}
			curInd++;
			if(curInd > (sLine.length-1)) {
				try {
					if((line = inputData.readLine()) != null) {
						line =  line.replaceAll("\\s+", " ");
						line = line.replaceAll("❄", " ❄ ");
						line = line.replaceAll("⛏", " ⛏ ");
						sLine = line.split(" ");
						curInd = 0;
					}
					else {
						return curInd;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return curInd;
	}
	
	/**
	 * Setter na nastaveni vstupniho souboru
	 * @param input nastaveni nazvu vstupniho souboru
	 */
	public void setInput(String input) {
		this.input = input;
	}
	
	/**
	 * Setter na nastaveni vystupniho souboru
	 * @param output  nastaveni nazvu vystupniho souboru
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	/**
	 * Getter na ziskani cesty k vystupnimu souboru
	 * @return vraci nazev vystupniho souboru
	 */
	public String getOutput() {
		return this.output;
	}
}
