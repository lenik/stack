package user.spring;

public class SilentBigBlob
        implements IBigBlob {

    String contents;

    public SilentBigBlob() {
    }

    public SilentBigBlob(String contents) {
        this.contents = contents;
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "SilentBigBlob [contents =" + contents + "]";
    }

}
