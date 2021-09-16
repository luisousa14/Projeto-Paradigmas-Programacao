/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package pp.projeto.io;

import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import pp.projeto.core.CartesianCoordinates;
import pp.projeto.core.GeographicCoordinates;

public class Importer implements IImporter {
    
    /**
     * Builder metohd Importer
     */
    public Importer() {
    }

    /**
     * Reads the input JSON file
     *
     * @param icity The city in which the file data will be read
     * @param string the file path
     * @return the IO statistics for the data read
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if the file cannot be read
     * @throws CityException if the city is null
     */
    @Override
    public IOStatistics importData(ICity icity, String string) throws FileNotFoundException, IOException, CityException {

        if (icity == null) {
            throw new CityException("The city is null");
        }

        //FileNotFoundException
        OStatistics statistics = new OStatistics();

        JSONArray array = (JSONArray) JSONValue.parse(new FileReader(string));
        
        if (array == null) {
            throw new FileNotFoundException("The file is not found");
        }

        for (int i = 0; i < array.size(); i++) {

            JSONObject resultObject = (JSONObject) array.get(i);
            
            String[] parameters = new String[]{"id", "date", "dateStandard", "value", "unit", "address", "coordinates"};

            if (this.verifyParameters(resultObject, parameters) == false) {
                throw new IOException("the file cannot be read");
            }
            
            String nameStation = (String) resultObject.get("address");
            String idSensor = (String) resultObject.get("id");
            String unit = (String) resultObject.get("unit");
            double value = Double.parseDouble(resultObject.get("value").toString());
            String dateTemp = (String) resultObject.get("date");

            JSONObject coordenate = (JSONObject) resultObject.get("coordinates");

            String[] parametersCoordenate = new String[]{"x", "y", "z", "lat", "lng"};

            if (this.verifyParameters(coordenate, parametersCoordenate) == false) {
                throw new IOException("the file cannot be read");
            }

            double x = Double.parseDouble(coordenate.get("x").toString());
            double y = Double.parseDouble(coordenate.get("y").toString());
            double z = Double.parseDouble(coordenate.get("z").toString());
            ICartesianCoordinates cc = new CartesianCoordinates(x, y, z);

            double lat = Double.parseDouble(coordenate.get("lat").toString());
            double lng = Double.parseDouble(coordenate.get("lng").toString());
            IGeographicCoordinates gc = new GeographicCoordinates(lat, lng);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTemp, format);

            try {

                if (icity.addStation(nameStation) == true) {
                    statistics.addNumberOfNewStationsRead();
                    statistics.addNumberOfStationsRead();
                } else {
                    statistics.addNumberOfStationsRead();
                }

                if (icity.addSensor(idSensor, nameStation, cc, gc) == true) {
                    statistics.addNumberOfNewSensorsRead();
                    statistics.addNumberOfSensorsRead();
                } else {
                    statistics.addNumberOfSensorsRead();
                }

                if (icity.addMeasurement(nameStation, idSensor, value, unit, dateTime) == true) {
                    statistics.addNumberOfNewMeasurementsRead();
                    statistics.addNumberOfReadMeasurements();
                } else {
                    statistics.addNumberOfReadMeasurements();
                }

            } catch (MeasurementException m) {
                statistics.addNumberOfReadMeasurements();
                statistics.setExceptions("MeasurementException");
            } catch (SensorException m) {
                statistics.addNumberOfSensorsRead();
                statistics.setExceptions("SensorException");
            } catch (StationException m) {
                statistics.addNumberOfStationsRead();
                statistics.setExceptions("StationException");
            }
        }
        
        statistics.relatorioImport();
        
        return statistics;
    }
    
    /**
     * Verify if all keys exist in json Object.
     * @param object json object
     * @param parameters keys for validation
     * @return 1.false if keys not valid <p> 2.true if keys are valid.
     */
    private boolean verifyParameters(JSONObject object, String[] parameters) {

        if (object == null) {
            return false;
        }
        
        for (int i = 0; i < parameters.length; i++) {

            if (!object.containsKey(parameters[i])) {
                return false;
            }
        }

        return true;
    }
}
