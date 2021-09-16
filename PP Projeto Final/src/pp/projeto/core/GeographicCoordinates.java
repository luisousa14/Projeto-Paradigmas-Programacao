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

import edu.ma02.core.interfaces.IGeographicCoordinates;

public class GeographicCoordinates implements IGeographicCoordinates{
    private double latitude;
    private double longitude;
    
    /**
     * Builder method geographicCoordinates
     * @param latitude latitude
     * @param longitude longitude
     */
    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Getter for latitude.
     * @return latitude.
     */
    @Override
    public double getLatitude() {
        return this.latitude;
    }
    
    /**
     * Getter for longitude.
     * @return longitude.
     */
    @Override
    public double getLongitude() {
        return this.longitude;
    }
    
}
