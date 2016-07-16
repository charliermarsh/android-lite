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

    public static ColorPalette forDomain(final int domain, final Context context) {
        if (domain == Domain.MATH) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.math_dark),
                    context.getResources().getColor(R.color.math_light),
                    context.getResources().getColor(R.color.math_text),
                    context.getResources().getColor(R.color.math_pressed)
            );
        } else if (domain == Domain.SCIENCE) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.science_dark),
                    context.getResources().getColor(R.color.science_light),
                    context.getResources().getColor(R.color.science_text),
                    context.getResources().getColor(R.color.science_pressed)
            );
        } else if (domain == Domain.FINANCE) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.finance_dark),
                    context.getResources().getColor(R.color.finance_light),
                    context.getResources().getColor(R.color.finance_text),
                    context.getResources().getColor(R.color.finance_pressed)
            );
        } else if (domain == Domain.HUMANITIES) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.humanities_dark),
                    context.getResources().getColor(R.color.humanities_light),
                    context.getResources().getColor(R.color.humanities_text),
                    context.getResources().getColor(R.color.humanities_pressed)
            );
        } else if (domain == Domain.COMPUTER_SCIENCE) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.computer_science_dark),
                    context.getResources().getColor(R.color.computer_science_light),
                    context.getResources().getColor(R.color.computer_science_text),
                    context.getResources().getColor(R.color.computer_science_pressed)
            );
        } else if (domain == Domain.TEST_PREP) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.test_prep_dark),
                    context.getResources().getColor(R.color.test_prep_light),
                    context.getResources().getColor(R.color.test_prep_text),
                    context.getResources().getColor(R.color.test_prep_pressed)
            );
        } else if (domain == Domain.PARTNER_CONTENT) {
            return new ColorPalette(
                    context.getResources().getColor(R.color.partner_content_dark),
                    context.getResources().getColor(R.color.partner_content_light),
                    context.getResources().getColor(R.color.partner_content_text),
                    context.getResources().getColor(R.color.partner_content_pressed)
            );
        } else if (domain == Domain.DEFAULT) {
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
