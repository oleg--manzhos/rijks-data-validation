package me.manzhos.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllUsersetsResponse {
        private int count;
        private int elapsedMilliseconds;
        @JsonProperty("userSets")
        private List<AllUsersetsModel.UserSet> userSets;
}
