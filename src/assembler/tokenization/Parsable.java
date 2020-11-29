package assembler.tokenization;

import java.util.List;
import assembler.Vertex;

public interface Parsable {
    public boolean parse(Vertex<?>...object);
    public List<CharSequence> getReturnValueObjects();
    public String getTypeEBNF();


    public default boolean parse(CharSequence...object){
        return parse(Vertex.class.cast(object));
    }
}
