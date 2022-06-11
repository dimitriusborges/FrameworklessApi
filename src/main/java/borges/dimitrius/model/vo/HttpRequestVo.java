package borges.dimitrius.model.vo;

public class HttpRequestVo {

    private String context;
    private String addr;
    private String arg;

    public HttpRequestVo(String context, String addr, String arg) {
        this.context = context;
        this.addr = addr;
        this.arg = arg;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }


    @Override
    public String toString() {
        return "HttpRequestVo{" +
                "context='" + context + '\'' +
                ", addr='" + addr + '\'' +
                ", arg='" + arg + '\'' +
                '}';
    }
}
