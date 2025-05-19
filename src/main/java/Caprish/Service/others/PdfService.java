package Caprish.Service.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public byte[] generarReciboPdf(Map<String, Object> datos) throws Exception {
        // 1) Renderizar template a XHTML
        Context ctx = new Context();
        ctx.setVariables(datos);
        String html = templateEngine.process("recibo", ctx);

        // 2) Convertir XHTML a PDF con Flying Saucer
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        }
    }
}
