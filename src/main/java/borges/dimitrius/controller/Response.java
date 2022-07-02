package borges.dimitrius.controller;

public class Response {

    private int code;
    private String body;

    public Response(){}

    public Response(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
