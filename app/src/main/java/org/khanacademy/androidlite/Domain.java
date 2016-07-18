package org.khanacademy.androidlite;

import java.util.HashMap;
import java.util.Map;

public final class Domain {
    public static final int MATH;
    public static final int SCIENCE;
    public static final int FINANCE;
    public static final int HUMANITIES;
    public static final int COMPUTER_SCIENCE;
    public static final int TEST_PREP;
    public static final int PARTNER_CONTENT;
    public static final int DEFAULT;

    static {
        int step = 0;

        MATH = step++;
        SCIENCE = step++;
        FINANCE = step++;
        HUMANITIES = step++;
        COMPUTER_SCIENCE = step++;
        TEST_PREP = step++;
        PARTNER_CONTENT = step++;
        DEFAULT = step;
    }

    private static final Map<String, Integer> DOMAIN_SLUGS = new HashMap<>();

    static {
        DOMAIN_SLUGS.put("math", MATH);
        DOMAIN_SLUGS.put("science", SCIENCE);
        DOMAIN_SLUGS.put("economics-finance-domain", FINANCE);
        DOMAIN_SLUGS.put("humanities", HUMANITIES);
        DOMAIN_SLUGS.put("computing", COMPUTER_SCIENCE);
        DOMAIN_SLUGS.put("test-prep", TEST_PREP);
        DOMAIN_SLUGS.put("partner-content", PARTNER_CONTENT);
        DOMAIN_SLUGS.put("default", DEFAULT);
    }

    public static int getDomainBySlug(final String slug) {
        final Integer maybeDomain = DOMAIN_SLUGS.get(slug);
        if (maybeDomain == null) {
            return DEFAULT;
        } else {
            return maybeDomain;
        }
    }

    public static String getSlugForDomain(final int domain) {
        for (final String slug : DOMAIN_SLUGS.keySet()) {
            if (DOMAIN_SLUGS.get(slug) == domain) {
                return slug;
            }
        }

        throw new IllegalArgumentException(String.valueOf(domain));
    }
}
