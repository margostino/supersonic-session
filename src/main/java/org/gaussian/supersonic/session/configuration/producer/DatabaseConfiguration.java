package org.gaussian.supersonic.session.configuration.producer;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.gaussian.supersonic.session.qualifiers.SessionDBTableName;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class DatabaseConfiguration {

    @Produces
    @Singleton
    public AmazonDynamoDBAsync localDynamoDbClient() {
        String endpoint = "http://localhost:8000/";
        String region = "eu-west-1";
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        return AmazonDynamoDBAsyncClientBuilder.standard()
                                               .withEndpointConfiguration(endpointConfiguration)
                                               .build();
    }

    @Produces
    @Singleton
    @SessionDBTableName
    public String tableName() {
        return "supersonic-sessions";
    }

    @Produces
    @Singleton
    public DynamoDBMapper localDynamoDbMapper() {
        String endpoint = "http://localhost:8000/";
        String region = "eu-west-1";
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AmazonDynamoDBAsync amazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder.standard()
                                                                                  .withEndpointConfiguration(endpointConfiguration)
                                                                                  .build();

        return new DynamoDBMapper(amazonDynamoDBAsync);
    }

}
