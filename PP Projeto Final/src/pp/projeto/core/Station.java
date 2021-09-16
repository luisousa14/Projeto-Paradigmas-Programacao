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

import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import java.time.LocalDateTime;

public class Station implements IStation {

    private String name;
    private ISensor[] sensor;
    private int max_sensor = 2;
    private int num_sensor;

    /**
     * Builder method without parameters
     */
    public Station() {
    }

    /**
     * Builder method to copy Station
     * @param station station to copy
     */
    public Station(Station station) {
        this.name = station.getName();
        this.sensor = station.getSensors();
        this.max_sensor = station.max_sensor;
        this.num_sensor = station.num_sensor;
    }
    
    /**
     * Builder method Station
     * @param name nameStation
     */
    public Station(String name) {
        this.name = name;
        this.sensor = new Sensor[this.max_sensor];
    }

    /**
     * Getter for name that identifies each Station
     *
     * @return name that identifies each Station
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Adds a new sensor to the station
     *
     * @param is instance to be inserted
     * @return 1.true if the sensor was inserted in the collection storing all
     * sensors
     * <p>
     * 2.false if the sensor already exists
     * @throws StationException if the @param sensor is null
     * @throws SensorException if the sensorId is invalid (i.e. less or more
     * than 10 characters)
     */
    @Override
    public boolean addSensor(ISensor is) throws StationException, SensorException {

        if (is == null) {
            throw new StationException("Parameter can't be null");
        }

        Sensor temp = (Sensor) is;

        if (is.getId().length() != 10 || this.validateSensorIdFromStation(temp) == false) {
            throw new SensorException("Id dont have 10 characters or is invalid");
        }

        if (this.num_sensor == this.max_sensor) {
            this.expandCapacitySensor();
        }

        if (this.findPosSensor(is.getId()) != -1) {
            return false;
        }
        
        this.sensor[this.num_sensor++] = is;
        return true;
    }

    /**
     * Adds a new measurement to a sensor
     *
     * @param string id identifying the sensor
     * @param d measurement value
     * @param ldt measurement date
     * @param string1 measurement unit for which the value was recorded
     * @return 1.true if the measurement was inserted in the collection storing
     * all sensors
     * <p>
     * 2.false if the measurement already exists
     * @throws StationException if:
     * <p>
     * 1.the sensor doesn't exists
     * <p>
     * 2.any parameter is null
     * @throws SensorException if the @param unit is not compatible with the
     * unit pre-defined for the sensor
     * @throws MeasurementException uncatched from measurement if the value is
     * -99
     */
    @Override
    public boolean addMeasurement(String string, double d, LocalDateTime ldt, String string1) throws StationException, SensorException, MeasurementException {

        if (this.verifyStationException(string, d, ldt, string1) == null) {
            throw new StationException("Sensor dont exist or any parameter is null");
        }

        Sensor temp = (Sensor) this.verifyStationException(string, d, ldt, string1);

        if (this.checkUnit(temp, string) == false) {
            throw new SensorException("unit is not compatible with the unit pre-defined for the sensor");
        }

        if (d == -99) {
            throw new MeasurementException("value is -99");
        }

        return this.sensor[this.findPosSensor(string)].addMeasurement(d, ldt, string1);
    }

    /**
     * Return a copy of the existing sensors
     *
     * @return a copy of the existing sensors
     */
    @Override
    public ISensor[] getSensors() {
        ISensor[] temp = new Sensor[this.max_sensor];

        if (temp.length == 0) {
            return null;
        }

        for (int i = 0; i < this.num_sensor; i++) {

            temp[i] = new Sensor((Sensor) this.sensor[i]);
        }

        return temp;
    }

    /**
     * Return a copy of a existing sensor from a @param id
     *
     * @param string identifying the sensor
     * @return a copy of a existing sensor from a @param id
     */
    @Override
    public ISensor getSensor(String string) {
        ISensor temp = null;

        if (string == null) {
            return null;
        }

        for (int i = 0; i < this.num_sensor; i++) {

            if (this.sensor[i].getId().equals(string)) {

                temp = new Sensor((Sensor) this.sensor[i]);
            }
        }

        return temp;
    }

    /**
     * Expand sensor array capacity *2.
     */
    private void expandCapacitySensor() {
        ISensor[] temp = new Sensor[this.max_sensor * 2];

        for (int i = 0; i < this.max_sensor; i++) {
            temp[i] = this.sensor[i];
        }

        this.sensor = temp;
        this.max_sensor *= 2;
    }

    /**
     * Find position in array of sensors from id
     *
     * @param string sensorID
     * @return 1. i if sensor exist
     * <p>
     * 2. -1 if sensor don't exist
     */
    private int findPosSensor(String string) {

        for (int i = 0; i < this.num_sensor; i++) {

            if (this.sensor[i].getId().equals(string)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Verify Station Exception
     *
     * @param string sensorId
     * @param d value measurement
     * @param ldt data measurement
     * @param string1 stationName
     * @return 1.null if the parameters is null or sensor dont exist
     * <p>
     * 2. sensor if exist
     */
    private ISensor verifyStationException(String string, double d, LocalDateTime ldt, String string1) {

        if (ldt == null || string1 == null || string == null) {
            return null;
        }

        ISensor temp = this.getSensor(string);

        if (this.findPosSensor(string) != -1) {
            return null;
        }

        return temp;
    }
    
    /**
     * Return a existing sensor from a id
     * @param string sensorId
     * @return a existing sensor from a id
     */
    protected ISensor getSensorOriginal(String string) {

        for (int i = 0; i < this.num_sensor; i++) {

            if (this.sensor[i].getId().equals(string)) {
                return this.sensor[i];
            }
        }

        return null;
    }
    
    /**
     * Getter for number of sensors in Station.
     * @return number of sensors in Station.
     */
    protected int getNum_Sensor() {
        return this.num_sensor;
    }
    
    /**
    * Validate if the sensor id is valid. The sensor id has a structure and if this structure does not exist the sensor is invaded or have more and less 10 characters.
    * @param is Sensor
    * @return 1.true if the sensor id is valid <p> 2.flase if the sensor id is invalid (the structure dont exist or have more and less 10 characters).
    */
    private boolean validateSensorIdFromStation(ISensor is) {

        String id = is.getId();
       
        int contador = 0;

        if (is.getType() == null) {
            return false;
        }

        for (int i = 0; i < is.getType().getParameters().length; i++) {

            if (is.getParameter() == is.getType().getParameters()[i]) {
                contador++;
            }
        }

        if (contador != 1) {
            return false;
        }

        return true;
    }
    
    /**
     * Check Unit with string unit and sensor.
     *
     * @param is Sensor
     * @param string unit
     * @return 1.false if parameter unit is equals to unit
     * <p>
     * 2.true if unit's are diferent
     */
    private boolean checkUnit(ISensor is, String string) {
        
        if (this.validateSensorIdFromStation(is) == false) {
            return false;
        }

        return is.getParameter().getUnit().equals(Unit.getUnitFromString(string));
    }
}
