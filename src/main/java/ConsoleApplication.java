import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConsoleApplication {

    public static void main(String[] args) {
       try(InputStream is = ConsoleApplication.class.getClassLoader().getResourceAsStream("SsccLabel.pdf")) {
           try(PDDocument sourceDoc = PDDocument.load(is)) {
               PDAcroForm sourceForm = sourceDoc.getDocumentCatalog().getAcroForm();
               try(PDDocument targetDoc = new PDDocument()) {
                   PDAcroForm targetForm = new PDAcroForm(targetDoc, new COSDictionary());
                   targetForm.setDefaultResources(sourceForm.getDefaultResources());
                   targetForm.setDefaultAppearance(sourceForm.getDefaultAppearance());

                   List<PDField> fields = new ArrayList<>();

                   createPage(0, sourceForm, targetForm, targetDoc, fields);

                   targetForm.setFields(fields);
                   targetForm.flatten();
                   targetDoc.getDocumentCatalog().setAcroForm(targetForm);
                   targetDoc.save("./target/sscc-label-test.pdf");
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

    private static void createPage(int idx, PDAcroForm sourceForm, PDAcroForm targetForm, PDDocument targetDoc, List<PDField> fields) throws IOException {
        PDPage page = new PDPage(PDRectangle.A6);
        targetDoc.addPage(page);

        PDTextField sourceField = (PDTextField) sourceForm.getField("title");
        PDTextField targetField = new PDTextField(targetForm);
        targetField.setDefaultAppearance(sourceField.getDefaultAppearance());
        List<PDAnnotationWidget> widgetList = new ArrayList<>();
        for(PDAnnotationWidget w : sourceField.getWidgets()) {
            PDAnnotationWidget widget = new PDAnnotationWidget();
            widget.getCOSObject().setString(COSName.DA, w.getCOSObject().getString(COSName.DA));
            widget.setRectangle(w.getRectangle());
            widgetList.add(widget);
        }
        targetField.setQ(sourceField.getQ());
        targetField.setWidgets(widgetList);
        targetField.setValue("Hello World");
        targetField.setPartialName("title-0");

        fields.add(targetField);
        page.getAnnotations().addAll(targetField.getWidgets());
    }

    private static void createPage(PDFormXObject formX, PDDocument doc) throws IOException {
        PDPage page = new PDPage(PDRectangle.A6);
        doc.addPage(page);

        try(PDPageContentStream contents = new PDPageContentStream(doc, page)) {
            contents.drawForm(formX);
        }
    }
}
