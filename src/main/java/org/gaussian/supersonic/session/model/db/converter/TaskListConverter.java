package org.gaussian.supersonic.session.model.db.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.gaussian.supersonic.session.model.Task;
import org.gaussian.supersonic.session.serializer.JsonCodec;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.gaussian.supersonic.session.serializer.JsonCodec.decode;

public class TaskListConverter implements DynamoDBTypeConverter<List<String>, List<Task>> {

    @Override
    public List<String> convert(List<Task> tasks) {
        return tasks.stream()
                    .map(JsonCodec::encode)
                    .collect(toList());
    }

    @Override
    public List<Task> unconvert(List<String> tasks) {
        return tasks.stream()
                    .map(experiment -> decode(experiment, Task.class))
                    .collect(toList());
    }

}
