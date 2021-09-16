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

import edu.ma02.core.interfaces.IMeasurement;
import java.time.LocalDateTime;

public class Measurement implements IMeasurement {

    private LocalDateTime time;
    private double value;

    /**
     * Builder method without parameters
     */
    public Measurement() {
    }
    
    /**
     * Builder method to copy Measurement
     * @param temp Measurement to copy
     */
    public Measurement(Measurement temp) {
        this.time = temp.getTime();
        this.value = temp.getValue();
    }

    /**
     * Builder method measurement
     * @param time time
     * @param value value
     */
    public Measurement(LocalDateTime time, double value) {
        this.time = time;
        this.value = value;
    }

    /**
     * Getter for time that identifies each Measurement
     *
     * @return time that identifies each Measurement
     */
    @Override
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Getter for value
     *
     * @return value
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Two Measurements are equal if they have been captured at the same time
     * and have the same value
     *
     * @param obj to compare
     * @return true is equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        IMeasurement temp = (Measurement) obj;

        if (!(this.time.equals(temp.getTime()) && this.value == temp.getValue())) {
            return false;
        }

        return true;
    }
}
