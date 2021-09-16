/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package pp.projeto.core;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.ICityStatistics;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.IStatistics;
import java.time.LocalDateTime;

public class City implements ICity, ICityStatistics {

    private String id;
    private String name;
    private int num_station;
    private int max_station = 2;
    private IStation[] stations;

    /**
     * Builder method city
     * @param id cityId
     * @param name nameCity
     */
    public City(String id, String name) {
        this.id = id;
        this.name = name;
        this.stations = new Station[this.max_station];
    }

    /**
     * Getter for id that identifies each City
     *
     * @return The id of the City
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Getter for name
     *
     * @return The name of the city
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Adds a new station to the city
     *
     * @param string station name that represents the name of the zone in which
     * sensors are installed
     * @return 1.true if the station was inserted in the collection storing all
     * stations
     * <p>
     * 2.false if the station already exists
     * @throws CityException if the @param name is null
     */
    @Override
    public boolean addStation(String string) throws CityException {

        if (string == null) {
            throw new CityException("Paramenters can't be null");
        }

        if (this.num_station == this.max_station) {
            this.expandCapcityStation();
        }

        if (this.findPosStation(string) != -1) {
            return false;
        }

        this.stations[this.num_station++] = new Station(string);
        return true;
    }

    /**
     * Adds a new sensor to a station
     *
     * @param string sensor id
     * @param string1 station name (used to identify the station) in which the
     * sensor will be added
     * @param icc cartesian coordinates in which the sensor is installed
     * @param igc geographic coordinates in which the sensor is installed
     * @return 1.true if the sensor was inserted in the collection storing all
     * sensors
     * <p>
     * 2.false if the sensor already exists
     * @throws CityException if the station doesn't exists or if param
     * stationName parameter is null
     * @throws StationException uncatched from station if the sensor doesn't
     * exists or the sensorId is invalid (i.e. less or more than 10 characters)
     * @throws SensorException uncatched from sensor if the sensorId is invalid
     * (i.e. less or more than 10 characters)
     */
    @Override
    public boolean addSensor(String string, String string1, ICartesianCoordinates icc, IGeographicCoordinates igc) throws CityException, StationException, SensorException {
        IStation tempStation = this.getStation(string1);

        if (tempStation == null || string1 == null) {
            throw new CityException("Station doesn't exist or stationName parameter is null");
        }

        //SENSOR NAO EXISTE NÃO PERCEBI
//        if (string.length() != 10) {
//            throw new StationException("Sensor is invalid");
//        }

        if (string.length() != 10) {
            throw new SensorException("SensorId is invalid");
        }

        if (this.validateSensor(string) == false) {
            return false;
        }

        return this.stations[this.findPosStation(string1)].addSensor(new Sensor(string, igc, icc));
    }

    /**
     * Adds a new measurement to a sensor from a station
     *
     * @param string station name used to identify the station
     * @param string1 sensor id in which the measurement will be associated
     * @param d measurement value
     * @param string2 measurement unit for which the value was recorded
     * @param ldt measurement date for which the value was recorded
     * @return 1.true if the measurement was inserted in the collection storing
     * all sensors
     * <p>
     * 2.false if the measurement already exists
     * @throws CityException if the station doesn't exists or if @param string
     * parameter is null
     * @throws StationException uncatched from station if the sensor doesn't
     * exists
     * @throws SensorException uncatched from sensor if the unit is not
     * compatible with the unit pre-defined for the sensor
     * @throws MeasurementException uncatched from measurement if the value is
     * -99 or any parameter is invalid
     */
    @Override
    public boolean addMeasurement(String string, String string1, double d, String string2, LocalDateTime ldt) throws CityException, StationException, SensorException, MeasurementException {

        if (string1.length() != 10 || string1 == null || string == null || string2 == null || ldt == null) {
            throw new MeasurementException("parameter is invalid");
        }

        if (d == -99) {
            throw new MeasurementException("the value is -99");
        }

        IStation tempStation = (Station) this.getStation(string);
        ISensor tempSensor = tempStation.getSensor(string1);

        if (string == null || tempStation == null) {
            throw new CityException("the station doesn't exists or sation name is null");
        }

        if (tempSensor == null) {
            throw new StationException("the sensor dont'r exist");
        }

        Station temp = (Station) this.stations[this.findPosStation(string)];

        return temp.getSensorOriginal(string1).addMeasurement(d, ldt, string2);
    }

