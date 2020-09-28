package die.mass.rabbitmq.consumers;

import com.google.gson.Gson;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Paragraph;
import die.mass.rabbitmq.PassportDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@AllArgsConstructor
public class ThirdListener {

	private final Gson gson;
	private final PdfCreator pdfCreator;
	private final static String queueName = "thirdQueue";

	@RabbitListener(queues = queueName)
	@SneakyThrows
	public void listen(Message message) {
		var dto = gson.fromJson(new String(message.getBody()), PassportDto.class);
		var document = pdfCreator.createPdf("Отчисление", dto.getSurname(), dto.getName());
		String content = String.format("Прошу поздравить уважаемого %s %s с ащислением из-за неуспеваемости", dto.getSurname(), dto.getName());
		document.add(new Paragraph(content).setFont(PdfFontFactory.createFont("src/main/resources/times.ttf","CP1251", true)));
		document.close();
	}
}
