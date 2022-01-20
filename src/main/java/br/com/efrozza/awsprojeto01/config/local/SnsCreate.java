package br.com.efrozza.awsprojeto01.config.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
// na aws em produção o profile se chama "default", então essa classe não é executada
public class SnsCreate {

    private static final Logger LOG = LoggerFactory.getLogger(SnsCreate.class);

    private final String productEventTopic;
    private final AmazonSNS snsClient;

    public SnsCreate(String productEventTopic, AmazonSNS snsClient) {
        this.productEventTopic = productEventTopic;
        this.snsClient = AmazonSNSClient.builder()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                    Regions.US_EAST_1.getName()))
            .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        // mesmo nome usado no application.properties aws.sns.topic.product.events.arn=product-events
        CreateTopicRequest createTopicRequest = new CreateTopicRequest("product-events");
        productEventTopic = this.snsClient.createTopic(createTopicRequest).getTopicArn();

        LOG.info("SNS topic ARN: {}", this.productEventTopic);
    }


    @Bean
    public AmazonSNS getSnsClient() {
        return this.snsClient;
    }

    @Bean(name = "productEventTopic")
    public Topic snsProductEventTopic() {
        return new Topic().withTopicArn(productEventTopic);
    }


}
