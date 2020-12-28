package org.gaussian.supersonic.session.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import org.gaussian.supersonic.session.validation.BaseSessionChecks;
import org.gaussian.supersonic.session.validation.CreateSessionChecks;
import org.gaussian.supersonic.session.validation.ValidCountry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Builder
@JsonInclude(NON_EMPTY)
@JsonDeserialize(builder = Task.TaskBuilder.class)
public class Task {

//    @Pattern(regexp = "^[A-Za-z]{2}$", groups = BaseSessionChecks.class)
//    @NotNull(groups = CreateSessionChecks.class)
    public final String name;

    //@Pattern(regexp = "^[A-Za-z]{2}(?:-[A-Za-z]{2})*$", groups = BaseSessionChecks.class)
    public final Instant createdAt;

    //@Pattern(regexp = "^[A-Za-z]{2}(?:-[A-Za-z]{2})*$", groups = BaseSessionChecks.class)
    public final TaskStatus status;

    @JsonIgnore
    public boolean isEmpty() {
        return this.equals(builder().build());
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class TaskBuilder {
    }

    @Override
    public int hashCode() {
        return name.hashCode() * status.hashCode() * status.hashCode();
    }
}
