package fr.ippon.kompetitors.config;

import fr.ippon.kompetitors.web.rest.DialogsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Component
public class AttachmentsConfiguration implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(DialogsResource.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String base = AttachmentsConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (base.lastIndexOf("/target") >=0 ) {
            base = base.substring(0,base.lastIndexOf("/target"));
        } else {
            base = "/home/jhipster";
        }
        String path = "file:" + base + "/attachments/";
        registry
            .addResourceHandler("/attachments/**")
            .addResourceLocations(path)
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}
