package kr.or.cmcnu.buchrbot.pojo.edge.bot;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kr.or.cmcnu.buchrbot.pojo.edge.Extras;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Say {
    @JsonProperty("content")
    String content;

    @JsonProperty("extras")
    Extras extras;

    public static Say create(String content){
        Say c = new Say();
        c.setContent(content);
        return c;
    }

    public static Say create(String content, @Nullable Extras extras) {
        Say c = new Say();
        c.setContent(content);
        c.setExtras(extras);
        return c;
    }
}
