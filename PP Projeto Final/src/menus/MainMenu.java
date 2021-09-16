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

import java.util.Scanner;
import pp.projeto.core.City;


public class MainMenu {
    
    /**
     * Builder method MainMenu
     */
    public MainMenu() {
    }
    
    /**
     * Print Main Menu
     * @param city city to be importer or exporter
     */
    public void printMainMenu(City city) {
        
        Scanner scan = new Scanner(System.in);

        System.out.print("-------------------------------"
                + "\n|                             |"
                + "\n|       | Main Menu |         |"
                + "\n|                             |"
                + "\n|                             |"
                + "\n|     1- Import File          |"
                + "\n|                             |"
                + "\n|     2- Export Statistcs     |"
                + "\n|                             |"
                + "\n|     3- Exit                 |"
                + "\n|                             |" 
                + "\n-------------------------------"
                + ""
                + "\nChoose an option: ");

        String opcao = scan.next();

        switch (opcao) {
            case "1":
                ImportMenu iMenu = new ImportMenu();
                
                if (iMenu.printImportMenu(city) == false) {
                    this.printMainMenu(city);
                } else {
                
                this.printMainMenu(city);
                }
                break;
            case "2":
                if (city == null) {
                    
                    System.out.println("Import file first!!");
                    this.printMainMenu(city);
                }
                
                ExporterMenu eMenu = new ExporterMenu();
                
                if (eMenu.printExportMenu(city) == false) {
                    this.printMainMenu(city);
                } else {
                
                this.printMainMenu(city);
                }
                break;
            case "3":
                break;
            default:
                this.printMainMenu(city);
                    
        }
}
}
