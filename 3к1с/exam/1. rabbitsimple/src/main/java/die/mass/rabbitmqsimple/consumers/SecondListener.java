package die.mass.rabbitmqsimple.consumers;

import com.google.gson.Gson;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Paragraph;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import die.mass.rabbitmqsimple.PassportDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@EnableRabbit
@Component
@AllArgsConstructor
public class SecondListener {

	private final Gson gson;
	private  final PdfCreator pdfCreator;
	private final static String queueName = "secondQueue";
	private final ConnectionFactory connectionFactory;

	@SneakyThrows
	public void listen() {
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		String exchangeName = "passports";
		channel.exchangeDeclare(exchangeName, "fanout", false, true, new HashMap<>());
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchangeName, "");
		DeliverCallback deliverCallback = (consumerTag, message) -> {
			var dto = gson.fromJson(new String(message.getBody()), PassportDto.class);
			try {
				var document = pdfCreator.createPdf("Увольнение", dto.getSurname(), dto.getName());
				var content = String.format("Увольте без выплаты выходного пособия %s %s сегодня же", dto.getSurname(), dto.getName());
				document.add(new Paragraph(content).setFont(PdfFontFactory.createFont("src/main/resources/times.ttf","CP1251", true)));
				document.close();
				channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
			} catch (IOException e) {
				System.err.printf("Dto %s destroy all :(%n", dto.toString());
				channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
			}
		};
		channel.basicConsume(queueName, false,  deliverCallback, tag -> {});
	}
}
