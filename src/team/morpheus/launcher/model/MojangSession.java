package team.morpheus.launcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MojangSession {

    String sessionToken;
    String Username;
    String UUID;
}
