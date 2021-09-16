/* 
* Nome: <Francisco Borges Sousa> 
* Número: <8200397> 
* Turma: <LSIRC1T1> 
* 
* Nome: <Luís Borges Sousa> 
* Número: <9200398> 
* Turma: <LEI1T4> 
*/
package menus;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.dashboards.Dashboard;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pp.projeto.core.City;
import pp.projeto.io.Exporter;

public class ExporterMenu {
    
    /**
     * Builder method ExporterMenu
     */
    public ExporterMenu() {
    }

    /**
     * Print Exporter Menu
     * @param city city to exporter the statistics
     * @return 1.true if evrething it's right <p> 2.false if user want to go Main Menu
     */
    public boolean printExportMenu(City city) {

        Scanner scan = new Scanner(System.in);

        String aggregationOperator;
        String parameter;
        String startDate;
        String endDate;

        LocalDateTime startDateF;
        LocalDateTime endDateF;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        System.out.print("-------------------------------"
                + "\n|                             |"
                + "\n|       | Export File |       |"
                + "\n|                             |"
                + "\n|   1- MeasurementByStation   |"
                + "\n|                             |"
                + "\n|   2- MeasurementByStation   |"
                + "\n|      with a time interval   |"
                + "\n|                             |"
                + "\n|   write exit if you want    |"
                + "\n|      go to MainMenu         |"
                + "\n-------------------------------\n"
                + "Choose a option: ");

        String opcao = scan.next();

        switch (opcao) {

            case "1":
                do {
                    
                    System.out.print("Choose Aggregation Operator:");

                    aggregationOperator = scan.next();

                    if (aggregationOperator.equals("exit")) {
                        return false;
                    }

                } while (this.convertAggregationOperator(aggregationOperator) == null);

                do {
                    
                    System.out.print("Choose Parameter:");

                    parameter = scan.next();

                    if (parameter.equals("exit")) {
                        return false;
                    }

                } while (this.convertParameter(parameter) == null);

                Exporter exporter = new Exporter("exportFile.json", city.getMeasurementsByStation(this.convertAggregationOperator(aggregationOperator), this.convertParameter(parameter)), this.convertParameter(parameter));

                try {
                    Dashboard.render(new String[]{exporter.export()});
                } catch (IOException ex) {
                    Logger.getLogger(ExporterMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "2":

                do {
                    do {

                        System.out.println("Choose Start Date(yyyyMMddHHmm): ");
                        startDate = scan.next();

                        if (startDate.equals("exit")) {
                            return false;
                        }

                    } while (startDate.length() != 12 || this.verifyIfStringDateIsOk(startDate) == false);

                    do {

                        System.out.println("Choose End Date(yyyyMMddHHmm): ");
                        endDate = scan.next();

                        if (endDate.equals("exit")) {
                            return false;
                        }

                    } while (endDate.length() != 12 || this.verifyIfStringDateIsOk(endDate) == false);

                    startDateF = LocalDateTime.parse(startDate, format);

                    endDateF = LocalDateTime.parse(endDate, format);

                } while (this.verifyDates(startDateF, endDateF) == false);

                do {
                    
                    System.out.print("Choose Aggregation Operator:");

                    aggregationOperator = scan.next();

                    if (aggregationOperator.equals("exit")) {
                        return false;
                    }

                } while (this.convertAggregationOperator(aggregationOperator) == null);

                do {
                    
                    System.out.print("Choose Parameter:");

                    parameter = scan.next();

                    if (parameter.equals("exit")) {
                        return false;
                    }

                } while (this.convertParameter(parameter) == null);

                Exporter exporterDate = new Exporter("exportFileDate.json", city.getMeasurementsByStation(this.convertAggregationOperator(aggregationOperator), this.convertParameter(parameter), startDateF, endDateF), this.convertParameter(parameter));

                try {
                    Dashboard.render(new String[]{exporterDate.export()});
                } catch (IOException ex) {
                    Logger.getLogger(ExporterMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "exit":
                return false;
            default:
                this.printExportMenu(city);
        }

        return true;
    }
    
    /**
     * Convert the aggregationOperator string to AggregationOperater.
     * @param aggregationOperator aggregationOperator string
     * @return 1.Return the converter AggregationOperater <p> 2.Return null if aggregationOperator string don't corresponds
     */
    private AggregationOperator convertAggregationOperator(String aggregationOperator) {

        switch (aggregationOperator) {
            case "AVG":
                return AggregationOperator.AVG;
            case "COUNT":
                return AggregationOperator.COUNT;
            case "MAX":
                return AggregationOperator.MAX;
            case "MIN":
                return AggregationOperator.MIN;
            default:
                return null;
        }
    }
    
    /**
     * Convert the parameter string to Parameter
     * @param parameter parameter string
     * @return 1.Return the converter Parameter <p> 2.Return null if parameter string don't corresponds
     */
    private Parameter convertParameter(String parameter) {
        switch (parameter) {
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
     * Verify dates
     * @param starDateF startDate
     * @param endDateF endDate
     * @return 1.true if the startDate is earlier than endDate <p> 2.false if the endDate is earlier than startDate
     */
    private boolean verifyDates(LocalDateTime startDateF, LocalDateTime endDateF) {

        if (endDateF.isBefore(startDateF)) {
            return false;
        }

        return true;
    }
    
    /**
     * Verify if the string contain characters
     * @param date date to verify
     * @return 1.true if string don't have characters <p> 2.false if the string have characters
     */
    private boolean verifyIfStringDateIsOk(String date) {

        for (int i = 0; i < date.length(); i++) {

            if (!Character.isDigit(date.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
