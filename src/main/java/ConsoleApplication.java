import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;

public class ConsoleApplication {
    private static final String UTF_8 = "UTF-8";

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        try(OutputStream outputStream = new FileOutputStream("./target/sscc-label-test.pdf")) {
            Context context = new Context();
            ITextRenderer renderer = new ITextRenderer();

            context.setVariable("name", "Mangala 0");
            String html = templateEngine.process("template", context);

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, false);

            for(int i = 1; i < 3; ++i) {
                context.setVariable("name", String.format("Mangala %d", i));
                html = templateEngine.process("template", context);

                renderer.setDocumentFromString(html);
                renderer.layout();
                renderer.writeNextDocument();
            }

            renderer.finishPDF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }
}
