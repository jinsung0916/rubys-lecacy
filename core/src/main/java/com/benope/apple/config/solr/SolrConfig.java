package com.benope.apple.config.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.schema.SolrPersistentEntitySchemaCreator;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.util.Collections;

@EnableSolrRepositories(
        basePackages = "com.benope.apple.**.repository",
        schemaCreationSupport = true
)
@Configuration
public class SolrConfig {

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        SolrTemplate template = new SolrTemplate(solrClient);
        template.setSchemaCreationFeatures(Collections.singletonList(SolrPersistentEntitySchemaCreator.Feature.CREATE_MISSING_FIELDS));
        return template;
    }

}
