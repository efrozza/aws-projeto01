package br.com.efrozza.awsprojeto01.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration

// configurar o profile de execu��o no intelliJ
// Menu Run -> Edit configurations
@Profile("!local")
public class SnsConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.sns.topic.product.events.arn}")
    private String productEventTopic;

    @Bean
    public AmazonSNS snsClient(){
        return AmazonSNSAsyncClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    @Bean(name = "productEventTopic")
    public Topic snsProductEventTopic(){
        return new Topic().withTopicArn(productEventTopic);
    }

}