    /**
     * Return a copy of the existing stations
     *
     * @return a copy of the existing stations
     */
    @Override
    public IStation[] getStations() {
        IStation[] temp = new Station[this.max_station];

        for (int i = 0; i < this.num_station; i++) {

            temp[i] = new Station((Station) this.stations[i]);
        }

        if (temp[0] == null) {
            return null;
        }

        return temp;
    }

    /**
     * Return a copy of a existing station from a @param name
     *
     * @param string station name
     * @return a copy of a existing station from a name
     */
    @Override
    public IStation getStation(String string) {

        if (string == null) {
            return null;
        }

        IStation temp = null;

        for (int i = 0; i < this.num_station; i++) {

            if (this.stations[i].getName().equals(string)) {

                temp = new Station((Station) this.stations[i]);
            }
        }

        return temp;
    }

    /**
     * Return a copy of the existing sensors from a station
     *
     * @param string identifying the station
     * @return a copy of the existing sensors
     */
    @Override
    public ISensor[] getSensorsByStation(String string) {

        if (string == null) {
            return null;
        }

        IStation tempStation = this.getStation(string);
        ISensor[] temp = tempStation.getSensors();

        if (temp.length == 0) {
            return temp;
        }

        for (int i = 0; i < temp.length; i++) {

            temp[i] = tempStation.getSensors()[i];
        }

        return temp;
    }

    /**
     * Return a copy of the existing measurements from a sensor
     *
     * @param string identifying the sensor
     * @return a copy of the existing measurements
     */
    @Override
    public IMeasurement[] getMeasurementsBySensor(String string) {

        if (string == null) {
            return null;
        }

        ISensor temp = null;

        for (int i = 0; i < this.num_station; i++) {

            if (this.stations[i].getSensor(string) != null) {

                temp = this.stations[i].getSensor(string);
            }
        }

        return temp.getMeasurements();
    }

