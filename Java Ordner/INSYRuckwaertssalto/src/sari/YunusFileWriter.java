package sari;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class YunusFileWriter {
	String filename = "";
	File x = null;
	
	/**
	 * Der File mit dem entsprechenden Filenamen wird erzeugt.
	 * Ein Verzeichnis davor kann auch angegeben werden. Achtung bei \ im String \\ angeben.
	 * 
	 * @param filename	- Angabe des Dateinamens. Auf Endung Achten! (z.B. .txt)
	 * @param overwrite - Soll die Datei übersrieben werden? Bei ja wird die alte gelöscht und eine neue erzeugt.
	 * 						Bei nein wird der Vorgang abgebrochen, falls eine Datei mit dem selben Namen existiert
	 */
	public YunusFileWriter(String filename,boolean overwrite){
		this.filename = filename;
		x = new File(filename);
		
		if(overwrite==true && x.exists())x.delete();
		if(overwrite==false && x.exists()){
			System.out.println("File existiert. Objekt fehlerhaft erzugt. Entweder löschen, überschreiben oder einen anderen Namen wählen!!!");
			return;
		}
		
		//File erzeugen.
		try {
			x.createNewFile();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}	
	}	
	
	/**
	 * Mit dieser Methode kann zu einem File Text hinzugefügt werden.
	 * 
	 * @param textToWrite	- Der Text, der zum File angehängt werden soll
	 * @param lineBreak		- Bei true wird am Ende des Textes ein Zeilenumbruch hinzugefügt.
	 */
	public void extendFile(String textToWrite, boolean lineBreak){
		BufferedWriter bw = null;

		try {
		    bw = new BufferedWriter(new FileWriter(filename, true));
		    bw.write(textToWrite);
		    if(lineBreak==true)bw.newLine();
		    bw.flush();
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		} finally { // always close the file
		    if (bw != null) {
		        try {
		            bw.close();
		        } catch (IOException ioe2) {
		            // just ignore it
		        }
		    }
		}
	}
}
