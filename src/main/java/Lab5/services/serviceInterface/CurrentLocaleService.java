package Lab5.services.serviceInterface;

import java.util.Locale;

public interface CurrentLocaleService {
    void set(String locale);
    Locale get();
}
