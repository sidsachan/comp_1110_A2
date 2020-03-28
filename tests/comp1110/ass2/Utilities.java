package comp1110.ass2;

import java.util.*;

public class Utilities {

    public static final String[] VALID_MEDIUM_BOARDSTRINGS = {
            "cccc01cbaa72ccda50adad06bcbc30dbcd16cddb51aaaa02dada31cbcb07",
            "dacc60acba61adad76ddbc74ccda51bbad27cccc66adbb02bbbb01baac64bcdd04",
            "dada20cdac73dbcd17aaaa00cddb30ccda40bcbc05cbcb07acba60bbad21adad22bcdd31cccc76",
            "dbba10bcdd11adad76dacc71dada47cbaa61bbad12acba73dddd51aaaa20ccda21aacb57aacb22cccc02bcbc04",
            "adad74cbaa05baac60adbb02cccc47ccda15bbad46bbbb75dada27acba16aaaa30cdac25dbba14ddbc72",
            "baac57bbad17aacb05ddbc10adad04dacc14dada15cbaa40adbb71dddd24bbbb47acba73",

    };

    public static final String[] VALID_SMALL_BOARDSTRINGS = {
            "adbb40baac04cdac05bcdd47accd27aacb15",
            "acba57bcbc00baac10dacc56",
            "dddd03acba57bbbb06cccc67",
            "bcbc20aacb70dbba21dbcd37cccc74badb11cddb30"
    };

    public static final String[] CORRESPONDING_HANDS = {
            "adbbcdac",
            "acbaacbaacba",
            "bbbbddddccccacba",
            "bcbcdbbaccccbcbccddb"
    };

    public static final ArrayList<ArrayList<String>> VALID_SMALL_BOARDSTRING_DRAWS = new ArrayList<>(
            Arrays.asList(
                    new ArrayList<>(Arrays.asList("cccc", "bbbb", "dddd", "cbcb", "bcbc", "aacb", "baac", "cbaa",
                            "acba", "aaaa", "dacc", "ccda", "accd", "dbba", "badb", "bbad", "ddbc", "cddb", "bcdd",
                            "dbcd", "adad", "dada")),
                    new ArrayList<>(Arrays.asList("cccc", "bbbb", "dddd", "cbcb", "bcbc", "aacb", "baac", "cbaa",
                            "aaaa", "dacc", "cdac", "ccda", "accd", "dbba", "adbb", "badb", "bbad", "ddbc", "cddb",
                            "bcdd", "dbcd", "adad", "dada")),
                    new ArrayList<>(Arrays.asList("cbcb", "bcbc", "aacb", "baac", "cbaa", "acba", "aaaa", "dacc",
                            "cdac", "ccda", "accd", "dbba", "adbb", "badb", "bbad", "ddbc", "cddb", "bcdd", "dbcd",
                            "adad", "dada")),
                    new ArrayList<>(Arrays.asList("bbbb", "dddd", "cbcb", "aacb", "baac", "cbaa", "acba", "aaaa",
                            "dacc", "cdac", "ccda", "accd", "adbb", "badb", "bbad", "ddbc", "bcdd", "dbcd", "adad",
                            "dada"))
            )
    );

    public static final String[] INVALID_MEDIUM_BOARDSTRINGS = {
            // Have a piece(s) not connected to the rest of the board
            "aacb05dada30bcbc02dacc46cddb06baac72bbad75accd62badb57ccda01adad31",
            "adbb74cbcb17dbcd37cdac60accd36aaaa07cccc06baac55aacb70ccda63acba72adad46dbba35",

            // Loops back to a station
            "badb73dacc76aacb05bbad15cddb10dbcd17accd37bbbb67bcdd04adbb72ccda66cbcb40dada65cccc73aaaa71",
            "bbbb74cbaa37baac07dada76aaaa06bcdd26cdac64dddd63ccda02ddbc73dacc53aaaa57",

            // overlaps other piece and/or a central station
            "cdac60dacc17accd01cddb04aaaa77baac16ddbc10dada14bcbc67cbaa67",
            "ccda05bbad76dddd66ddbc74aacb03dada13cbcb37cdac12aaaa75bcbc22bbbb67bcdd06dbba47baac65cddb32baac27accd26dacc31acba01adbb41cccc56badb57cbaa17aacb55adad51dbba50dbcd54cbaa44",
    };

