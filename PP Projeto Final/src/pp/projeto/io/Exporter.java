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

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Exporter implements IExporter {

    private String fileName;
    private IStatistics[] statistics;
    private Parameter parameter;

    /**
     * Builder method Exporter
     * @param file fileName
     * @param statistics statistics to represent
     * @param parameter parameter
     */
    public Exporter(String file, IStatistics[] statistics, Parameter parameter) {
        this.fileName = file;
        this.statistics = statistics;
        this.parameter = parameter;
    }

    /**
     * Serialize an object to a specific format that can be stored
     * @return the JSON representation
     * @throws IOException java.io.IOException Signals that an I/O exception of
     * some sort has occurred. This class is the general class of exceptions
     * produced by failed or interrupted I/O operations.
     */
    @Override
    public String export() throws IOException {

        if (this.statistics == null) {
            return null;
        }

        //Object principal
        JSONObject resultObject = new JSONObject();
        
        JSONArray labels = new JSONArray();

        JSONObject data = new JSONObject();
        
        JSONArray dataSets = new JSONArray();

        JSONArray data1Array = new JSONArray();
        
        JSONObject label_data = new JSONObject();

        resultObject.put("type", "bar");

        for (int i = 0; i < this.statistics.length; i++) {

            labels.add(this.statistics[i].getDescription());
        }

        data.put("labels", labels);

        label_data.put("label", this.parameter.toString());

        for (int i = 0; i < this.statistics.length; i++) {

            data1Array.add(this.statistics[i].getValue());
        }

        label_data.put("data", data1Array);

        dataSets.add(label_data);

        data.put("datasets", dataSets);
        resultObject.put("data", data);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(resultObject.toJSONString());
        }

        return resultObject.toJSONString();
    }

}
