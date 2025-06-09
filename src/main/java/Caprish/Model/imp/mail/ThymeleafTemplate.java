package Caprish.Model.imp.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class ThymeleafTemplate {

    private static TemplateEngine templateEngine;

    @Autowired
    public ThymeleafTemplate(TemplateEngine templateEngine) {
        ThymeleafTemplate.templateEngine = templateEngine;
    }

    public static String processTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}