    public static final String[] INVALID_SMALL_BOARDSTRINGS = {
            "cbcb07dada47dacc73cddb14acba77accd02",
            "bbad37cddb04cdac37ddbc60acba36",
            "acba76dbba75baac60dacc26dada30",
            "cddb77baac37adad74aacb30dbba27badb03"
    };

    public static final String[] COMPLETE_BOARDSTRINGS = {
            "ccda01dada17cbcb04baac73ccda67aaaa74bcdd63accd37bbbb47bcbc46dacc40acba56acba75acba55cccc45cbcb05bbad62dbcd65ddbc76cbaa11baac06bcdd53dbba10cddb16acba72aacb64cdac26dacc41dbba60baac02cdac66ddbc71dddd12cbcb27cbaa03cccc54aaaa31dddd14dada50bcbc52aaaa25aacb42badb24baac61cbaa07aacb32adad13adad21dbcd23cddb36bbad51bbbb57adbb30accd35bcbc15aacb22aaaa77badb20cbaa70adbb00",
            "adbb01acba50cbaa70dbba75cbcb47aaaa51acba72aaaa06bcdd03dacc67cbaa20adad66accd56bbbb65badb04dddd41aacb37dacc10badb61dbcd11baac05cddb42dddd32cbcb02dbba15ccda16accd14cbaa46cddb64cccc74aacb25aaaa36ddbc52bbad22acba73baac17dbcd71aaaa13baac63cbaa12bcbc40bcbc53cccc21adbb31cdac54cbcb57dada23acba30bcbc35aacb55bcdd62ccda27adad45bbbb26bbad24dada60cdac76ddbc00baac77aacb07",
            "aaaa04badb47accd27cbcb57cbaa14dacc50dddd56baac13cbaa67aaaa20dddd55aaaa26dbcd45adad24bcdd25dbba71baac06cddb05cbcb30ccda61bcbc16cbcb65cddb12ccda54dada40dbba11baac77badb36dada22dacc60baac72ddbc53bbbb66acba62cccc41cdac15ddbc76aacb63aacb46acba17aaaa52accd31bcdd35adad21cdac74bbad75aacb23bcbc37acba64adbb32acba00cbaa01adbb02cbaa73bbbb42bbad51aacb10bcbc03dbcd07cccc70",
            "baac74aaaa27dacc71dddd61dbcd37aaaa05adad02bcbc62cccc51cbcb75baac15bbad01acba41dada63bcdd16acba76cbaa65aaaa73ccda57dbcd66cbcb56adad11bbad26acba47aacb72cbaa12dbba64aaaa00accd55dada30accd31aacb22cdac40bbbb50dacc60cddb53bcbc45cddb25dddd35baac67cccc13acba17ccda52cdac36adbb46bcdd54bcbc04ddbc10aacb24ddbc14dbba23baac77badb42adbb21cbcb03aacb07badb06cbaa32bbbb20cbaa70",
            "bcdd01dada47bbbb02aacb46badb10dddd11dbba76cdac66cccc75acba21cbcb20ddbc12aacb06cddb65acba77acba17badb27bbbb40acba00baac26accd22adad45aaaa36dddd31cccc71cbaa72ddbc16dbcd23dbcd37ccda24bbad13cbaa60ccda62cbaa73aaaa25adad52dacc53bcbc54adbb35aaaa42cdac64accd15cbcb57cbcb63cbaa04baac56bbad03cddb05dada14bcbc50aacb32bcbc51dacc30aacb41aaaa07adbb74bcdd55baac61dbba67baac70",
            "adad03cbaa71acba06badb27dddd61dddd13aacb12accd05bcbc62bbbb10baac51acba75ccda01bcbc37dbba16dbba36bcdd15aaaa72aacb41adad02cdac74accd46dacc52baac67cccc47dada40cbcb11aaaa73cbaa26cddb30aacb63adbb21dbcd45bbad04acba76dada50aaaa57baac20cbaa66ccda60dbcd53bbbb17bbad23badb65cddb64cbaa31bcbc14baac55ddbc24bcdd54cbcb35cdac25ddbc22cccc42cbcb70aacb56acba77aaaa00adbb32dacc07",
            "cccc72acba37aacb71baac17dada27aacb16adad26aacb74ddbc61dada60bbbb25dacc73dddd24cbaa04dbcd36dbcd35adbb30baac02cdac50baac67bbbb75cbaa70ccda51adad12cbcb06adbb76accd14aaaa40aaaa11cccc64cbaa54dddd65dacc13baac00acba57bcdd31bcbc15bcbc55acba20aaaa23ccda05cdac41bcdd32badb66bbad45badb52accd01cddb53bcbc63dbba42cbaa22acba56cddb62dbba47aaaa07cbcb21cbcb46aacb10ddbc77bbad03",
            "cbaa74baac40adbb20cbaa37cbcb60bbbb47ddbc61acba17dddd46ccda30cdac21dddd31bcbc51dbcd22aaaa64aacb56acba75cccc02accd65dbcd36cbaa32adad04baac06dada57bcbc01ddbc10cddb11cccc16dbba45ccda26bbad27aacb72dacc62aaaa15bcbc52aaaa07badb05aacb55cbcb42cddb41bcdd35accd23adad14dada13bbbb76bbad25cbaa63aaaa70dbba67cbcb50aacb71baac66cdac53bcdd03dacc24acba00adbb12acba73baac54badb77",
            "dacc37acba27dbba50cccc04dbba20bcdd21bbad57cdac26dada16dbcd75cddb11dddd31baac47ccda40baac01aacb74cccc46badb25cbaa45dbcd24cdac06aaaa10adbb41dddd36badb22aacb56cbaa03cbaa55cbaa30baac51cddb23aaaa64bcbc52bbbb17adad65cbcb61baac02ddbc62bbad54dacc15adad76bcbc60accd71cbcb32cbcb13accd73acba00bcdd63aacb70acba77aaaa42aaaa05bbbb67dada35ddbc72ccda12aacb14adbb66acba53bcbc07",
            "aacb03dada60dacc61baac02acba77badb04dbba57cddb10cdac13accd37cbcb05aacb50dbcd23aacb15aaaa76bbbb22baac11bbad01cccc20dddd24dbba25ccda14bcdd32dddd36cbaa47aaaa66dbcd62dada40bcbc42cbaa70bcbc00adbb73bcbc21adad46aaaa12ddbc41cbcb63baac72cdac31baac64acba53adad16cccc17dacc45ccda65bbad67aacb06aaaa27cbaa07ddbc54cbaa56acba26adbb75accd71badb51cddb52acba55bcdd35cbcb30bbbb74"
    };

