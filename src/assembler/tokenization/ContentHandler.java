package assembler.tokenization;

public interface ContentHandler {
    public Object[] getReturnValueObjects();
    public boolean tokenization(Object...input);
}
