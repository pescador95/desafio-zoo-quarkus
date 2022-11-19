package desafio.zoo.model;


import java.util.List;

public class Responses {

   public Integer status;

   public Object data;

   public List<Object> dataList;

   public List<String> messages;

    public Responses() {

    }

    public Responses(int i, Object data, List<String> messages) {
        this.status = i;
        this.data = data;
        this.messages = messages;

    }
    public Responses(int i, Object data, List<Object> dataList, List<String> messages) {
        this.status = i;
        this.data = data;
        this.dataList = dataList;
        this.messages = messages;

    }
}