import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  private static final int SAND = 2;
  private static final int WATER = 3;
  private static final int DIRT = 4;
  private static final int FIRE = 5;
  private static final int COOL = 6;
  private static final int ICE = 7;
  private static final int STEAM = 8;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[7];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[DIRT] = "Soil";
    names[FIRE] = "Fire";
    names[COOL] = "Cooler";
    grid = new int[numRows][numCols];
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    if (tool == METAL){
      grid[row][col] = METAL;
    } else if(tool == SAND){
      grid[row][col] = SAND;
    } else if(tool == WATER){
      grid[row][col] = WATER;
    } else if(tool == DIRT){
      grid[row][col] = DIRT;
    } else if(tool == FIRE){
      grid[row][col] = FIRE;
    } else if(tool == COOL){
      grid[row][col] = COOL;
    } else{
      grid[row][col] = EMPTY;
    }
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    Color e = new Color(0,0,0);
    Color m = new Color(102,153,153);
    Color s = new Color(255,255,0);
    Color w = new Color(0,0,255);
    Color d = new Color(69,0,0);
    Color f = new Color(255,0,0);
    Color st = new Color(255,255,255);
    Color i = new Color(179,255,255);
    Color co = new Color(200,233,233);
    for (int r = 0; r < grid.length; r++){
      for (int c = 0; c < grid[0].length; c++){
        if (grid[r][c] == METAL){
          display.setColor(r, c, m);
        } else if (grid[r][c] == SAND){
          display.setColor(r, c, s);
        } else if (grid[r][c] == WATER){
          display.setColor(r, c, w);
        } else if (grid[r][c] == DIRT){
          display.setColor(r, c, d);
        } else if (grid[r][c] == FIRE){
          display.setColor(r, c, f);
        } else if (grid[r][c] == STEAM){
          display.setColor(r, c, st);
        } else if (grid[r][c] == COOL){
          display.setColor(r, c, co);
        } else if (grid[r][c] == ICE){
          display.setColor(r, c, i);
        } else{
          display.setColor(r, c, e);
        }
        }
      }
    }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
    int r = (int) ((Math.random() * (grid.length - 1)));
    int c = (int) ((Math.random() * grid[0].length));
    int i = ((int) ((Math.random() * 2)));
    int j = (int) ((Math.random() * 3));
    int[] a = {-1, 1};
    if (grid[r][c] == SAND && ((grid[r+1][c] == EMPTY) || (grid[r+1][c] == WATER))){
      int temp = grid[r][c];
      grid[r][c] = grid[r+1][c];
      grid[r+1][c] = temp;
    }
    if ((r > 0) && (r < (grid.length-1))){
      if ((grid[r+1][c] == SAND) && (grid[r-1][c] == SAND)){
        if ((c > 0) && (c < (grid[0].length-1)) && (grid[r][c+1] != METAL) && (grid[r][c-1] != METAL) && (grid[r][c+1] != DIRT) && (grid[r][c-1] != DIRT) && (grid[r][c+1] != ICE) && (grid[r][c-1] != ICE) && (grid[r][c+1] != COOL) && (grid[r][c-1] != COOL)){
          int t = grid[r][c+a[i]];
          grid[r][c+a[i]] = grid[r][c];
          grid[r][c] = t;
        } else{
          if (c == 0 && (grid[r][c+1] == EMPTY)){
            int t = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = t;
          }
          if (c == (grid[0].length - 1) && (grid[r][c-1] == EMPTY)){
            int t = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = t;
          }
        }
      } 
    }
    if (grid[r][c] == WATER){
        if ((j == 0) && (grid[r+1][c] == EMPTY)){
          int temp = grid[r+1][c];
          grid[r+1][c] = grid[r][c];
          grid[r][c] = temp;
        } 
        if ((c > 0) && (c < (grid[0].length - 1))){
          if((j == 1) && (grid[r][c-1] == EMPTY)){
            int temp = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = temp;
          }
          if((j == 2) && (grid[r][c+1] == EMPTY)){
            int temp = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = temp;
          }
        } else {
          if (c == 0){
            int temp = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = temp;
          }
          if (c == (grid[0].length - 1)){
            int temp = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = temp;
          }
        }
      }
    if ((r > 0) && (c > 0) && (c < (grid[0].length - 1))){
      if ((grid[r][c] == FIRE) && ((grid[r-1][c] == WATER) || (grid[r][c+1] == WATER) || (grid[r][c-1] == WATER))){
        grid[r][c] = EMPTY;
      }
    }
    if (r < (grid.length - 1)){
      if ((grid[r][c] == WATER) && (grid[r+1][c] == FIRE)){
        grid[r+1][c] = EMPTY;
      }
    }
    if (r < (grid.length - 1)){
      if ((grid[r][c] == DIRT) && (grid[r+1][c] == WATER)){
        grid[r+1][c] = EMPTY;
      }
    }
    if (r < (grid.length - 1)){
      if (((grid[r][c] == SAND) || (grid[r][c] == DIRT)) && (grid[r+1][c] == FIRE)){
        grid[r+1][c] = EMPTY;
      }
    }
      if ((grid[r][c] == DIRT) && (grid[r+1][c] == EMPTY)){
        int temp = grid[r][c];
        grid[r][c] = grid[r+1][c];
        grid[r+1][c] = temp;
      }
      if ((r > 0) && (r < (grid.length-1))){
        if ((grid[r+1][c] == DIRT) && (grid[r-1][c] == DIRT)){
          if ((c > 0) && (c < (grid[0].length-1)) && (grid[r][c+1] != METAL) && (grid[r][c-1] != METAL) && (grid[r][c+1] != SAND) && (grid[r][c-1] != SAND) && (grid[r][c+1] != ICE) && (grid[r][c-1] != ICE) && (grid[r][c+1] != COOL) && (grid[r][c-1] != COOL)){
          int t = grid[r][c+a[i]];
          grid[r][c+a[i]] = grid[r][c];
          grid[r][c] = t;
        } else{
          if (c == 0 && (grid[r][c+1] == EMPTY)){
            int t = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = t;
          }
          if (c == (grid[0].length - 1) && (grid[r][c-1] == EMPTY)){
            int t = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = t;
          }
        }
      }
    }
    if ((grid[r][c] == FIRE) && (grid[r+1][c] == EMPTY)){
      int temp = grid[r+1][c];
      grid[r+1][c] = grid[r][c];
      grid[r][c] = temp;
    }
    if (r < (grid.length - 7)){
      if ((grid[r][c] == WATER) && ((grid[r+1][c] == METAL) || (grid[r+2][c] == METAL) || (grid[r+3][c] == METAL)) && ((grid[r+2][c] == FIRE) || (grid[r+3][c] == FIRE) || (grid[r+4][c] == FIRE) || (grid[r+5][c] == FIRE) || (grid[r+6][c] == FIRE) || (grid[r+7][c] == FIRE))){
        grid[r][c] = STEAM;
      } 
    }
     if (grid[r][c] == STEAM){
      if (r > 0){
        if ((j == 0) && ((grid[r-1][c] == EMPTY) || (grid[r-1][c] == WATER))){
          int temp = grid[r-1][c];
          grid[r-1][c] = grid[r][c];
          grid[r][c] = temp;
        } 
      } else {
        grid[r][c] = WATER;
      }
        if ((c > 0) && (c < (grid[0].length - 1))){
          if((j == 1) && ((grid[r][c-1] == EMPTY) || (grid[r][c-1] == WATER))){
            int temp = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = temp;
          }
          if((j == 2) && ((grid[r][c+1] == EMPTY) || (grid[r][c+1] == WATER))){
            int temp = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = temp;
          }
        } else {
          if (c == 0){
            int temp = grid[r][c+1];
            grid[r][c+1] = grid[r][c];
            grid[r][c] = temp;
          }
          if (c == (grid[0].length - 1)){
            int temp = grid[r][c-1];
            grid[r][c-1] = grid[r][c];
            grid[r][c] = temp;
          }
        }
      }
      if (grid[r][c] == COOL){
        if (r > 0){
          if (grid[r-1][c] == WATER){
            grid[r-1][c] = ICE;
          }
          if (grid[r-1][c] == STEAM){
            grid[r-1][c] = WATER;
          }
        }
        if (r < (grid.length - 1)){
          if (grid[r+1][c] == WATER){
            grid[r+1][c] = ICE;
          }
          if (grid[r+1][c] == STEAM){
            grid[r+1][c] = WATER;
          }
        }
      }
      if ((r > 0) && (r < (grid.length - 1))){
        if ((grid[r][c] == WATER) && ((grid[r+1][c] == ICE) || (grid[r-1][c] == ICE))){
          grid[r][c] = ICE;
       }
      }
      if ((r < (grid.length - 1))){
        if ((grid[r][c] == ICE) && (grid[r+1][c] == FIRE)){
          grid[r][c] = EMPTY;
        }
        if ((grid[r][c] == FIRE) && (grid[r+1][c] == ICE)){
          grid[r+1][c] = EMPTY;
        }
      }
      if ((c < (grid[0].length - 1))){
        if ((grid[r][c] == ICE) && (grid[r][c+1] == FIRE)){
          grid[r][c] = EMPTY;
        }
        if ((grid[r][c] == FIRE) && (grid[r][c+1] == ICE)){
          grid[r][c+1] = EMPTY;
        }
      }
      if ((r < (grid.length - 1))){
        if ((grid[r][c] == ICE) && (grid[r+1][c] == FIRE)){
          grid[r][c] = EMPTY;
        }
        if ((grid[r][c] == FIRE) && (grid[r+1][c] == ICE)){
          grid[r+1][c] = EMPTY;
        }
      }
      if ((r < (grid.length - 1)) && (grid[r][c] == ICE) && (grid[r+1][c] == EMPTY)){
        int temp = grid[r+1][c];
        grid[r+1][c] = grid[r][c];
        grid[r][c] = temp;
      }
      if (r < (grid.length - 7)){
        if ((grid[r][c] == ICE) && ((grid[r+1][c] == METAL) || (grid[r+2][c] == METAL) || (grid[r+3][c] == METAL)) && ((grid[r+2][c] == FIRE) || (grid[r+3][c] == FIRE) || (grid[r+4][c] == FIRE) || (grid[r+5][c] == FIRE) || (grid[r+6][c] == FIRE) || (grid[r+7][c] == FIRE))){
          grid[r][c] = WATER;
          if (grid[r-1][c] == ICE){
            grid[r-1][c] = WATER;
          }
        } 
      }
    }
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
