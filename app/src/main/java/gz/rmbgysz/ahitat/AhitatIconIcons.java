package gz.rmbgysz.ahitat;

import gz.rmbgysz.ahitat.Icon;

public enum AhitatIconIcons implements Icon {
    ahitat_book('\ue906'),
    ahitat_heart('\ue87d'),
	ahitat_searchbydate('\ue916'),
	ahitat_calendar('\ue24f'),
	ahitat_favourite('\ue838'),
	ahitat_help('\ue887'),
	ahitat_information('\ue88e'),
	ahitat_ujj('\ue900'),
	ahitat_locallibrary('\ue54b'),
	ahitat_actionname('\ue8b0'),
	ahitat_myshare('\ue80d'),
	ahitat_touchapp('\ue913'),
	ahitat_menu('\ue5d2'),
    ahitat_bookopenvariant('\ue901'),
	ahitat_search('\ue8fa');
    
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
