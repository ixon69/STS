package kr.or.cmcnu.bucbot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kr.or.cmcnu.bucbot.pojo.edge.bot.Reply;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by thisno on 2016-10-27.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyList {

    @JsonProperty("replies")
    List<Reply> replyList;

}
