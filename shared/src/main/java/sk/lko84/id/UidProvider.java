package sk.lko84.id;

public interface UidProvider {
    String generate();
    String generate(String prefix);
}
