package die.mass.rabbitmq.consumers;

import com.google.gson.Gson;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Paragraph;
import die.mass.rabbitmq.dto.PassportDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@EnableRabbit
@Component
@AllArgsConstructor
public class FirstFanoutListener implements Consumer {

	private final Gson gson;
	private final PdfCreator pdfCreator;
	private final static String queueName = "firstFanoutQueue";

	@RabbitListener(queues = queueName)
	@SneakyThrows
	public void listen(Message message) {
		var string = new String(message.getBody());
		var dto = gson.fromJson(string, PassportDto.class);
		System.out.println("FFL: " + dto);
		try {
			PDDocument pDDocument = PDDocument.load(new File("pdfs/pattern.pdf"));
			PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
			PDField field = pDAcroForm.getField("name");
			field.setValue(dto.getName());
			field = pDAcroForm.getField("surname");
			field.setValue(dto.getSurname());
			field = pDAcroForm.getField("patronymic");
			field.setValue(dto.getPatronymic());
			field = pDAcroForm.getField("country");
			field.setValue(dto.getBirthCountry());
			field = pDAcroForm.getField("birthYear");
			field.setValue(String.valueOf(dto.getBirthYear()));
			field = pDAcroForm.getField("date");
			field.setValue(dto.getDate());
			field = pDAcroForm.getField("number");
			field.setValue(String.valueOf(dto.getNumber()));
			field = pDAcroForm.getField("service");
			field.setValue("свидетельство");
			pDDocument.save(String.format("pdfs/%s %s %s.pdf", "Свидетельство", dto.getSurname(), dto.getName()));
			pDDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
