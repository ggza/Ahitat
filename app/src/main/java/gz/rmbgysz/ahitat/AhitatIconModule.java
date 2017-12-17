package gz.rmbgysz.ahitat;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

public class AhitatIconModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "ahitaticon.ttf";
    }

    @Override
    public Icon[] characters() {
        return AhitatIconIcons.values();
    }
}
