import java.io.FileWriter;
import java.io.IOException;

public class CreateReport {


    public CreateReport(String addressee,String myMessage,String receiveMessage){
        try {
            FileWriter fileWriter=new FileWriter("C:\\Users\\DELL\\OneDrive\\שולחן העבודה\\binari\\whatsAppReport.txt");
            fileWriter.write("נמען: "+addressee+"\n"+
                                 "הודעה נשלחה: "+myMessage+"\n"+
                                 "הודעה התקבלה: "+receiveMessage);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
