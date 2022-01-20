package br.com.efrozza.awsprojeto01.service;

import br.com.efrozza.awsprojeto01.enums.EventType;
import br.com.efrozza.awsprojeto01.model.Product;
import br.com.efrozza.awsprojeto01.model.sns.Envelope;
import br.com.efrozza.awsprojeto01.model.sns.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(
            ProductPublisher.class);

    private AmazonSNS snsClient;
    private Topic productEventsTopic;
    private ObjectMapper objectMapper;

    public ProductPublisher(AmazonSNS snsClient, @Qualifier("productEventsTopic")
            Topic productEnvetsTopic,
                            ObjectMapper objectMapper) {

        this.snsClient = snsClient;
        this.productEventsTopic = productEnvetsTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username) {

        // Configura o product event com os dados do produto recebido
        ProductEvent productEvent = new ProductEvent();
        productEvent.setCode(product.getCode());
        productEvent.setProductId(product.getId());
        productEvent.setUsername(username);

        // Cria um envelope
        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);

        try {

            // seta o conteudo do envelope com o objeto productEvent convertido para String
            envelope.setData(objectMapper.writeValueAsString(productEvent));

            // publica o topico
            snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelope)
            );

        } catch (JsonProcessingException e) {
            LOG.error("Falha ao criar o evento do produto");
        }



    }


}
