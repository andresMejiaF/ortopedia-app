package ortopedia.proyecto;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File carpeta = new File("images/");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }

}
