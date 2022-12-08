package az.online.listener;

import entity.Auditable;
import java.time.Instant;
import javax.persistence.PrePersist;

public class Listener {

    @PrePersist
    public void prePersist(Auditable auditable) {
        auditable.setCreatedAt(Instant.now());
        auditable.setCreatedBy("Security Context");
    }

}
