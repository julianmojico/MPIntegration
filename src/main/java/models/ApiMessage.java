package models;

import com.google.gson.JsonElement;

public class ApiMessage extends ApiResponse {

    private short status;
    private JsonElement content;
    private String objectType;

    public ApiMessage(short status, JsonElement content, String objectType){
        this.status = status;
        this.content = content;
        this.objectType = objectType;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public JsonElement getContent() {
        return content;
    }

    public void setContent(JsonElement content) {
        this.content = content;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }


}
