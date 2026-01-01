package net.rnsqd.kitVault.applicators;

import net.rnsqd.kitVault.services.ApplicatorService;

public final class TimeFormatApplicator implements ApplicatorService<String> {
    @Override
    public String apply(Object data) {
        if (data instanceof Long sec) {
            if (sec < 1L) {
                return "0 секунд";
            } else {
                long m = sec / 60L;
                sec %= 60L;
                long h = m / 60L;
                m %= 60L;
                long d = h / 24L;
                h %= 24L;
                long y = d / 365L;
                d %= 365L;
                String time = "";
                if (y > 0L) {
                    time = time + y + " " + formatTime(y, "год", "лет", "лет");
                    if (d > 0L || h > 0L || m > 0L || sec > 0L) {
                        time = time + " ";
                    }
                }

                if (d > 0L) {
                    time = time + d + " " + formatTime(d, "день", "дня", "дней");
                    if (h > 0L || m > 0L || sec > 0L) {
                        time = time + " ";
                    }
                }

                if (h > 0L) {
                    time = time + h + " " + formatTime(h, "час", "часа", "часов");
                    if (m > 0L || sec > 0L) {
                        time = time + " ";
                    }
                }

                if (m > 0L) {
                    time = time + m + " " + formatTime(m, "минута", "минуты", "минут");
                    if (sec > 0L) {
                        time = time + " ";
                    }
                }

                if (sec > 0L) {
                    time = time + sec + " " + formatTime(sec, "секунда", "секунды", "секунд");
                }

                return time;
            }
        }
        return "input not a long value";
    }

    private String formatTime(long num, String single, String lessfive, String others) {
        if (num % 100L > 10L && num % 100L < 15L) {
            return others;
        } else {
            return switch ((int) (num % 10L)) {
                case 1 -> single;
                case 2, 3, 4 -> lessfive;
                default -> others;
            };
        }
    }
}
