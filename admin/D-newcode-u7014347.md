# New Code for Deliverable D2D

## < u7014347 > < Hengrui Xu >

For Deliverable D2D, I contributed the following new statements of original code:

Metro Class, line 735-744
-         private static boolean checkInnerBoard(int[][] tilePresent){
              for(int x = 1; x<7;x++){
                  for(int y=1; y<7; y++){
                      if(tilePresent[x][y]!=1&&(x!=3)&&(x!=4)&&(y!=3)&&(y!=4)){
                          return false;
                      }
                  }
              }
              return true;
          }

Metro Class, line 166-173    
-         if ((row == 3 || row == 4) && (col == 3 || col == 4))       //central station positions check
              return false;
      
          if (((row == 2 || row == 5) && (col == 3 || col == 4)) || ((row == 3 || row == 4) && (col == 2 || col == 5)) ){
              if(tilePresent[row-1][col] == 0 && tilePresent[row+1][col] == 0
                      &&tilePresent[row][col+1] == 0 && tilePresent[row][col-1] == 0)
                  return false;           //next to central station check
          }

          



(List at least 10, and include line numbers.)
