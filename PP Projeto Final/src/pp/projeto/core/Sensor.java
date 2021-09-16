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

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import java.time.LocalDateTime;

public class Sensor implements ISensor {

    private String id;
    private SensorType sensorType;
    private Parameter parameter;
    private IGeographicCoordinates geoCoordinates;
    private ICartesianCoordinates cartCoordinates;
    private IMeasurement[] measurement;
    private int num_measurement;
    private int max_measurement = 2;

    /**
     * Builder method without parameters
     */
    public Sensor() {
    }

    /**
     * Builder method to copy Sesnor
     *
     * @param sensor sensor to copy
     */
    public Sensor(Sensor sensor) {
        this.id = sensor.getId();
        this.sensorType = sensor.getType();
        this.parameter = sensor.getParameter();
        this.cartCoordinates = sensor.getCartesianCoordinates();
        this.geoCoordinates = sensor.getGeographicCoordinates();
        this.measurement = sensor.getMeasurements();
        this.max_measurement = sensor.max_measurement;
        this.num_measurement = sensor.num_measurement;
    }

    /**
     * Builder method Sensor
     *
     * @param id sensorId
     * @param temp geographicCoordenates
     * @param temp1 CartesianCoordenates
     */
    public Sensor(String id, IGeographicCoordinates temp, ICartesianCoordinates temp1) {
        this.id = id;
        this.sensorType = this.findType(id);
        this.parameter = this.findParameter(id);
        this.cartCoordinates = temp1;
        this.geoCoordinates = temp;
        this.measurement = new Measurement[this.max_measurement];
    }

    /**
     * Getter for id that identifies each Sensor. The id should have 10
     * characters
     *
     * @return id that identifies each Sensor
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Returns the Sensor sensor Type
     *
     * @return Sensor sensor Type
     */
    @Override
    public SensorType getType() {
        return this.sensorType;
    }

    /**
     * Getter for parameter accepted for the Sensor
     *
     * @return parameter accepted for the Sensor
     */
    @Override
    public Parameter getParameter() {
        return this.parameter;
    }

