package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {

    private Military aircraftCarrier = new Military("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private Military battleship = new Military("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    private Civilian clipper = new Civilian("Clipper",3, new Coordinate(0,0),new Coordinate(0,0));
    private Civilian dinghy = new Civilian("Dinghy",1, new Coordinate(0,0),new Coordinate(0,0));
    private Military submarine = new Military("Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    private Military computer_aircraftCarrier = new Military("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,7));
    private Military computer_battleship = new Military("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(6,8));
    private Civilian computer_clipper = new Civilian("Computer_Clipper",3, new Coordinate(4,1),new Coordinate(4,4));
    private Civilian computer_dinghy = new Civilian("Computer_Dinghy",2, new Coordinate(7,3),new Coordinate(7,5));
    private Military computer_submarine = new Military("Computer_Submarine",2, new Coordinate(9,6),new Coordinate(9,8));

    ArrayList<Coordinate> playerHits;
    private ArrayList<Coordinate> playerMisses;
    ArrayList<Coordinate> computerHits;
    private ArrayList<Coordinate> computerMisses;
    ArrayList<Coordinate> startLocation;

    boolean scanResult = false;
    boolean isVertical = false;



    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }


    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        } if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        } if(shipName.equalsIgnoreCase("Clipper")) {
            return clipper;
        } if(shipName.equalsIgnoreCase("dinghy")) {
            return dinghy;
        }if(shipName.equalsIgnoreCase("submarine")) {
            return submarine;
        } else {
            return null;
        }
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        Coordinate startLoc = new Coordinate(rowint,colInt);

        if(orientation.equals("horizontal")){
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+5));
            } if(shipName.equalsIgnoreCase("battleship")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 4));

            /*} if(shipName.equalsIgnoreCase("Cruiser")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+3));
            } if(shipName.equalsIgnoreCase("destroyer")) {*/
            } if(shipName.equalsIgnoreCase("clipper")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+3));
            } if(shipName.equalsIgnoreCase("dinghy")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+2));
            }if(shipName.equalsIgnoreCase("submarine")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
            }
        }else{
            //vertical


                if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+5,colInt));
                    isVertical = true;
                }if(shipName.equalsIgnoreCase("battleship")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+4,colInt));
                    isVertical = true;
                }if(shipName.equalsIgnoreCase("Clipper")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+3,colInt));
                    isVertical = true;
                }if(shipName.equalsIgnoreCase("dinghy")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+2,colInt));
                    isVertical = true;
                }if(shipName.equalsIgnoreCase("submarine")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
                    isVertical = true;
                }
        }
        return this;
    }

    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row,col);
        if(computer_aircraftCarrier.covers(coor)){
            computerHits.add(coor);
        }else if (computer_battleship.covers(coor)){
            computerHits.add(coor);
        }else if (computer_clipper.covers(coor)) {
            computerHits.add(computer_clipper.getStart());
            computerHits.add(computer_clipper.getEnd());
            //add code to sink entire civilian ship
            Coordinate begin = computer_clipper.getStart();
            Coordinate end = computer_clipper.getEnd();

            if (isVertical == true) {
                computerHits.add(new Coordinate(coor.getAcross()+1,coor.getDown()));
            }else if (isVertical == false) {
                computerHits.add(new Coordinate(coor.getAcross(),coor.getDown()+1));
            }
        }else if (computer_dinghy.covers(coor)){
            computerHits.add(coor);
        }else if (computer_submarine.covers(coor)){
            computerHits.add(coor);
        }else {
            computerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
        int max = 10;
        int min = 1;
        Random random = new Random();
        int randRow = random.nextInt(max - min + 1) + min;
        int randCol = random.nextInt(max - min + 1) + min;

        Coordinate coor = new Coordinate(randRow,randCol);
        playerShot(coor);
    }

    void playerShot(Coordinate coor) {


        if(playerMisses.contains(coor)){
            System.out.println("Dupe");
        }

        if(aircraftCarrier.covers(coor)){
            playerHits.add(coor);
        }else if (battleship.covers(coor)){
            playerHits.add(coor);
        }else if (clipper.covers(coor)) {
            if (isVertical == true) {
                playerHits.add(new Coordinate(clipper.getStartAcross(), clipper.getStartDown()));
                playerHits.add(new Coordinate(clipper.getStartAcross() + 1, clipper.getStartDown()));
                playerHits.add(new Coordinate(clipper.getStartAcross() + 2, clipper.getStartDown()));
                playerHits.add(new Coordinate(clipper.getStartAcross() + 3, clipper.getStartDown()));
            } else if (isVertical == false) {
                playerHits.add(new Coordinate(clipper.getStartAcross(), clipper.getStartDown()));
                playerHits.add(new Coordinate(clipper.getStartAcross(), clipper.getStartDown() + 1));
                playerHits.add(new Coordinate(clipper.getStartAcross(), clipper.getStartDown() + 2));
                playerHits.add(new Coordinate(clipper.getStartAcross(), clipper.getStartDown() + 3));
            } else if (dinghy.covers(coor)) {
                playerHits.add(coor);
            } else if (submarine.covers(coor)) {
                playerHits.add(coor);
            } else {
                playerMisses.add(coor);
            }
        }
    }


    public void scan(int rowInt, int colInt) {
        Coordinate coor = new Coordinate(rowInt,colInt);
            scanResult = false;
        if(computer_aircraftCarrier.scan(coor)){
            scanResult = true;
        }
        else if (computer_battleship.scan(coor)){
            scanResult = true;
        }else if (computer_clipper.scan(coor)){
            scanResult = true;
        }else if (computer_dinghy.scan(coor)){
            scanResult = true;
        }else if (computer_submarine.scan(coor)){
            scanResult = true;
        } else {
            scanResult = false;
        }
    }


}