/*
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import javax.swing.JFileChooser;




public class leituraaaaaaaaaaA {
  
    public static void main(String args[]) throws Exception{
          String Arquivo = null;
        JFileChooser fileChooser = new JFileChooser();
        File DirInicial= new File(".\\");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setCurrentDirectory(DirInicial);
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION){
                java.io.File file = fileChooser.getSelectedFile( );
                Arquivo =file.getName();
        //////////////////////\\\\\\\\\\\\\\\\\\\\\\\\        
             } 
       //Código para a leitura do arquivo csv     
       CSVReader reader = new CSVReader(new FileReader(Arquivo), ',' , '"' , 1);
       List<String[]> content = reader.readAll();
       System.out.print(content.get(20)[7]);
    }
    
}
*/