    /**
     * Returns a collection of statistics based on the measurements of a station
     * and a date interval
     *
     * @param ao the operator applied to the query
     * @param prmtr the parameter applied to the query
     * @param ldt start date for the results
     * @param ldt1 end date for the results
     * @return the collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao, Parameter prmtr, LocalDateTime ldt, LocalDateTime ldt1) {

        // i = numero da station a ser percorrida.
        // j = numero do sensor a ser percorrido.
        // x = numero da medição.
        
        IStatistics[] tempStatistics = new IStatistics[this.getNumberOfStatisticsByStation(prmtr)];

        for (int i = 0; i < this.num_station; i++) {
            int numOfSensors = this.getSensorsByStation(this.stations[i].getName()).length;

            ISensor[] tempSensor = this.getSensorsByStation(this.stations[i].getName());

            for (int j = 0; j < numOfSensors; j++) {

                if (tempSensor[j] != null) {
                    if (tempSensor[j].getParameter() == prmtr) {

                        if (ldt1.isAfter(ldt) && ldt.isBefore(ldt1)) {

                            switch (ao) {
                                case AVG:
                                    double average = 0;

                                    for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                        average += tempSensor[j].getMeasurements()[x].getValue();
                                    }

                                    average /= tempSensor[j].getNumMeasurements();

                                    tempStatistics[i] = new Statistics(this.stations[i].getName(), average);
                                    break;
                                case COUNT:

                                    tempStatistics[i] = new Statistics(this.stations[i].getName(), tempSensor[j].getNumMeasurements());
                                    break;
                                case MAX:
                                    double max = Double.NEGATIVE_INFINITY;

                                    for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                        if (tempSensor[j].getMeasurements()[x].getValue() > max) {
                                            max = tempSensor[j].getMeasurements()[x].getValue();
                                        }
                                    }

                                    tempStatistics[i] = new Statistics(this.stations[i].getName(), max);
                                    break;
                                case MIN:
                                    double min = Double.POSITIVE_INFINITY;

                                    for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                        if (tempSensor[j].getMeasurements()[x].getValue() < min) {
                                            min = tempSensor[j].getMeasurements()[x].getValue();
                                        }
                                    }

                                    tempStatistics[i] = new Statistics(this.stations[i].getName(), min);
                                    break;
                                default:
                                    return null;
                            }
                        }
                    }
                }
            }
        }
        return tempStatistics;
    }

    /**
     * Returns a collection of statistics based on the measurements of a station
     *
     * @param ao the operator applied to the query
     * @param prmtr the parameter applied to the query
     * @return the collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao, Parameter prmtr) {

        // i = numero da station a ser percorrida.
        // j = numero do sensor a ser percorrido.
        // x = numero da medição.
        IStatistics[] tempStatistics = new IStatistics[this.getNumberOfStatisticsByStation(prmtr)];

        for (int i = 0; i < this.num_station; i++) {

            int numOfSensors = this.getSensorsByStation(this.stations[i].getName()).length;
            ISensor[] tempSensor = this.getSensorsByStation(this.stations[i].getName());

            for (int j = 0; j < numOfSensors; j++) {

                if (tempSensor[j] != null) {
                    if (tempSensor[j].getParameter() == prmtr) {

                        switch (ao) {
                            case AVG:
                                double average = 0;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    average += tempSensor[j].getMeasurements()[x].getValue();
                                }

                                average = average / tempSensor[j].getNumMeasurements();

                                tempStatistics[i] = new Statistics(this.stations[i].getName(), average);
                                break;
                            case COUNT:

                                tempStatistics[i] = new Statistics(this.stations[i].getName(), tempSensor[j].getNumMeasurements());
                                break;
                            case MAX:
                                double max = Double.NEGATIVE_INFINITY;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    if (tempSensor[j].getMeasurements()[x].getValue() > max) {
                                        max = tempSensor[j].getMeasurements()[x].getValue();
                                    }
                                }

                                tempStatistics[i] = new Statistics(this.stations[i].getName(), max);
                                break;
                            case MIN:
                                double min = Double.POSITIVE_INFINITY;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    if (tempSensor[j].getMeasurements()[x].getValue() < min) {
                                        min = tempSensor[j].getMeasurements()[x].getValue();
                                    }
                                }

                                tempStatistics[i] = new Statistics(this.stations[i].getName(), min);
                                break;
                            default:
                                return null;
                        }
                    }
                }
            }
        }
        return tempStatistics;
    }

    /**
     * Returns a collection of statistics based on the measurements of a sensor
     * and a date interval
     *
     * @param string the selected station
     * @param ao the operator applied to the query
     * @param prmtr the parameter applied to the query
     * @param ldt start date for the results
     * @param ldt1 end date for the results
     * @return the collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string, AggregationOperator ao, Parameter prmtr, LocalDateTime ldt, LocalDateTime ldt1) {

        IStation tempStation = this.getStation(string);

        if (tempStation == null) {
            return null;
        }

        int numOfSensors = tempStation.getSensors().length;

        IStatistics[] tempStatistics = new Statistics[numOfSensors];

        // j = numero do sensor a ser percorrido.
        // x = numero da medição.
        ISensor[] tempSensor = this.getSensorsByStation(tempStation.getName());

        for (int j = 0; j < numOfSensors; j++) {

            if (tempSensor[j] != null) {
                if (tempSensor[j].getParameter() == prmtr) {

                    if (ldt1.isAfter(ldt) && ldt.isBefore(ldt1)) {

                        switch (ao) {
                            case AVG:
                                double average = 0;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    average += tempSensor[j].getMeasurements()[x].getValue();
                                }

                                average /= tempSensor[j].getNumMeasurements();

                                tempStatistics[j] = new Statistics(string, average);
                                break;
                            case COUNT:

                                tempStatistics[j] = new Statistics(string, tempSensor[j].getNumMeasurements());
                                break;
                            case MAX:
                                double max = Double.NEGATIVE_INFINITY;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    if (tempSensor[j].getMeasurements()[x].getValue() > max) {
                                        max = tempSensor[j].getMeasurements()[x].getValue();
                                    }
                                }

                                tempStatistics[j] = new Statistics(string, max);
                                break;
                            case MIN:
                                double min = Double.POSITIVE_INFINITY;

                                for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                    if (tempSensor[j].getMeasurements()[x].getValue() < min) {
                                        min = tempSensor[j].getMeasurements()[x].getValue();
                                    }
                                }

                                tempStatistics[j] = new Statistics(string, min);
                                break;
                            default:
                                return null;
                        }
                    }
                }
            }
        }
        return tempStatistics;
    }

    /**
     * Returns a collection of statistics based on the measurements of a sensor
     *
     * @param string the selected station
     * @param ao the operator applied to the query
     * @param prmtr the parameter applied to the query
     * @return the collection of statistics
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string, AggregationOperator ao, Parameter prmtr) {

        IStation tempStation = this.getStation(string);

        if (tempStation == null) {
            return null;
        }

        int numOfSensors = tempStation.getSensors().length;

        IStatistics[] tempStatistics = new Statistics[numOfSensors];


        // j = numero do sensor a ser percorrido.
        // x = numero da medição.
        ISensor[] tempSensor = this.getSensorsByStation(tempStation.getName());

        for (int j = 0; j < numOfSensors; j++) {

            if (tempSensor[j] != null) {
                if (tempSensor[j].getParameter() == prmtr) {

                    switch (ao) {
                        case AVG:
                            double average = 0;

                            for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                average += tempSensor[j].getMeasurements()[x].getValue();
                            }

                            average /= tempSensor[j].getNumMeasurements();

                            tempStatistics[j] = new Statistics(string, average);
                            break;
                        case COUNT:

                            tempStatistics[j] = new Statistics(string, tempSensor[j].getNumMeasurements());
                            break;
                        case MAX:
                            double max = Double.NEGATIVE_INFINITY;

                            for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                if (tempSensor[j].getMeasurements()[x].getValue() > max) {
                                    max = tempSensor[j].getMeasurements()[x].getValue();
                                }
                            }

                            tempStatistics[j] = new Statistics(string, max);
                            break;
                        case MIN:
                            double min = Double.POSITIVE_INFINITY;

                            for (int x = 0; x < tempSensor[j].getNumMeasurements(); x++) {

                                if (tempSensor[j].getMeasurements()[x].getValue() < min) {
                                    min = tempSensor[j].getMeasurements()[x].getValue();
                                }
                            }

                            tempStatistics[j] = new Statistics(string, min);
                            break;
                        default:
                            return null;
                    }
                }
            }
        }
        return tempStatistics;
    }

    /**
     * Expand station array capacity * 2.
     */
    private void expandCapcityStation() {
        IStation[] temp = new Station[this.num_station * 2];

        for (int i = 0; i < this.num_station; i++) {
            temp[i] = this.stations[i];
        }

        this.stations = temp;
        this.max_station *= 2;
    }

