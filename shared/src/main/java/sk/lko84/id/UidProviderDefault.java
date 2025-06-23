package sk.lko84.id;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.stereotype.Component;

@Component
public class UidProviderDefault implements UidProvider {

    private static final ULID ulid = new ULID();

    public String generate() {
        return ulid.nextULID();
    }

}