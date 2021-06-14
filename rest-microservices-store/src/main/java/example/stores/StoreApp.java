/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.stores;

import example.stores.rest.model.PropertyResponse;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring configuration class main application bootstrap point.
 * 
 * @author Oliver Gierke
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StoreApp extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Store.class);
    }

    @PostConstruct
	public void exposeIds() {
	}

	public static void main(String[] args) {
		SpringApplication.run(StoreApp.class, args);
	}

    @Controller
    public static class SimpleStoresController {
        @Autowired
        StoreRepository repository;


        @RequestMapping("/simple/stores")
        @ResponseBody
        List<Store> getStores() {
            Page<Store> all = repository.findAll(new PageRequest(0, 10));
            return all.getContent();
        }
    }

    @Controller
    @RefreshScope
    public class MyRestController {

      private String propertyA;
      private String propertyB;

      @Autowired
      public MyRestController(@Value("${stores.a}") String propertyA, @Value("${stores.b}")String propertyB) {
        this.propertyA = propertyA;
        this.propertyB = propertyB;
      }

      @GetMapping(value = "/properties", produces = "application/json")
      @ResponseBody
      public PropertyResponse getProperty(final HttpServletRequest request) {
        return new PropertyResponse(this.propertyA, this.propertyB);
      }
    }
    
    @Configuration
    @Profile("cloud")
    protected static class CloudFoundryConfiguration {
    	
    	@Bean
    	  public Cloud cloud() {
    	    return new CloudFactory().getCloud();
    	  }

    }

}
