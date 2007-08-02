package org.ourproject.kune.docs.client.rpc;

import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DocumentService {
    public ContextDataDTO getContext(String userHash, String contextRef);
    public ContentDataDTO getContent(String userHash, String ctxRef, String docRef);
    public class App {
        private static DocumentServiceAsync ourInstance = null;

        public static synchronized DocumentServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (DocumentServiceAsync) GWT.create(DocumentService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneDocumentService");
            }
            return ourInstance;
        }

        public static void setMock(DocumentServiceAsync mock) {
            ourInstance = mock;
        }
    }
}
