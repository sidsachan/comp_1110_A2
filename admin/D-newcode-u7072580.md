# New Code for Deliverable D2D

## < u7072580 > < Siddharth Sachan >

For Deliverable D2D, I contributed the following new statements of original code:

Metro class, sortedOccurrence() method, lines 524-534 (work for feedback D2C)
-       HashMap<String, Integer> occurrence = new HashMap<>();
         for (Tile value : sorted)
             occurrence.put(value.getType(), 0);
         for (int i = 0; i < l; i = i + 6) {
             String s = placementSequence.substring(i, i + 4);
             Integer old = occurrence.get(s);
             occurrence.put(s, old + 1);
         }
         for (Tile tile : sorted) {
             tile.setNumber(occurrence.get(tile.getType()));
         }

Metro class, getScoreFromStation() method (Task 7 code), lines 684-697 
-          switch (ch) {
                        case 'a':
                            exitIndex = (startIndex + 5) % 8;
                            break;
                        case 'b':
                            exitIndex = (startIndex + 3) % 8;
                            break;
                        case 'c':
                            exitIndex = (startIndex + 7) % 8;
                            break;
                        case 'd':
                            exitIndex = (startIndex + 1) % 8;
                            break;
                    }