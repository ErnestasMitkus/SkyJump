package com.ernestas.skyjump.Fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontFactory {
    /********************************************************/
    private static FontFactory instance = null;

    private FontFactory() {}

    public static FontFactory getInstance() {
        if (instance == null) {
            instance = new FontFactory();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }
    /********************************************************/

    public enum FontName {
        aller, openSans, roboto
    }

    public static BitmapFont generateFont(FontName fontName, int size, boolean bold, boolean italic) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        return generateFont(fontName, parameter, bold, italic);
    }

    public static BitmapFont generateFont(FontName fontName, FreeTypeFontParameter parameters, boolean bold, boolean italic) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontNameToPath(fontName, bold, italic)));
        return generator.generateFont(parameters);
    }

    private static String fontNameToPath(FontName fontName, boolean bold, boolean italic) {
        String dest = "Fonts/";
        StringBuilder path = new StringBuilder();
        path.append(dest);

        switch (fontName) {
            default:
            case aller:
                path.append("Aller/Aller");
                break;
            case openSans:
                path.append("OpenSans/OpenSans");
                break;
            case roboto:
                path.append("Roboto/Roboto");
                break;
        }

        String decoration = "Rg";
        if (bold) {
            decoration = "Bd";
        }
        if (italic) {
            decoration = "It";
        }
        if (bold && italic) {
            decoration = "BdIt";
        }

        path.append("_" + decoration);
        path.append(".ttf");
        return path.toString();
    }

}
