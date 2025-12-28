package net.rnsqd.kitVault.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInformation {
    String name();
    String[] aliases() default {};
    String description() default "";
    String permission() default "";
    boolean needPermission() default false;
}
