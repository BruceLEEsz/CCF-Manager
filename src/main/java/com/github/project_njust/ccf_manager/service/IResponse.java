package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.service.kt.Response;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;

public interface IResponse extends JsonSection {

    public enum Status {
        SUCCESS("SUCCESS"),
        ERROR("ERROR"),
        REFUSE("REFUSE"),
        OVERTIME("OVERTIME");
        private final String key;

        private Status(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    void setStatus(@NotNull Status s);

    @NotNull
    public static IResponse createIResponse(@NotNull Status s){
        return new Response(s);
    }

    @NotNull
    public static IResponse createIResponse(){
        return new Response(Status.SUCCESS);
    }

}
