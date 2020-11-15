package die.mass.rabbitmq.consumers;

import com.google.gson.Gson;
import die.mass.rabbitmq.dto.PassportDto;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@AllArgsConstructor
public class KazakhstanPassportListener implements Consumer {

	private final Gson gson;

	@RabbitListener(queues = "kazakhstanPassportQueue")
	@Override
	public void listen(Message message) {
		var dto = gson.fromJson(new String(message.getBody()), PassportDto.class);
		try {
			PDDocument pDDocument = PDDocument.load(new File("pdfs/pattern.pdf"));
			PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
			PDField field = pDAcroForm.getField("name");
			field.setValue(dto.getName());
			field = pDAcroForm.getField("surname");
			field.setValue(dto.getSurname());
			field = pDAcroForm.getField("patronymic");
			field.setValue(dto.getPatronymic());
			field = pDAcroForm.getField("anotherCountry");
			field.setValue(dto.getBirthCountry());
			field = pDAcroForm.getField("country");
			field.setValue(dto.getBirthCountry());
			field = pDAcroForm.getField("birthYear");
			field.setValue(String.valueOf(dto.getBirthYear()));
			field = pDAcroForm.getField("series");
			field.setValue(String.valueOf(dto.getSeries()));
			field = pDAcroForm.getField("number");
			field.setValue(String.valueOf(dto.getNumber()));
			field = pDAcroForm.getField("date");
			field.setValue(dto.getDate());
			field = pDAcroForm.getField("service");
			field.setValue("паспорт");
			System.out.printf("%s %s %s.pdf started to create\n", "КазПаспорт", dto.getSurname(), dto.getName());
			pDDocument.save(String.format("pdfs/%s %s %s.pdf", "КазПаспорт", dto.getSurname(), dto.getName()));
			pDDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
