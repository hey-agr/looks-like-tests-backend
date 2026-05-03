package ru.agr.backend.looksliketests;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.TypeReference;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class NativeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(@NonNull RuntimeHints hints, ClassLoader classLoader) {
        // Hibernate DeprecationLogger for GraalVM native-image
        hints.reflection().registerType(
                TypeReference.of("org.hibernate.internal.log.DeprecationLogger"),
                MemberCategory.values()
        );

        // Hibernate Dialect logging
        hints.reflection().registerType(
                TypeReference.of("org.hibernate.internal.log.DeprecationLogger_$logger"),
                MemberCategory.values()
        );

        // Hibernate function array support
        hints.reflection().registerType(
                TypeReference.of("org.hibernate.dialect.function.array.AbstractArrayContainsFunction"),
                MemberCategory.values()
        );

        // JBoss Logging
        hints.reflection().registerType(
                TypeReference.of("org.jboss.logging.Logger"),
                MemberCategory.values()
        );
        hints.reflection().registerType(
                TypeReference.of("org.jboss.logging.Log4j2Logger"),
                MemberCategory.values()
        );
        hints.reflection().registerType(
                TypeReference.of("org.jboss.logging.Log4j2LoggerProvider"),
                MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INVOKE_DECLARED_METHODS,
                MemberCategory.INVOKE_PUBLIC_METHODS,
                MemberCategory.DECLARED_FIELDS,
                MemberCategory.PUBLIC_FIELDS
        );
    }
}
