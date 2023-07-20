package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import com.google.android.vending.expansion.downloader.Constants;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromInputStream(Context context, InputStream is) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        FontFamily.Builder familyBuilder = null;
        ContentResolver resolver = context.getContentResolver();
        int length = fonts.length;
        int i = 0;
        while (true) {
            int i2 = 1;
            if (i >= length) {
                break;
            }
            FontsContractCompat.FontInfo font = fonts[i];
            try {
                ParcelFileDescriptor pfd = resolver.openFileDescriptor(font.getUri(), "r", cancellationSignal);
                if (pfd != null) {
                    Font.Builder weight = new Font.Builder(pfd).setWeight(font.getWeight());
                    if (!font.isItalic()) {
                        i2 = 0;
                    }
                    Font platformFont = weight.setSlant(i2).setTtcIndex(font.getTtcIndex()).build();
                    if (familyBuilder == null) {
                        familyBuilder = new FontFamily.Builder(platformFont);
                    } else {
                        familyBuilder.addFont(platformFont);
                    }
                    if (pfd != null) {
                        pfd.close();
                    }
                } else if (pfd != null) {
                    pfd.close();
                }
            } catch (IOException e) {
            }
            i++;
        }
        if (familyBuilder == null) {
            return null;
        }
        FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? 700 : Constants.STATUS_BAD_REQUEST, (style & 2) != 0 ? 1 : 0);
        return new Typeface.CustomFallbackBuilder(familyBuilder.build()).setStyle(defaultStyle).build();
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry familyEntry, Resources resources, int style) {
        FontFamily.Builder familyBuilder = null;
        FontResourcesParserCompat.FontFileResourceEntry[] entries = familyEntry.getEntries();
        int length = entries.length;
        int i = 0;
        while (true) {
            int i2 = 1;
            if (i >= length) {
                break;
            }
            FontResourcesParserCompat.FontFileResourceEntry entry = entries[i];
            try {
                Font.Builder weight = new Font.Builder(resources, entry.getResourceId()).setWeight(entry.getWeight());
                if (!entry.isItalic()) {
                    i2 = 0;
                }
                Font platformFont = weight.setSlant(i2).setTtcIndex(entry.getTtcIndex()).setFontVariationSettings(entry.getVariationSettings()).build();
                if (familyBuilder == null) {
                    familyBuilder = new FontFamily.Builder(platformFont);
                } else {
                    familyBuilder.addFont(platformFont);
                }
            } catch (IOException e) {
            }
            i++;
        }
        if (familyBuilder == null) {
            return null;
        }
        FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? 700 : Constants.STATUS_BAD_REQUEST, (style & 2) != 0 ? 1 : 0);
        return new Typeface.CustomFallbackBuilder(familyBuilder.build()).setStyle(defaultStyle).build();
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        try {
            FontFamily family = new FontFamily.Builder(new Font.Builder(resources, id).build()).build();
            FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? 700 : Constants.STATUS_BAD_REQUEST, (style & 2) != 0 ? 1 : 0);
            return new Typeface.CustomFallbackBuilder(family).setStyle(defaultStyle).build();
        } catch (IOException e) {
            return null;
        }
    }
}