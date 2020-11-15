package die.mass.anno;

import com.google.auto.service.AutoService;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"die.mass.anno.HtmlForm", "die.mass.anno.HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		// получить типы с аннотацией HtmlForm
		Set<? extends Element> forms = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
		for (Element element : forms) {
			// получаем путь с class-файлам
			String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			// создадим путь к html-файлу
			// User.class -> User.html
			path = path + element.getSimpleName().toString() + ".html";
			// формируем путь для записи данных
			Path out = Paths.get(path);
			try {
				Map<String, Object> map = new HashMap<>();
				HtmlForm annotation = element.getAnnotation(HtmlForm.class);
				List<HtmlInput> inputs = element.getEnclosedElements().stream()
						.filter(elem -> elem.getKind().isField())
						.map(elem -> elem.getAnnotation(HtmlInput.class)).collect(Collectors.toList());
				map.put("action", annotation.action());
				map.put("method", annotation.method());
				map.put("inputs", inputs);
				HtmlCreator.createHtml("template.ftl", map, out);
			} catch (TemplateException | IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return true;
	}
}
