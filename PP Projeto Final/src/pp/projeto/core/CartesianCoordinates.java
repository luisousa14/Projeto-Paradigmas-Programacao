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

import edu.ma02.core.interfaces.ICartesianCoordinates;

public class CartesianCoordinates implements ICartesianCoordinates{
    private double x;
    private double y;
    private double z;
    
    /**
     * Build method cartesianCoordinates
     * @param x value to x
     * @param y value to y
     * @param z value to z
     */
    public CartesianCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Getter for X.
     * @return X.
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * Getter for Y.
     * @return Y.
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Getter for Z.
     * @return Z.
     */
    @Override
    public double getZ() {
        return this.z;
    }
    
}
