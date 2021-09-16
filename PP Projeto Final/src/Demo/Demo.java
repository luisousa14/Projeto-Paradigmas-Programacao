/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package Demo;

import menus.MainMenu;
import pp.projeto.core.City;

public class Demo {

    public static void main(String[] args) {

        City city = new City("0", "City");

        MainMenu menu = new MainMenu();

        menu.printMainMenu(city);
    }
}
