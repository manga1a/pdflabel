import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleApplication {

    public static void main(String[] args) {
       try(InputStream is = ConsoleApplication.class.getClassLoader().getResourceAsStream("SsccLabel.pdf")) {
           try(PDDocument source = PDDocument.load(is)) {
               try(PDDocument target = new PDDocument()) {
                   LayerUtility layerUtility = new LayerUtility(target);
                   PDFormXObject formX = layerUtility.importPageAsForm(source, 0);
                   for(int i=0; i < 3; ++i) {
                       createPage(formX, target);
                   }

                   target.save("./target/sscc-label-test.pdf");
               }
           }
//           PDDocument document = PDDocument.load(is);
//           PDAcroForm form = document.getDocumentCatalog().getAcroForm();
//           PDField title = form.getField("title");
//           title.setValue("Hello World");
//
//           PDField sscc = form.getField("sscc");
//           sscc.setValue("1234567890");
//           form.flatten();
//           document.save("./target/sscc-label-test.pdf");
//           document.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    private static void createPage(PDFormXObject formX, PDDocument doc) throws IOException {
        PDPage page = new PDPage(PDRectangle.A6);
        doc.addPage(page);

        try(PDPageContentStream contents = new PDPageContentStream(doc, page)) {
            contents.drawForm(formX);
        }
    }
}
