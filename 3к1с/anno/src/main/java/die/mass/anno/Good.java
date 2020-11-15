package die.mass.anno;

@HtmlForm(method = "post", action = "/good")
public class Good {
	@HtmlInput(type = "text", name = "title", placeholder = "Название")
	private String title;
	@HtmlInput(type = "double", name = "price", placeholder = "Цена")
	private String price;
}
