package org.ourproject.kune.platf.integration;

import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.wiki.server.WikiServerModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class IntegrationTestHelper {

    public static Injector createInjector() {
        final Injector injector = Guice.createInjector(new PlatformServerModule(), new DocumentServerModule(),
                new ChatServerModule(), new WikiServerModule(), new AbstractModule() {
                    @Override
                    protected void configure() {
                        bindScope(SessionScoped.class, Scopes.SINGLETON);
                        bindScope(RequestScoped.class, Scopes.SINGLETON);
                        // test: use
                        // memory
                        // test_db: use
                        // mysql
                        bindConstant().annotatedWith(JpaUnit.class).to("test_db");
                        bindConstant().annotatedWith(PropertiesFileName.class).to("kune.properties");
                        bind(HttpServletRequest.class).to(HttpServletRequestMocked.class);
                    }
                });
        return injector;
    }

    public IntegrationTestHelper(final Object... tests) {
        final Injector injector = createInjector();
        injector.getInstance(KunePersistenceService.class).start();
        for (final Object test : tests) {
            injector.injectMembers(test);
        }
    }
}
