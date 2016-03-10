package com.ivymobi.ui;


/**
 * Created by jinqian on 1/28/16.
 */
public final class Constants {

    public static final String[] IMAGES = new String[] {
            // Heavy images
            "https://media.starwars.ea.com/content/starwars-ea-com/en_US/starwars/battlefront/_jcr_content/ogimage.img.jpeg",
            "http://www.digitalbrands.cl/wp-content/uploads/2015/06/star_wars-660x420.png",
            "http://static3.gamespot.com/uploads/original/1365/13658182/2885935-star_wars_battlefront_e3_screen_5_weapon_variety_wm.jpg",
    };

    private Constants() {
    }

    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }

    public static class Extra {
        public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
        public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";

        public static final String TITLE = "title";
        public static final String PATH = "path";
        public static final String URL = "url";

        public static final String TITLES = "titles";
        public static final String URLS = "urls";
        public static final String STARTPOS = "startpos";
    }

//    public static class CallBack{
//        public static CallbackContext callbackContext;
//    }

}
