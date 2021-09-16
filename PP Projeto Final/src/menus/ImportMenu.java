/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package menus;

import edu.ma02.core.exceptions.CityException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pp.projeto.core.City;
import pp.projeto.io.Importer;

public class ImportMenu {

    /**
     * Builder method ImportMenu
     */
    public ImportMenu() {
    }
    
    /**
     * Print Import Menu
     * @param city city will be recive the data
     * @return 1.true if evrething it's right <p> 2.false if user want to go Main Menu
     */
    public boolean printImportMenu(City city) {

        Scanner scan = new Scanner(System.in);

        String fileName;

        System.out.print("-------------------------------"
                + "\n|                             |"
                + "\n|       | Import File |       |"
                + "\n|                             |"
                + "\n|   write exit if you want    |"
                + "\n|      go to MainMenu         |"
                + "\n-------------------------------\n");

        do {

            System.out.print("Choose File Name:");

            fileName = scan.next();

            if (fileName.equals("exit")) {
                return false;
            }

        } while (this.verifyIfIsJsonFile(fileName) == false);

        Importer importer = new Importer();
        
        try {
            importer.importData(city, fileName);
        } catch (IOException ex) {
            Logger.getLogger(ImportMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CityException ex) {
            Logger.getLogger(ImportMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;

    }
    
    /**
     * Verify if the file is JSONFile.
     * @param fileName fileName
     * @return 1.true if the file is a JSONFile <p> 2.false if the file is not a JSONFile
     */
    private boolean verifyIfIsJsonFile(String fileName) {

        char[] charOld = new char[fileName.length()];

        int contador = 0;

        for (int i = 0; i < fileName.length(); i++) {

            if (fileName.charAt(i) == '.') {
                contador = i;
            }
        }

        return fileName.substring(contador, fileName.length()).equals(".json");
    }
}
