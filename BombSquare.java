import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private boolean BeenClicked=false;
	
	
	/*
	 * Bombsquare class to handle bombs
	 */
	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		this.thisSquareHasBomb=(r.nextInt(MINE_PROBABILITY)==0);
		
	
	}

	/*
	 * When clicked on this square
	 */
	public void clicked() 
	{
	
		canPlaceBomb(); //Can a bomb be generated here
		numberOfBombs(xLocation,yLocation); //Count number of bombs for this circle
		if(thisSquareHasBomb){ //If bomb can be placed then this takes priority, place bomb
			setImage("images/bomb.png");
			
		}
		
		
	}

	/*
	 * Get size of board, counts how many in terms of width and height
	 * @param type : whether to count for x direction or y, which is any other string
	 */
	public int getSize(String type){

		int i=0;
		if(type=="x"){
			while( (BombSquare)board.getSquareAt(i, 0)!=null ){
				i++;
			}
			return(i); //return number which represents width of board, just before it becomes null
			
		}

		//repeat same for y value
		else{
			while( (BombSquare)board.getSquareAt(0,i)!=null ){
				i++;
			}
			return(i);
	
		}
			
	}
	
	/*
	 * Checking if a bomb can be placed at this tile
	 */
	public void canPlaceBomb(){

		BombSquare bomb;

		int bombCount=0;

		for (int y = 0; y<getSize("y"); y++)
		{
			for (int x = 0; x<getSize("x"); x++)
			{
				
				bomb = (BombSquare)board.getSquareAt(x, y);

				if(bomb.hasBomb()){
					bombCount++;
					if(bombCount>10){ //Only 10 bombs maximum
						bomb.changeSquareHasBomb(); 
						// 'remove' any bombs after all ones that could have been placed have been placed
					}
				}

				

			}
		}

	
	}

	/*
	 * Fetch boolean to check if this square has a bomb
	 */
	public boolean hasBomb(){
		return(this.thisSquareHasBomb);
	}
	

	/*
	 * Set bomb status to false
	 */
	public void changeSquareHasBomb(){
		this.thisSquareHasBomb=false;
	}

	/*
	 * Check if given square has already been clicked
	 */
	public boolean hasBeenClicked(){
		return(this.BeenClicked);
	}

	/*
	 * Count number of bombs
	 */
	public void numberOfBombs(int xLoc, int yLoc)
	{
		
		//Count number of bombs at a given square
		
		int numBombs=0;

		BombSquare bomb;
		bomb = (BombSquare)board.getSquareAt(xLoc,yLoc);
		
		if(bomb==null ) return;
		//cannot check if been clicked in same statement as value would be null before so no method call//System.out.println("xloc: "+xLoc+" yloc: "+yLoc+" is invalid");
			
		else if(bomb.BeenClicked) return;  //now can check if been clicked already
		
		bomb.BeenClicked=true; // set to been clicked
		
		for(int y=-1;y<2;y++)
		{ 						//top,middle,bottom
			
			for(int x=-1;x<2;x++){ //left,middle,right
				try{
					bomb = (BombSquare)board.getSquareAt(xLoc+x,yLoc+y);

					if(x==0 && y==0){
						continue;
					}
	
					if(bomb.hasBomb()){ //Count number of bombs in surrouding squares
						numBombs++;
					}

				}
				catch(NullPointerException e){
					
				}
			}
		}
		// Reset value
		bomb = (BombSquare)board.getSquareAt(xLoc, yLoc);
		bomb.setImage("images/"+Integer.toString(numBombs)+".png");
		
		if(numBombs!=0 || bomb.hasBomb()) return; 
		//Do not check other squares if surrounding squares have a bomb in them
			
		else{
			
			for(int y=-1;y<2;y++)
			{ 						//top,middle,bottom
			
				for(int x=-1;x<2;x++){ //left,middle,right
					
						numberOfBombs(xLoc+x, yLoc+y); //recursive call

						if(x==0 && y==0){
							continue;
						}						

					}
					
				}
			}
			
	}
	
}