    public static final int[][] CORNER_STATION_POSITIONS = {
            // ROW, COLUMN, EXIT, STATION
            {0, 0, 0, 8},
            {0, 0, 1, 8},
            {0, 0, 6, 9},
            {0, 0, 7, 9},
            {0, 7, 0, 1},
            {0, 7, 1, 1},
            {0, 7, 2, 32},
            {0, 7, 3, 32},
            {7, 0, 4, 17},
            {7, 0, 5, 17},
            {7, 0, 6, 16},
            {7, 0, 7, 16},
            {7, 7, 2, 25},
            {7, 7, 3, 25},
            {7, 7, 4, 24},
            {7, 7, 5, 24}
    };

    public static final int[][] EDGE_STATION_POSITIONS = {
            // ROW, COLUMN, STATION
            {0, 6, 2},
            {0, 5, 3},
            {0, 4, 4},
            {0, 3, 5},
            {0, 2, 6},
            {0, 1, 7},
            {1, 0, 10},
            {2, 0, 11},
            {3, 0, 12},
            {4, 0, 13},
            {5, 0, 14},
            {6, 0, 15},
            {7, 1, 18},
            {7, 2, 19},
            {7, 3, 20},
            {7, 4, 21},
            {7, 5, 22},
            {7, 6, 23},
            {6, 7, 26},
            {5, 7, 27},
            {4, 7, 28},
            {3, 7, 29},
            {2, 7, 30},
            {1, 7, 31}
    };

