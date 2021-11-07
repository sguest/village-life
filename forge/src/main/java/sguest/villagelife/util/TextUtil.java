package sguest.villagelife.util;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TextUtil {
    public static TranslationTextComponent styledTranslation(String key, TextFormatting formatting, Object ... formatArgs) {
        TranslationTextComponent text = new TranslationTextComponent(key, formatArgs);
        text.setStyle(text.getStyle().applyFormatting(formatting));
        return text;
    }
}
