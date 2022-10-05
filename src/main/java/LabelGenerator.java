import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.List;

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

    public void generate(String fileName, List<String> pageValues) throws IOException {
        if (pageValues == null || pageValues.isEmpty()) {
            return;
        }

        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            Context context = new Context();
            ITextRenderer renderer = new ITextRenderer();
            renderer.getSharedContext().setReplacedElementFactory(
                    new BarcodeReplacedElementFactory(
                            renderer.getSharedContext().getReplacedElementFactory()
                    )
            );

            // Set resources as working directory
            String baseUrl = FileSystems
                    .getDefault()
                    .getPath("src", "main", "resources")
                    .toUri()
                    .toURL()
                    .toString();

            context.setVariable("name", pageValues.get(0));
            String html = templateEngine.process("template", context);

            renderer.setDocumentFromString(html, baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream, false);

            for (int i = 1; i < pageValues.size(); ++i) {
                context.setVariable("name", pageValues.get(i));
                html = templateEngine.process("template", context);

                renderer.setDocumentFromString(html, baseUrl);
                renderer.layout();
                renderer.writeNextDocument();
            }

            renderer.finishPDF();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
