package project.services.users.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import project.dto.user.UserVerifiedDto;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageContentServiceImpl implements MessageContentService {

    @Autowired
    private Configuration configuration;

    @Override
    public String getHelloPageText(UserVerifiedDto userVerifiedDto, MimeMessage message) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("token", userVerifiedDto.getToken());
            model.put("name", userVerifiedDto.getName());
            Template template = configuration.getTemplate("verification.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getImagePage(String email, String imagePath, MimeMessage message) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("email", email);
            model.put("image", imagePath);
            Template template = configuration.getTemplate("image.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
