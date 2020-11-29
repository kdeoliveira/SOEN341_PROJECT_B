package assembler;

public class Comment{
    private String comment;
    public Comment(String comment){
        this.comment = comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return this.comment;
    }
}
