package kr.or.cmcnu.buchrbot.pojo.edge;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {

	@JsonProperty("msg")
	Integer msg;

	@JsonProperty("user")
	Integer user;

	@JsonProperty("type")
	Integer type;

	@JsonProperty("len")
	Integer len;

	@JsonProperty("content")
	String content;

	@JsonProperty("file")
	ChatFile file;

    @JsonProperty("extras")
    Extras extras;

    @Nullable
    public String getResponseId() {
        if(extras != null && extras.getExtraV2() != null) {
            return extras.getExtraV2().getResponseId();
        }

        return null;
    }
}
