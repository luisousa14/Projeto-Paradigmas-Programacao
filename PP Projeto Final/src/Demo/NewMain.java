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

import static edu.ma02.core.enumerations.AggregationOperator.COUNT;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IImporter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import pp.projeto.Demo;
import pp.projeto.core.CartesianCoordinates;
import pp.projeto.core.City;
import pp.projeto.core.GeographicCoordinates;
import pp.projeto.io.Exporter;
import pp.projeto.io.Importer;

public class NewMain {

    public static void main(String[] args) throws IOException {

        City city = new City("0", "LuisCity");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        
        LocalDateTime data1 = LocalDateTime.parse("202105121800", format);
        LocalDateTime data2 = LocalDateTime.parse("202105131800", format);

        IImporter importer = new Importer();

        try {
            importer.importData(city, "sensorDataT.json");
        } catch (IOException | CityException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        ICartesianCoordinates icc = new CartesianCoordinates(-200, 100, 150);
        IGeographicCoordinates igc = new GeographicCoordinates(10, 2);

        Exporter temp = new Exporter("export.json", city.getMeasurementsByStation(COUNT, Parameter.NO2, data1, data2), Parameter.NO2);

        try {
            System.out.println(temp.export());
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

            Dashboard.render(new String[] {temp.export()});
  
    }

}
