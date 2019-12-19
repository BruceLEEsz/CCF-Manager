package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

        public class DownLoadFinalList extends Service {
            public DownLoadFinalList(){
                super("downLoadFinalList",UserType.ADMIN);
            }

            @Override
            public @NotNull IResponse onRequest(@NotNull ISubmitData input){
                String fileId=input.getData().getString("fileID");
                String url = null;


        IResponse res=IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("url",url);
        return res;

    }
}
