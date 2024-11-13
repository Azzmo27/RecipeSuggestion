package com.example.recipesuggestion.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "role",
        "content",
        "refusal"
})

public class Message {

    @JsonProperty("role")
    private String role;
    @JsonProperty("content")
    private String content;
    @JsonProperty("refusal")
    private Object refusal;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("refusal")
    public Object getRefusal() {
        return refusal;
    }

    @JsonProperty("refusal")
    public void setRefusal(Object refusal) {
        this.refusal = refusal;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}