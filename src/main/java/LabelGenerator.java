import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.List;
import java.util.Map;

public class LabelGenerator {
    private static final String UTF_8 = "UTF-8";
    private final ClassLoaderTemplateResolver templateResolver;
    private final TemplateEngine templateEngine;

    public LabelGenerator() {
        this.templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public void generate(String fileName, List<Map<String, Object>> pageVariables) throws IOException {
        if (pageVariables == null || pageVariables.isEmpty()) {
            return;
        }

        try (OutputStream outputStream = new FileOutputStream(fileName)) {

            ITextRenderer renderer = new ITextRenderer();
            renderer.getSharedContext().setReplacedElementFactory(
                    new BarcodeReplacedElementFactory(
                            renderer.getSharedContext().getReplacedElementFactory()
                    )
            );

            final String template = "sscc_template";
            Context context = new Context();

            for (int i = 0; i < pageVariables.size(); ++i) {
                context.clearVariables();
                context.setVariables(pageVariables.get(i));
                String html = templateEngine.process(template, context);

                renderer.setDocumentFromString(html);
                renderer.layout();
                if (i == 0) {
                    renderer.createPDF(outputStream, false);
                } else {
                    renderer.writeNextDocument();
                }
            }

            renderer.finishPDF();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
