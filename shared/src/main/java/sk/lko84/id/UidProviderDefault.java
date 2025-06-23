package sk.lko84.id;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.stereotype.Component;

@Component
public class UidGenerator {

    private final ULID ulid = new ULID();

    public String generate() {
        return ulid.nextULID();
    }

    public String generate(String prefix) {
        return prefix + "_" + ulid.nextULID();
    }
}