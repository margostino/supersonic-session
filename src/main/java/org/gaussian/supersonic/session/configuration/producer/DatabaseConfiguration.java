package org.gaussian.supersonic.session.configuration.producer;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.gaussian.supersonic.session.qualifiers.SessionDBTableName;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class DatabaseConfiguration {

    @ConfigProperty(name = "database.hostname")
    private String hostname;

    private String getEndpoint() {
        return "http://" + hostname + ":8000/";
    }

    @Produces
    @Singleton
    public AmazonDynamoDBAsync localDynamoDbClient() {
        String region = "eu-west-1";
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(getEndpoint(), region);
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
        String region = "eu-west-1";
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(getEndpoint(), region);
        AmazonDynamoDBAsync amazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder.standard()
                                                                                  .withEndpointConfiguration(endpointConfiguration)
                                                                                  .build();

        return new DynamoDBMapper(amazonDynamoDBAsync);
    }

}
