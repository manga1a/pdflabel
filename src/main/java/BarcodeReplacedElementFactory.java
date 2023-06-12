import com.lowagie.text.Image;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BarcodeReplacedElementFactory implements ReplacedElementFactory {
    private final ReplacedElementFactory superFactory;
    private final EAN128Bean generator;
    private static final int DPI = 300;

    public BarcodeReplacedElementFactory(ReplacedElementFactory superFactory) {
        this.superFactory = superFactory;

        generator = new EAN128Bean();
        generator.setModuleWidth(UnitConv.in2mm(30d / (double) DPI));
        generator.setBarHeight(UnitConv.in2mm(2250d / (double) DPI));
        generator.doQuietZone(true);
        generator.setFontSize(UnitConv.in2mm(300d / (double) DPI));
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String node = element.getNodeName();
        if(!"img".equals(node)) {
            return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
        }

        // Replace any <img src="00393123450000000013"/> with barcode image
        String src = element.getAttribute("src");
        try {
            BufferedImage bufferedImage = generateBarcodeImage(src);
            Image image = Image.getInstance(bufferedImage, Color.BLACK);
            final FSImage fsImage = new ITextFSImage(image);

            return new ITextImageElement(fsImage);
        } catch (Exception e) {
            throw new RuntimeException("There was a problem generating barcode image.", e);
        }
    }

    @Override
    public void reset() {
        this.superFactory.reset();
    }

    @Override
    public void remove(Element element) {
        this.superFactory.remove(element);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        this.superFactory.setFormSubmissionListener(listener);
    }

    private BufferedImage generateBarcodeImage(String text) {
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(DPI, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        generator.generateBarcode(canvas, text);
        return canvas.getBufferedImage();
    }
}
