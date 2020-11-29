package assembler.tokenization;
import java.util.List;

public interface Lexical {
    public boolean tokenization(CharSequence...input);
    public List<CharSequence> getTokens();
    public void reset();
}
