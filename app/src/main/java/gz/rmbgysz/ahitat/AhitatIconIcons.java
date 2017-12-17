package gz.rmbgysz.ahitat;

import com.joanzapata.iconify.Icon;

public enum AhitatIconIcons implements Icon {
    ahitat_book('\ue900'),
    ahitat_heart('\ue9da');
    
    char character;

    AhitatIconIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
