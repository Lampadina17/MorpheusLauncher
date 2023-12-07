package team.morpheus.launcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MorpheusSession {

    final String sessionToken;
    final String productID;
    final String hwid;
}
