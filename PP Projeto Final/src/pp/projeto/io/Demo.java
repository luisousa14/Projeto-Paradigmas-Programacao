/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package pp.projeto;

import edu.ma02.core.enumerations.AggregationOperator;
import static edu.ma02.core.enumerations.AggregationOperator.COUNT;
import edu.ma02.core.enumerations.Parameter;
import static edu.ma02.core.enumerations.Parameter.NO2;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IExporter;
import edu.ma02.io.interfaces.IImporter;
import java.io.IOException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.MAX;
import java.time.format.DateTimeFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;
import pp.projeto.core.CartesianCoordinates;
import pp.projeto.core.City;
import pp.projeto.core.GeographicCoordinates;
import pp.projeto.io.Exporter;
import pp.projeto.io.Importer;

public class Demo {
    
    //TESTES FEITOS
    
    public static void main(String[] args) {
        try {
            City city = new City("0", "LuisCity");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

            IImporter importer = new Importer();

            try {
                importer.importData(city, "sensorDataT.json");
            } catch (IOException | CityException ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }

            ICartesianCoordinates icc = new CartesianCoordinates(-200, 100, 150);
            IGeographicCoordinates igc = new GeographicCoordinates(10, 2);

            Exporter temp = new Exporter("export.json", city.getMeasurementsByStation(AggregationOperator.COUNT, Parameter.NO2), Parameter.NO2);

            Dashboard.render(new String[]{temp.export()});
            
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

//
//        try {
//            System.out.println(city.addStation("station1"));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addStation("station2"));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addStation("station32"));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addSensor("QA0NO20001", "station1", icc, igc));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addSensor("QA00CO0001", "station1", icc, igc));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addSensor("QA0NO20002", "station1", icc, igc));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addMeasurement("station1", "QA0NO20001", -80, "μg/m3", LocalDateTime.MIN));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MeasurementException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addMeasurement("station1", "QA0NO20001", 80, "μg/m3", LocalDateTime.MAX));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MeasurementException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        LocalDateTime dateTime = LocalDateTime.parse("202005120000", format);
//
//        LocalDateTime dateTime1 = LocalDateTime.parse("202105121800", format);
//
//        try {
//            System.out.println(city.addMeasurement("station1", "QA0NO20001", 70, "μg/m3", dateTime));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MeasurementException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addMeasurement("station1", "QA0NO20001", 151, "μg/m3", dateTime1));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MeasurementException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            System.out.println(city.addMeasurement("station1", "QA00CO0001", -92, "mg/m3", LocalDateTime.MAX));
//        } catch (CityException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (StationException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SensorException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MeasurementException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        System.out.println(city.getStation("station1").getSensor("QA0NO20001").getNumMeasurements());
//
//        ISensor temp = new Sensor("QA0NO20010", igc, icc);
//
//        System.out.println(temp.getParameter());
//
//        
//        System.out.println(city.getMeasurementsBySensor("station1", AggregationOperator.COUNT, NO2)[0].getValue());
//        for (int i = 0; i < city.getSensorsByStation("station1").length; i++) {
//            System.out.println(city.getSensorsByStation("station1")[i].getId());
//            System.out.println(city.getSensorsByStation("station1")[i].getParameter());
//            System.out.println(city.getSensorsByStation("station1")[i].getType());
//            System.out.println(city.getSensorsByStation("station1")[i].getNumMeasurements());
//
//        }
//TESTE LER O ID E DAR PARAMETER E SENSOR TYPE
//        String string = "QA0NO20002";
//        String temp;
//        temp = string.substring(2, 6);
//
//
//        String temp1;
//        temp1 = string.substring(0, 2);
//        switch (temp1) {
//            case "QA":
//                System.out.println("air");
//                break;
//            case "RU":
//                System.out.println(SensorType.NOISE);
//                break;
//            case "ME":
//                System.out.println(SensorType.WEATHER);
//                break;
//        }
//        char[] temp2 = new char[temp.length()];
//        char[] temp3 = new char[temp.length()];
//        int contador = 0;
//
//        for (int i = 0; i < temp.length(); i++) {
//            temp2[i] = temp.charAt(i);
//        }
//        
//        for (int i = 0; i < temp.length(); i++) {
//            
//            if (temp2[i] != '0') {
//                temp3[contador++] = temp2[i];
//            }
//        }
//        
//        String parameter = new String(temp3);
//        
//        System.out.println(parameter);
//        
////        switch (parameter) {
////            case "NO2":
////                System.out.println("sdaasdsda");
////                break;
////            case "CO":
////                System.out.println("co");
////                break;
////        }
////        
//        Sensor sensor = new Sensor(string, igc, icc);
//        
//        sensor.getParameter();
//        
//    }
    }
}
