package org.gaussian.supersonic.session.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.gaussian.supersonic.session.model.Task;
import org.gaussian.supersonic.session.validation.BaseSessionChecks;
import org.gaussian.supersonic.session.validation.CreateSessionChecks;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Builder
@JsonInclude(NON_EMPTY)
@JsonDeserialize(builder = UpdateSessionRequest.UpdateSessionRequestBuilder.class)
public class UpdateSessionRequest {

    @ApiModelProperty(value = "The applicable task lines (max 1000)", required = true, position = 9)
//    @Valid
//    @NotNull(groups = CreateSessionChecks.class)
//    @Size(min = 1, max = 1000, groups = BaseSessionChecks.class)
    public final List<Task> tasks;

    @JsonIgnore
    public boolean isEmpty() {
        return this.equals(builder().build());
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class UpdateSessionRequestBuilder {
    }

}
