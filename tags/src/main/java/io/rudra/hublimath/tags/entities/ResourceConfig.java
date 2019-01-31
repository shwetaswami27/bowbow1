package io.rudra.hublimath.tags.entities;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static io.rudra.hublimath.tags.entities.Constants.PRODUCT;
import static io.rudra.hublimath.tags.entities.Constants.UPLOADED_inGALLERY;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //System.out.println("Image path in config -"+imagePath);
        registry.addResourceHandler("/products/**").addResourceLocations(new String[]{"file:"+PRODUCT});
        registry.addResourceHandler("/gallery/**").addResourceLocations(new String[]{"file:"+UPLOADED_inGALLERY});
    }
}