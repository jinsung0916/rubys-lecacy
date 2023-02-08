package com.benope.apple.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class ElasticsearchConfig {

    private static final String ELASTICSEARCH_VERSION = "7.10.2";
    private static final DockerImageName ELASTICSEARCH_IMAGE =
            DockerImageName
                    .parse("docker.elastic.co/elasticsearch/elasticsearch")
                    .withTag(ELASTICSEARCH_VERSION);

    private static final ElasticsearchContainer container;

    static {
        container = new ElasticsearchContainer(ELASTICSEARCH_IMAGE);
        container.start();
    }

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHost() + ":" + container.getFirstMappedPort())
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