    /**
     * Find position in array of stations
     *
     * @param string station name
     * @return 1. i if stations exist
     * <p>
     * 2. -1 if staion don't exist
     */
    private int findPosStation(String string) {

        for (int i = 0; i < this.num_station; i++) {

            if (this.stations[i].getName().equals(string)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Verify if the sensor already exist
     *
     * @param string sensorId
     * @return 1.true if the sensor don´t exist
     * <p>
     * 2.false if the sensor already exist
     */
    private boolean validateSensor(String string) {

        for (int i = 0; i < this.num_station; i++) {

            if (this.stations[i].getSensor(string) != null) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Getter number of statistics by station
     * @param prmtr parameter
     * @return Return number of statistics by station
     */
    private int getNumberOfStatisticsByStation(Parameter prmtr) {

        int contador = 0;

        for (int i = 0; i < this.num_station; i++) {
            int numOfSensors = this.getSensorsByStation(this.stations[i].getName()).length;

            ISensor[] tempSensor = this.getSensorsByStation(this.stations[i].getName());

            for (int j = 0; j < numOfSensors; j++) {
                if (tempSensor[j] != null) {
                    if (tempSensor[j].getParameter() == prmtr) {
                        contador++;
                    }
                }
            }
        }

        return contador;
    }
    
    public int NumOfMes(int number, Parameter parameter) {
        
        int counter = 0;
        
        for (int i = 0; i < this.num_station; i++) { 
           
           IStation[] temp = new IStation[this.num_station];
           
           temp[i] = this.getStations()[i];
           
           ISensor[] sensorTemp = new ISensor[temp[i].getSensors().length];
           
           for (int j = 0; j < sensorTemp.length; j++) {
               
               sensorTemp[j] = temp[i].getSensors()[j];
               
               for (int x = 0; x < sensorTemp[j].getNumMeasurements(); x++) {
                      
                   if (sensorTemp[j].getParameter().equals(parameter) && sensorTemp[j].getMeasurements()[x].getValue() > number) {
                       
                       counter++;          
                   }         
               }         
           }          
        }
        return counter;
    }
}
