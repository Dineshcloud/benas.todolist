

package io.github.benas.todolist.web.services;

import io.github.todolist.core.domain.Priority;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.util.StringToEnumCoercion;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * 
 */
public class TodoListModule {
    
    private TodoListModule() {}
    
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "secret");
        configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
    }

    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration) {
        add(configuration, Priority.class);
    }

    private static <T extends Enum> void add(Configuration<CoercionTuple> configuration, Class<T> enumType) {
        configuration.add(CoercionTuple.create(String.class, enumType, StringToEnumCoercion.create(enumType)));
    }

}
