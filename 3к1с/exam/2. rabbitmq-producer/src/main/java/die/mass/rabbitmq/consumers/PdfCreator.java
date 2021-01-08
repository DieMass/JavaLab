package die.mass.rabbitmq.consumers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PdfCreator {

	@SneakyThrows
	public Document createPdf(String type, String surname, String name) {
		String path = String.format("pdfs/%s %s %s.pdf", type, surname, name);
		File file = new File(path);
		file.getParentFile().mkdirs();
		PdfWriter writer = new PdfWriter(path);
		PdfDocument pdf = new PdfDocument(writer);
		return new Document(pdf);
	}
}
