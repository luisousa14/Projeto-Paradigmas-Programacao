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

import edu.ma02.io.interfaces.IOStatistics;

public class OStatistics implements IOStatistics {
    
    private int numberOfReadMeasurements;
    private int numberOfNewStationsRead;
    private int numberOfStationsRead;
    private int numberOfSensorsRead;
    private int numberOfNewSensorsRead;
    private int numberOfNewMeasurementsRead;
    private String[] exceptions;
    private int numException;
    private int maxException = 2;
    
    /**
     * Builder method OStatistics
     */
    public OStatistics() {
        this.exceptions = new String[this.maxException];
    }
    
    /**
     * Returns the number of Measurements data read
     * @return the number of Measurements data read
     */
    @Override
    public int getNumberOfReadMeasurements() {
        return this.numberOfReadMeasurements;
    }
    
    /**
     * Returns the number of new and unique Stations data read
     * @return the number of new and unique Stations data read
     */
    @Override
    public int getNumberOfNewStationsRead() {
        return this.numberOfNewStationsRead;
    }
    
    /**
     * Returns the number of uniqueStations data read
     * @return the number of uniqueStations data read
     */
    @Override
    public int getNumberOfStationsRead() {
        return this.numberOfStationsRead;
    }

    /**
     * Returns the number of unique Sensors data read
     * @return the number of unique Sensors data read
     */
    @Override
    public int getNumberOfSensorsRead() {
        return this.numberOfSensorsRead;
    }

    /**
     * Returns the number of new and unique Sensors data read
     * @return the number of new and unique Sensors data read
     */
    @Override
    public int getNumberOfNewSensorsRead() {
        return this.numberOfNewSensorsRead;
    }
    
    /**
     * Returns the number of new and unique Measurements data read
     * @return the number of new and unique Measurements data read
     */
    @Override
    public int getNumberOfNewMeasurementsRead() {
        return this.numberOfNewMeasurementsRead;
    }
    
    /**
     * Returns the array of string representing the of exceptions occurred
     * @return the array of string representing the of exceptions occurred
     */
    @Override
    public String[] getExceptions() {
        return this.exceptions;
    }

    /**
     * Add +1 Number of read Measurements
     */
    protected void addNumberOfReadMeasurements() {
        this.numberOfReadMeasurements += 1;
    }

    /**
     * Add +1 Number of new Station read
     */
    protected void addNumberOfNewStationsRead() {
        this.numberOfNewStationsRead += 1;
    }

    /**
     * Add +1 Number of read Station
     */
    protected void addNumberOfStationsRead() {
        this.numberOfStationsRead += 1;
    }

    /**
     * Add +1 Number of read Sensors
     */
    protected void addNumberOfSensorsRead() {
        this.numberOfSensorsRead += 1;
    }

    /**
     * Add +1 Number of new Sensors read
     */
    protected void addNumberOfNewSensorsRead() {
        this.numberOfNewSensorsRead += 1;
    }

    /**
     * Add +1 Number of new Measurements read
     */
    protected void addNumberOfNewMeasurementsRead() {
        this.numberOfNewMeasurementsRead += 1;
    }

    /**
     * Setter the exceptions
     * @param exceptions the exceptions to set
     */
    protected void setExceptions(String exceptions) {
            
        if (this.maxException == this.numException) {
            
            String[] temp = new String[this.maxException * 2];
            
            for (int i = 0; i < this.numException; i++) {
                temp[i] = this.exceptions[i];
            }
            
            this.maxException *= 2;
            this.exceptions = temp;
        }
        
        this.exceptions[this.numException++] = exceptions;

    }
    
    /**
     * Print the report
     */
    protected void relatorioImport() {
        System.out.println("\nNumber of new reads:"
                + "\nStations: " + this.numberOfNewStationsRead
                + "\nSensors: " + this.numberOfNewSensorsRead
                + "\nMeasurements: " + this.numberOfNewMeasurementsRead
                + "\n"
                + "\nNumber of reads:"
                + "\nStations: " + this.numberOfStationsRead
                + "\nSensors: " + this.numberOfSensorsRead
                + "\nMeasurements: " + this.numberOfReadMeasurements
                + "\n"
                + "\nExcpetions:");
        
        for (int i = 0; i < this.numException; i++) {
            System.out.println(this.exceptions[i]);
        }
    }
}
