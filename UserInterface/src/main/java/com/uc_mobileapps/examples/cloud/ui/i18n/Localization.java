package com.uc_mobileapps.examples.cloud.ui.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Spring localization provider.
 */
@Component
public class Localization implements I18NProvider {

    private static final Logger logger = LoggerFactory.getLogger(Localization.class);

    private final List<Locale> availableLocales;

    @Autowired
    public Localization(@Qualifier("availableLocales") List<Locale> availableLocales) {
        this.availableLocales = availableLocales;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return availableLocales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            logger.error("Cannot translate null key.");
            return "";
        }

        final ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", new Locale(locale.getLanguage()));

        String translation;
        try {
            translation = bundle.getString(key);
        } catch (MissingResourceException e) {
            logger.error("Translation not available", e);
            return "[" + locale.getLanguage() + ": " + key + "]";
        }
        if (params.length > 0) {
            translation = MessageFormat.format(translation, params);
        }
        return translation;
    }
}