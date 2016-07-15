package org.khanacademy.androidlite;

import android.content.Context;

public final class ColorPalette {
    public final int dark;
    public final int light;
    public final int text;
    public final int pressed;

    private ColorPalette(final int dark,
                         final int light,
                         final int text,
                         final int pressed) {
        this.dark = dark;
        this.light = light;
        this.text = text;
        this.pressed = pressed;
    }

    public static ColorPalette forDomain(final Domain domain, final Context context) {
        switch (domain) {
            case MATH:
                return new ColorPalette(
                        context.getResources().getColor(R.color.math_dark),
                        context.getResources().getColor(R.color.math_light),
                        context.getResources().getColor(R.color.math_text),
                        context.getResources().getColor(R.color.math_pressed)
                );

            case SCIENCE:
                return new ColorPalette(
                        context.getResources().getColor(R.color.science_dark),
                        context.getResources().getColor(R.color.science_light),
                        context.getResources().getColor(R.color.science_text),
                        context.getResources().getColor(R.color.science_pressed)
                );

            case FINANCE:
                return new ColorPalette(
                        context.getResources().getColor(R.color.finance_dark),
                        context.getResources().getColor(R.color.finance_light),
                        context.getResources().getColor(R.color.finance_text),
                        context.getResources().getColor(R.color.finance_pressed)
                );

            case HUMANITIES:
                return new ColorPalette(
                        context.getResources().getColor(R.color.humanities_dark),
                        context.getResources().getColor(R.color.humanities_light),
                        context.getResources().getColor(R.color.humanities_text),
                        context.getResources().getColor(R.color.humanities_pressed)
                );

            case COMPUTER_SCIENCE:
                return new ColorPalette(
                        context.getResources().getColor(R.color.computer_science_dark),
                        context.getResources().getColor(R.color.computer_science_light),
                        context.getResources().getColor(R.color.computer_science_text),
                        context.getResources().getColor(R.color.computer_science_pressed)
                );

            case TEST_PREP:
                return new ColorPalette(
                        context.getResources().getColor(R.color.test_prep_dark),
                        context.getResources().getColor(R.color.test_prep_light),
                        context.getResources().getColor(R.color.test_prep_text),
                        context.getResources().getColor(R.color.test_prep_pressed)
                );

            case PARTNER_CONTENT:
                return new ColorPalette(
                        context.getResources().getColor(R.color.partner_content_dark),
                        context.getResources().getColor(R.color.partner_content_light),
                        context.getResources().getColor(R.color.partner_content_text),
                        context.getResources().getColor(R.color.partner_content_pressed)
                );

            case DEFAULT:
                return new ColorPalette(
                        context.getResources().getColor(R.color.default_dark),
                        context.getResources().getColor(R.color.default_light),
                        context.getResources().getColor(R.color.default_text),
                        context.getResources().getColor(R.color.default_pressed)
                );
        }

        throw new IllegalArgumentException("Invalid domain: " + domain);
    }
}
