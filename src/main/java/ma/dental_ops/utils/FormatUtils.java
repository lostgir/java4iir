
package ma.dental_ops.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatUtils {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE);

    private FormatUtils() {
        // Empêche l'instanciation de cette classe utilitaire
    }

    // Formater un montant : 1 230,00 MAD
    public static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0,00 MAD";
        }

        DecimalFormatSymbols symbols =
                new DecimalFormatSymbols(Locale.FRANCE);

        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');

        DecimalFormat formatter =
                new DecimalFormat("#,##0.00", symbols);

        formatter.setRoundingMode(RoundingMode.HALF_UP);

        return formatter.format(amount) + " MAD";
    }

    // Formater un montant de type double
    public static String formatAmount(double amount) {
        return formatAmount(BigDecimal.valueOf(amount));
    }

    // Formater une date : 11/11/2026
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }

        return DATE_FORMATTER.format(date);
    }
}
