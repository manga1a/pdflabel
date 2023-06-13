import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;
import java.util.Map;

public class LabelGenerator {
    private static Logger logger = LoggerFactory.getLogger(LabelGenerator.class);
    private static final String UTF_8 = "UTF-8";

    public LabelGenerator() {

    }

    public void generate(String fileName, List<Map<String, Object>> pageVariables) {
        if (pageVariables == null || pageVariables.isEmpty()) {
            return;
        }

        ClassLoader classLoader = getClass().getClassLoader();

        try (OutputStream outputStream = new FileOutputStream(fileName);
             InputStream templateStream = classLoader.getResourceAsStream("sscc_template.xsl");
             InputStream dataStream = classLoader.getResourceAsStream("asn_sample.xml")) {

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent userAgent = fopFactory.newFOUserAgent();

            // set output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outputStream);

            // Load template
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(templateStream));

            // Set value of parameters in stylesheet
            transformer.setParameter("version", "1.0");

            // Input for XSLT transformations
            Source xmlSource = new StreamSource(dataStream);

            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, result);

        } catch (Exception e) {
            logger.error("Error generating label.", e);
        }
    }
}
