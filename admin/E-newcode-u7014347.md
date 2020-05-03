# New Code for Deliverable D2E

## < u7014347 > < Hengrui Xu >

For Deliverable D2E, I contributed the following new statements of original code:

Metro Class, line 460-490
-     Board[] startBoard = Board.getStartBoard();
         List<Board> board = new ArrayList<>(Arrays.asList(startBoard));
         boolean check = false;
         String result ="";
 
         for(int i=4;i<placementSequence.length();i+=6) {
             String position = placementSequence.substring(i, i + 2);
 
             for (int j = 0; j < board.size(); j++) {
                 String name = board.get(j).getName();
                 if (name.equals(position)) {
                     board.remove(j);
                 }
             }
         }
             while(!check){
                 Random random = new Random();
                 int num = random.nextInt(board.size());
                 String place = board.get(num).getName();
                 placementSequence = placementSequence.concat(piece);
                 placementSequence = placementSequence.concat(place);
                 if(!isPlacementSequenceValid(placementSequence)){
                     board.remove(num);
                     placementSequence=placementSequence.substring(0,placementSequence.length()-6);
                     int i = 1;
                 }else {
                     result = piece.concat(place);
                     check=true;
                 }
             }
         return result;

checkInnerBoardTest class, Line 34-44
-       @Test
        public void testEmptyBoard(){
            int[][] emptyBoard = Utilities.EMPTY_BOARD;
            test(emptyBoard,false);
        }
 
        @Test
        public void testFullBoard(){
            int[][] fullInnerBoard = Utilities.FULL_INNER_BOARD;
            test(fullInnerBoard,true);
        }

(List at least 20, including at least one new unit test, and include line numbers.)
