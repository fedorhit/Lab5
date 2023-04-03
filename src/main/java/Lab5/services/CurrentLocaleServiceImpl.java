package Lab5.services;

import Lab5.aop.annotation.MET;
import Lab5.exception.LocaleNotSupportedException;
import Lab5.services.serviceInterface.CurrentLocaleService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Service
public class CurrentLocaleServiceImpl implements CurrentLocaleService {
    private final static Map<String, Locale> SUPPORTED = Map.of(
            "en", Locale.forLanguageTag("en"),
            "ru", Locale.forLanguageTag("ru")
    );

    private Locale current = SUPPORTED.get("en");

    @MET
    @Override
    public void set(String locale) {
        var loc = SUPPORTED.get(locale);
        if (loc == null) {
            throw new LocaleNotSupportedException("setLanguageFailed", locale);
        }

        current = loc;
    }

    @MET
    @Override
    public Locale get() {
        return current;
    }
}