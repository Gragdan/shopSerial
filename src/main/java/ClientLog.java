import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    List<String[]> logFile = new ArrayList<>();
    String[] header = "productNum amount".split(" ");
    public void log(int productNum, int amount){
       logFile.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }
    public void exportAsCSV(){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("log.csv" ));
            writer.writeNext(header, false);
           writer.writeAll(logFile,false);

writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
