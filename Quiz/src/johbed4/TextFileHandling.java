package johbed4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Klass för filhanteringen. Klassen har två statiska metoder som kan skriva respektive läsa till/från en textfil.
 * @author johbed4 - Johannes Bergius
 * @version 1.0
 */
public class TextFileHandling {
	
	public static final String FILEPATH = "./src/johbed4/files/";
	public static final String FILE_ERROR = "File error";
	public static final String FILE_NOT_SAVED = "File error, file not saved!";
	public static final String FILE_NOT_LOADED = "File error, file not loaded";
	public static final String FILE_NOT_CLOSED = "File error, stream not closed";
	
    /**
     * Metod som sparar en sträng till en textfil.
     * Returnerar true om sparande lyckas, false om det misslyckas.
     * Misslyckas sparandet så får användaren ett felmeddelande.
     * @param text - Text-strängen som ska skrivas.
     * @param fileName - filens namn.
     * @param append - true om text ska läggas till eller false om filen ska ersättas.
     * @return success
     */
    public static boolean save(String text, String fileName, boolean append) {
        boolean success = false;
        PrintWriter pw = null;
        FileWriter fw = null;
        try {
        	fw = new FileWriter(FILEPATH + fileName, append);
            pw = new PrintWriter(fw);
            pw.println(text);
            success = true;
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, FILE_NOT_SAVED, FILE_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    return success;
    }
    
    /**
     * Metod som läser text från en fil.
     * Returnerar en lista med alla textrader i filen eller null om laddningen misslyckats.
     * Om laddning misslyckas får användaren ett felmeddelande.
     * @param fileName - filens namn.
     * @return textAsList - Arraylist med alla textrader.
     */
    public static List<String> read(String fileName) {
    	List<String> rows = new ArrayList<>();
    	BufferedReader reader = null;
    	FileReader fl = null;
 
    	try {
    		fl = new FileReader(FILEPATH + fileName);
    		reader = new BufferedReader(fl);
    		String row;
    		while ((row = reader.readLine()) != null) {
    			rows.add(row);
    		}
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, FILE_NOT_LOADED, FILE_ERROR, JOptionPane.ERROR_MESSAGE);
        	return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                	JOptionPane.showMessageDialog(null, FILE_NOT_CLOSED, FILE_ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return rows;
    }
}
