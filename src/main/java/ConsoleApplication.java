import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleApplication {

    public static void main(String[] args) {
       try(InputStream is = ConsoleApplication.class.getClassLoader().getResourceAsStream("SsccLabel.pdf")) {
           PDDocument document = PDDocument.load(is);
           PDAcroForm form = document.getDocumentCatalog().getAcroForm();
           PDField title = form.getField("title");
           title.setValue("Hello World");

           PDField sscc = form.getField("sscc");
           sscc.setValue("1234567890");
           form.flatten();
           document.save("./target/sscc-label-test.pdf");
           document.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
