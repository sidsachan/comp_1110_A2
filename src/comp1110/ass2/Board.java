package comp1110.ass2;

public class Board {
    private String name;

    public Board(String name) {
        this.name = name;
    }

    public static Board[] getStartBoard(){
        return new Board[]{
                new Board("00"),
                new Board("01"),
                new Board("02"),
                new Board("03"),
                new Board("04"),
                new Board("05"),
                new Board("06"),
                new Board("07"),
                new Board("10"),
                new Board("11"),
                new Board("12"),
                new Board("13"),
                new Board("14"),
                new Board("15"),
                new Board("16"),
                new Board("17"),
                new Board("20"),
                new Board("21"),
                new Board("22"),
                new Board("23"),
                new Board("24"),
                new Board("25"),
                new Board("26"),
                new Board("27"),
                new Board("30"),
                new Board("31"),
                new Board("32"),
                //new Board("33"),
                //new Board("34"),
                new Board("35"),
                new Board("36"),
                new Board("37"),
                new Board("40"),
                new Board("41"),
                new Board("42"),
                //new Board("43"),
                //new Board("44"),
                new Board("45"),
                new Board("46"),
                new Board("47"),
                new Board("50"),
                new Board("51"),
                new Board("52"),
                new Board("53"),
                new Board("54"),
                new Board("55"),
                new Board("56"),
                new Board("57"),
                new Board("60"),
                new Board("61"),
                new Board("62"),
                new Board("63"),
                new Board("64"),
                new Board("65"),
                new Board("66"),
                new Board("67"),
                new Board("70"),
                new Board("71"),
                new Board("72"),
                new Board("73"),
                new Board("74"),
                new Board("75"),
                new Board("76"),
                new Board("77"),
        };
    }

    public String getName(){
        return this.name;
    }

}
