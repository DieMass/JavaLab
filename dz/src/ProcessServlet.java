import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("process.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        String i = req.getParameter("input");
        HttpSession session = req.getSession();
        Integer result = 0;
        Integer count = (Integer) session.getAttribute("coint");
        switch (i) {
            case "Characters count": {
                result = characters(text);
                break;
            }
            case "Word count": {
                result = words(text);
                break;
            }
            case "Sentence count": {
                result = sentenses(text);
                break;
            }
            case "Paragraph count": {
                result = paragraphs(text);
                break;
            }
        }

        if(count == null) {
            session.setAttribute("count",1);
        } else {
            session.setAttribute("count", count + 1);
        }
        session.setAttribute("result", result);

        resp.sendRedirect("/result");
    }

    private Integer paragraphs(String text) {
        Pattern pattern = Pattern.compile("\n");
        Matcher matcher = pattern.matcher(text);
        int count = 1;
        while (matcher.find()){
            count++;
        }
        return count;
    }

    private Integer sentenses(String text) {
        Pattern pattern = Pattern.compile("!|\\.|\\?");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()){
            count++;
        }
        return count;
    }

    private Integer words(String text) {
        Pattern pattern = Pattern.compile(" ");
        Matcher matcher = pattern.matcher(text);
        int count = 1;
        while (matcher.find()){
            count++;
        }
        return count;
    }

    private Integer characters(String text) {
        return text.length();
    }
}
