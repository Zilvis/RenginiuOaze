package dev.zilvis.renginiuoaze.payload.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String JwtToken;

    public UserInfoResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
