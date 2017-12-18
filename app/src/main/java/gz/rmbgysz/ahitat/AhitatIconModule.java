package gz.rmbgysz.ahitat;

import gz.rmbgysz.ahitat.Icon;
import gz.rmbgysz.ahitat.IconFontDescriptor;

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
