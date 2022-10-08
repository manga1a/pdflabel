import com.lowagie.text.Image;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(BarcodeReplacedElementFactory.class);
    private final ReplacedElementFactory superFactory;
    private final EAN128Bean generator;

    public BarcodeReplacedElementFactory(ReplacedElementFactory superFactory) {
        this.superFactory = superFactory;

        generator = new EAN128Bean();
        generator.setGroupSeparator(' ');
        generator.setFontSize(2d);
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String node = element.getNodeName();
        String src = element.getAttribute("src");
        // Replace any <img src="00393123450000000013"/> with the binary data of barcode image
        if(!"img".equals(node)) {
            return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
        }

        try {
            BufferedImage bufferedImage = generateBarcodeImage(src);
            Image image = Image.getInstance(bufferedImage, Color.BLACK);
            final FSImage fsImage = new ITextFSImage(image);

            if ((cssWidth != -1) || (cssHeight != -1)) {
                fsImage.scale(cssWidth, cssHeight);
            }

            return new ITextImageElement(fsImage);
        } catch (Exception e) {
            logger.error("Barcode generation exception.", e);
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
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        generator.generateBarcode(canvas, text);
        return canvas.getBufferedImage();
    }
}