    /**
     * Getter for cartesian coordinates
     *
     * @return cartesian coordinates for the Sensor
     */
    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return this.cartCoordinates;
    }

    /**
     * Getter for geographic coordinates
     *
     * @return geographic coordinates for the Sensor
     */
    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return this.geoCoordinates;
    }

    /**
     * Adds a new measurement
     *
     * @param d measurement value
     * @param ldt measurement date
     * @param string measurement unit for which the value was recorded
     * @return 1.true if the measurement was inserted in the collection
     * <p>
     * 2.false if the measurement already exists.
     * @throws SensorException if the @param unit is not compatible with the
     * unit pre-defined for the sensor
     * @throws MeasurementException if the value is -99 or any parameter is
     * invalid
     */
    @Override
    public boolean addMeasurement(double d, LocalDateTime ldt, String string) throws SensorException, MeasurementException {

        if (this.verifyMeasurementExcpetion(d, ldt, string) == true) {
            throw new MeasurementException("value is -99 or any parameter is invalid");
        }

        if (this.checkUnit(string) == false) {
            throw new SensorException("unit is not compatible with the unit pre-defined for the sensor");
        }

        if (this.num_measurement == this.max_measurement) {
            this.expandCapcityMeasurement();
        }

        if (this.findPosMeasurement(d, ldt) != -1) {
            return false;
        }

        this.measurement[this.num_measurement++] = new Measurement(ldt, d);
        return true;
    }

    /**
     * Returns the number of measurements captured
     *
     * @return number of measurements captured
     */
    @Override
    public int getNumMeasurements() {
        return this.num_measurement;
    }

    /**
     * Return a copy of the existing measurements
     *
     * @return a copy of the existing measurements
     */
    @Override
    public IMeasurement[] getMeasurements() {
        IMeasurement[] temp = new Measurement[this.max_measurement];

        if (temp.length == 0) {
            return null;
        }

        for (int i = 0; i < this.num_measurement; i++) {

            temp[i] = new Measurement((Measurement) this.measurement[i]);
        }

        return temp;
    }

    /**
     * Check Unit with string unit.
     *
     * @param string unit
     * @return 1.false if parameter unit is equals to unit
     * <p>
     * 2.true if unit's are diferent
     */
    private boolean checkUnit(String string) {

        if (this.validateSensorIdFromSensor() == false) {
            return false;
        }

        return this.parameter.getUnit().equals(Unit.getUnitFromString(string));
    }

    /**
     * Find position of measurement in collection
     *
     * @param d value
     * @param ldt data
     * @return 1.position if measurement exist in collection
     * <p>
     * 2.-1 if measurement don't exist
     */
    private int findPosMeasurement(double d, LocalDateTime ldt) {
        IMeasurement temp = new Measurement(ldt, d);

        for (int i = 0; i < this.num_measurement; i++) {

            if (this.measurement[i].equals(temp)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Expand station array capacity * 2.
     */
    private void expandCapcityMeasurement() {
        IMeasurement[] temp = new Measurement[this.num_measurement * 2];

        for (int i = 0; i < this.num_measurement; i++) {
            temp[i] = this.measurement[i];
        }

        this.measurement = temp;
        this.max_measurement *= 2;
    }

    /**
     * Verify MeasurementExcpetion.
     *
     * @param d value
     * @param ldt data
     * @param string unit
     * @return
     */
    private boolean verifyMeasurementExcpetion(double d, LocalDateTime ldt, String string) {

        if (d == -99 || ldt == null || Unit.getUnitFromString(string) == null) {
            return true;
        }

        return false;
    }

    /**
     * Validate if the sensor id is valid. The sensor id has a structure and if
     * this structure does not exist the sensor is invaded or have more and less
     * 10 characters.
     *
     * @return 1.true if the sensor id is valid
     * <p>
     * 2.flase if the sensor id is invalid (the structure dont exist or have
     * more and less 10 characters).
     */
    private boolean validateSensorIdFromSensor() {

        String id = this.getId();

        int contador = 0;

        if (this.getType() == null) {
            return false;
        }

        for (int i = 0; i < this.getType().getParameters().length; i++) {

            if (this.getParameter() == this.getType().getParameters()[i]) {
                contador++;
            }
        }

        if (contador != 1) {
            return false;
        }

        return true;
    }

    /**
     * Find sensor type based in your id.
     *
     * @param string sensor id
     * @return the sensor type
     */
    private SensorType findType(String string) {
        String temp;
        temp = string.substring(0, 2);

        switch (temp) {
            case "QA":
                return SensorType.AIR;
            case "RU":
                return SensorType.NOISE;
            case "ME":
                return SensorType.WEATHER;
        }

        return null;
    }

    /**
     * Returns the parameter based on a given String
     *
     * @param string the parameter
     * @return the parameter based on a given String
     */
    private static Parameter getParameterFromString(String string) {
        switch (string) {
            case "NO2":
                return Parameter.NO2;
            case "O3":
                return Parameter.O3;
            case "PM25":
                return Parameter.PM2_5;
            case "PM10":
                return Parameter.PM10;
            case "SO2":
                return Parameter.SO2;
            case "C6H6":
                return Parameter.C6H6;
            case "CO":
                return Parameter.CO;
            case "LAEQ":
                return Parameter.LAEQ;
            case "PA":
                return Parameter.PA;
            case "TEMP":
                return Parameter.TEMP;
            case "RU":
                return Parameter.RU;
            case "VD":
                return Parameter.VD;
            case "VI":
                return Parameter.VI;
            case "HM":
                return Parameter.HM;
            case "PC":
                return Parameter.PC;
            case "RG":
                return Parameter.RG;
            default:
                return null;
        }
    }

    /**
     * Find Parameter based in your id
     *
     * @param string sensor id
     * @return the parameter
     */
    private Parameter findParameter(String string) {
        String temp;
        temp = string.substring(2, 6);

        char[] charOld = new char[temp.length()];
        int contador = 0;

        for (int i = 0; i < temp.length(); i++) {
            charOld[i] = temp.charAt(i);
        }

        //se a primeira posiçao for diferente de 0 então o parametro tem 4 caracteres por isso podemos dar logo return á string temp.
        if (charOld[0] != '0') {

            return Sensor.getParameterFromString(temp);

        } else {

            for (int i = 0; i < temp.length(); i++) {

                if (charOld[i] != '0') {
                    contador++;
                }
            }
        }

        int contador2 = 0;
        char[] charNew = new char[contador];

        for (int i = 0; i < temp.length(); i++) {

            if (charOld[i] != '0') {
                charNew[contador2++] = charOld[i];
            }
        }

        String tempParameter = new String(charNew);

        return Sensor.getParameterFromString(tempParameter);
    }
}