    public static final String[] BOARDSTRINGS_FOR_DECK = {
            "dacc72dacc73cdac74cdac75ccda01ccda02accd11accd12dbba21dbba22",
            "adbb01adbb02badb11badb12bbad71bbad72ddbc73ddbc74cddb21cddb60bcdd63",
            "dbcd72dbcd57adad73adad02dada12dada13cccc56cccc63bbbb64bbbb54dddd62"
    };

    public static final ArrayList<ArrayList<String>> DECK_CHOICES = new ArrayList<>(
            Arrays.asList(
                    new ArrayList<>(Arrays.asList("adbb", "badb", "bbad", "ddbc", "cddb", "bcdd", "dbcd", "adad", "dada", "cccc",
                            "bbbb", "dddd", "cbcb", "bcbc", "aacb", "baac", "cbaa", "acba", "aaaa")),
                    new ArrayList<>(Arrays.asList("bcdd", "dbcd", "adad", "dada", "cccc", "bbbb", "dddd", "cbcb", "bcbc", "aacb",
                            "baac", "cbaa", "acba", "aaaa", "dacc", "cdac", "ccda", "accd", "dbba")),
                    new ArrayList<>(Arrays.asList("dddd", "cbcb", "bcbc", "aacb", "baac", "cbaa", "acba", "aaaa", "dacc", "cdac",
                            "ccda", "accd", "dbba", "adbb", "badb", "bbad", "ddbc", "cddb", "bcdd"))
            ));

    public static final ArrayList<String> ALL_UNIQUE_PIECES = new ArrayList<>(
            Arrays.asList("cccc", "bbbb", "dddd", "cbcb", "bcbc", "aacb", "baac", "cbaa", "acba", "aaaa", "dacc",
                    "cdac", "ccda", "accd", "dbba", "adbb", "badb", "bbad", "ddbc", "cddb", "bcdd", "dbcd", "adad",
                    "dada")
    );

    public static final String[] SINGLE_CONNECTING_STATIONS = {
            "baac00aaaa01cbaa02",
            "bbbb01cbcb02",
            "adbb03aacb02bbad12cbaa13dbba11acba22cbcb32bcdd31dbcd21dada10",
            "accd74aaaa75acba76ddbc64bcdd27bcbc20baac01adbb30",
            "accd03cdac13adbb12bcdd37aaaa27bcbc17aacb73ddbc63cccc62bbad72"
    };

    public static final int[][] SINGLE_CONNECTING_SCORES = {
            {0, 3},
            {2, 0},
            {0, 7},
            {0, 2},
            {4, 0}
    };

    public static final String[] MULTIPLE_CONNECTING_STATIONS = {
            "bbad01badb02cdac11baac10adad03",
            "bbad72bbbb62cbcb73badb63dddd53adbb64cdac74",
            "cdac20cbcb21acba10dbcd11badb30bbbb31adbb05baac01acba00",
            "aaaa57baac47dada37adbb46ccda36cbcb35ddbc45adad26aacb27cddb16cdac40bbbb50acba30bcbc76bbad75",
            "cddb05ddbc15dada16aaaa17bcbc26cbaa25dddd36cccc37aacb47acba46dacc56bbbb45accd55badb65cdac75" +
                    "adad74dbba66dbcd67baac76cbcb64ccda63aacb73acba77"
    };

    public static final int[][] MULTIPLE_CONNECTING_SCORES = {
            {2, 6},
            {9, 2},
            {4, 10},
            {13, 3},
            {12, 25}
    };

    public static final String[] MISCELLANEOUS_MOVES = {
            "bbbb03dddd13accd04dbcd75adbb02bcbc60cbaa76aacb06dbba16cddb14aaaa17adad61cbcb05ccda51cdac",
            "bcbc20cddb21bbad01baac31aacb03dbba76acba75dacc67cbcb74cdac71aaaa13ccda22dddd66dada27dbcd",
            "cddb05accd17cbaa74dbcd73adad75acba65dddd15aacb66bbad64cbcb60ccda27baac20dada14bcbc"
    };

