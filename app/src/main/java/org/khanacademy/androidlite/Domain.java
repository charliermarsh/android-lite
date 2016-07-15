package org.khanacademy.androidlite;

import java.util.HashMap;
import java.util.Map;

public enum Domain {
    MATH("math"),
    SCIENCE("science"),
    FINANCE("economics-finance-domain"),
    HUMANITIES("humanities"),
    COMPUTER_SCIENCE("computing"),
    TEST_PREP("test-prep"),
    PARTNER_CONTENT("partner-content"),
    DEFAULT("default");

    private static final Map<String, Domain> DOMAIN_SLUGS;

    static {
        final Map<String, Domain> domainSlugs =  new HashMap<>();
        for (final Domain domain : values()) {
            domainSlugs.put(domain.slug, domain);
        }

        DOMAIN_SLUGS = domainSlugs;
    }

    public final String slug;

    public static Domain getDomainBySlug(final String slug) {
        final Domain maybeDomain = DOMAIN_SLUGS.get(slug);
        if (maybeDomain == null) {
            return DEFAULT;
        } else {
            return maybeDomain;
        }
    }

    Domain(final String slug) {
        this.slug = slug;
    }
}
