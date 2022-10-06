import org.krysalis.barcode4j.impl.code128.EAN128;
import org.krysalis.barcode4j.impl.code128.EAN128AI;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.w3c.tidy.Tidy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleApplication {
    private static final String UTF_8 = "UTF-8";

    public static void main(String[] args) {

        LabelGenerator labelGenerator = new LabelGenerator();
        List<String> pageValues = new ArrayList<>(Arrays.asList("Page 1", "Page 2", "Page 3"));
        try {
            labelGenerator.generate("./target/sscc-label-test.pdf", pageValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage getBarcodeImage(String text) {
        EAN128Bean generator = new EAN128Bean();
        generator.setGroupSeparator(' ');
        generator.setFontSize(2d);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        generator.generateBarcode(canvas, text);
        return canvas.getBufferedImage();
    }
}