    public static final ArrayList<ArrayList<String>> POSSIBLE_MOVES = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("cdac01", "cdac10", "cdac12", "cdac15", "cdac20", "cdac23", "cdac24",
                    "cdac26", "cdac30", "cdac40", "cdac41", "cdac50", "cdac52", "cdac62", "cdac65", "cdac66", "cdac71",
                    "cdac72", "cdac73", "cdac74")),

            new ArrayList<>(Arrays.asList("dbcd11", "dbcd12", "dbcd14", "dbcd17", "dbcd23", "dbcd26", "dbcd32",
                    "dbcd37", "dbcd42", "dbcd47", "dbcd56", "dbcd57", "dbcd62", "dbcd64", "dbcd65", "dbcd72",
                    "dbcd73")),

            new ArrayList<>(Arrays.asList("bcbc00", "bcbc01", "bcbc02", "bcbc03", "bcbc04", "bcbc06", "bcbc10",
                    "bcbc13", "bcbc16", "bcbc21", "bcbc24", "bcbc25", "bcbc26", "bcbc30", "bcbc37", "bcbc40", "bcbc47",
                    "bcbc50", "bcbc54", "bcbc55", "bcbc56", "bcbc57", "bcbc61", "bcbc63", "bcbc67", "bcbc71", "bcbc72",
                    "bcbc76", "bcbc77"))

    ));

    public static final String[] PARTIAL_ADVANCED_SCORE_BOARDS = {
            "bcbc01dada30cbcb05cdac60bbad31accd67baac02bcdd41cccc57bbbb17dacc72ccda20cbaa21dbba76dddd22aacb06aaaa32acba42",
            "ddbc20cbcb06dbcd72bcbc02dbba75ccda03dada47cddb50adbb76cddb51cbaa05bcdd04bbbb61dacc73aaaa00dddd62bbad46bcbc67aacb07acba57badb52acba56cbaa40accd41baac55aaaa15adad36adbb26aacb14accd01cdac54cccc71cccc64baac25bcdd13cdac24cbcb63aacb35dbba23ddbc45baac16",
            "baac01acba71bcdd57badb02bbad47dacc20aacb03ccda56cccc72accd13cbcb73cdac76cddb63dddd53dada62aaaa12aaaa61adbb74baac51bcbc50dbba17adad54bcbc66ddbc65bbbb40aacb22accd23cbaa52",
            "cbcb05cbaa73bcdd57bbad63ccda04bcbc02badb17ddbc76aaaa07bbbb01dada66cdac53acba12"
    };

    public static final int[][] PARTIAL_ADVANCED_SCORES = {
            {12, 5},
            {19, 43},
            {10, 4},
            {4, 6}
    };

    public static final String[] PARTIAL_MULTIPLAYER_BOARDS = {
            "cccc73baac06aacb17bcdd16dbba57dada67bcbc76cdac20ddbc10accd74adad15ccda04bbad03cbaa63acba75dddd56badb21dbcd26adbb02bcbc46aaaa01acba27cddb36cbcb55baac53aacb13dacc23",
            "dbcd72cdac73aacb62accd74cbaa20cddb63ccda01dada61cbcb51bbad17adad75dddd11aaaa71baac00bcbc57badb12ddbc40dbba60bcbc05acba30dacc21bbbb52bcdd03aaaa50cbaa27cccc42baac06adbb15aacb26",
            "cccc10aaaa37cbcb05cdac72aacb30acba02ccda06cddb20acba11dacc74bcbc76bbad03cbaa04baac60dbba66bbbb15dddd14dbcd62cbaa67accd16bcdd64bcbc00ddbc13dada52bbad61adbb40aaaa65baac36badb24dddd23adad53dbcd71cbaa07bbbb73cbcb47ddbc25baac42aaaa35aacb31badb51cbcb41dacc27",
            "ccda57aaaa50cccc05cbcb10cddb04dbba40ddbc30bbad01dacc76baac20bcdd67cdac74accd03bbbb73bcbc47cdac15cbaa66acba41aacb13dbcd23adad22ddbc65badb32cbcb25dada14dada64acba11dbcd16cbaa21aaaa72accd24dddd55adbb26baac02aacb75adad71"
    };

    public static final int[][] PARTIAL_MULTIPLAYER_SCORES = {
            {13, 5, 7},
            {6, 20, 8, 0},
            {7, 2, 10, 14, 4},
            {20, 0, 9, 7, 0, 5}
    };

    public static final String[] FULL_ADVANCED_BOARDS = {
            "ccda01ddbc71cddb05acba76bcdd27baac74aaaa64baac72cbaa11badb04bbad02acba75dddd61dbba40bbbb66bcbc14dbcd41cccc57adbb50adbb06aacb10dacc24adad73cdac42cbcb21cddb13dacc31dada60cbaa07aacb23ddbc65accd62cdac54baac55cbcb63aaaa12bbbb03aaaa47dbba16badb45bcbc17bbad25dddd52dbcd26acba51adad36cccc20bcdd53cbaa70cbaa35ccda37accd56aacb46aacb67dada32cbcb30baac00aaaa77bcbc22acba15",
            "bcbc02cbcb67bcdd66cbaa17ddbc12ccda03dbcd37badb16cccc13dada65bbbb11aacb06dacc21dada36adbb22baac75acba04aaaa15cbaa23cdac05dddd24aacb27baac55bcbc32badb47acba26accd73bbbb45bbad64aaaa20cddb25aacb07cbcb30adad01aaaa00acba10cdac60dacc72ccda14dbba35cccc62accd71cbaa63baac56acba77cddb61dbcd54cbaa31bbad76cbcb74adad52baac51adbb42ddbc40dddd46dbba53bcbc41aacb57bcdd50aaaa70",
            "ddbc76adad71acba67cdac04aacb37dbba30aaaa17cccc06bbad27cddb05ccda60dddd16dada26dbcd75bbbb47baac00baac25adbb50bcbc15cbaa14accd01cbcb10adbb20aacb51accd24bcdd52dacc31bbad11dbcd12badb46acba66ccda22bcbc72ddbc23aaaa70aacb02bcbc36dddd13cccc65baac55dacc42adad54cbcb41badb45cdac73acba57bcdd21cbaa07cddb32dbba35aacb63cbaa62cbcb03aaaa77cbaa61baac53bbbb40dada56acba74aaaa64",
            "aacb03cbcb20ccda27cbaa30ddbc10dacc26bbad47adad13dbba17cdac05dbcd36acba02accd21bcdd23dada35bcbc15acba50aaaa76baac24bbbb12bcdd06dddd46cddb14dada66cccc71badb56cddb65bcbc75aacb51adbb04dacc73cbaa31dbcd22cdac64acba77baac37ddbc61badb16cbcb07bbbb74ccda01aaaa32aaaa42adad55cbcb72dddd25baac52aacb62acba11adbb63cbaa45bbad53aaaa57bcbc60dbba67aacb41cccc54baac40accd70cbaa00",
            "aaaa03cbcb47cbaa67ccda01cccc50dacc10dada60bbad75cdac76ddbc71bcdd46bbbb56bcbc20dbba17acba05baac55badb54cbaa27aacb72adbb74accd21cddb22dddd61bbbb37aaaa31dbcd45adad32dddd16aacb15bbad04baac11dbba23bcbc57acba24cccc41adbb62cbcb26aacb53bcdd51ccda02accd36adad64cbaa35acba14aaaa66ddbc52cdac25cbcb73dbcd13baac42badb30dacc12cddb63aaaa40baac00aacb65bcbc06cbaa07acba77dada70"
    };

    public static final int[][] FULL_ADVANCED_SCORES = {
            {111, 74},
            {92, 69, 45},
            {62, 45, 49, 38},
            {30, 53, 28, 23, 37},
            {57, 26, 26, 24, 55, 13}
    };

}
