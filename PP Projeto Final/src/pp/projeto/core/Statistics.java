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

import edu.ma02.core.interfaces.IStatistics;

public class Statistics implements IStatistics{
    private String description;
    private double value;
    
    /**
     * Builder method statistics
     * @param string description
     * @param value value
     */
    public Statistics(String string, double value) {
        this.description = string;
        this.value = value;
    }
    
    /**
     * Return the description of a result (e.g. StationName)
     * @return the description of a result (e.g. StationName)
     */
    @Override
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Return the value of a result (e.g.50)
     * @return the value of a result (e.g.50)
     */
    @Override
    public double getValue() {
        return this.value;
    }
    
}
