package com.fees;

import com.fees.paging.PagingAndSortingArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		exposeDirectory("user-photos", registry);
//		exposeDirectory("../category-images", registry);
//		exposeDirectory("../brand-logos", registry);
//		exposeDirectory("../product-images", registry);
//		exposeDirectory("../site-logo", registry);
//	}
//	
//	private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
//		Path path = Paths.get(pathPattern);
//		String absolutePath = path.toFile().getAbsolutePath();
//		
//		String logicalPath = pathPattern.replace("../", "") + "/**";
//				
//		registry.addResourceHandler(logicalPath)
//			.addResourceLocations("file:/" + absolutePath + "/");		
//	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PagingAndSortingArgumentResolver());
	}

}
