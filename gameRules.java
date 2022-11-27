package Reversi;
import java.util.ArrayList;
import java.util.function.Predicate;

public class GameRules {
    private final Board board;
    private ArrayList<AbleToPutSpotDto> ableToPutSpotDtos;
    private Player turn = Player.playerBlack;
    private int canPlayerMove = 0; 

    public GameRules(Board board, ArrayList<AbleToPutSpotDto> ableToPutSpotDtos) {
        this.board = board;
        this.ableToPutSpotDtos = ableToPutSpotDtos;
    }

    public ArrayList<AbleToPutSpotDto> getAbleToPutSpots() {
        return ableToPutSpotDtos;
    }

    public Player getTurn() {
        return turn;
    }

    public Disk createNewDisk() {
        if (this.getTurn().equals(Player.playerWhite)) {
            return new Disk(DiskColor.White);
        } else {
            return new Disk(DiskColor.Black);
        }
    }

    public void FlipDisks(Coordinates c) {
        ArrayList<Disk> alreadyFlipped = new ArrayList<>();
        for (AbleToPutSpotDto ableToPutSpotDto : this.findAbleToPutSpots(coordinates)) {
            for (Disk d : ableToPutSpotDto.getWillFlipDisks()) {
                if (this.checkIfAlreadyFlipped(alreadyFlipped, d)) {
                    continue;
                }
                d.flipColor();
                alreadyFlipped.add(d);
            }
        }
    }
 
    public void addAbleToPutSpots() {
        for (int i = 0; i < this.board.getHeight(); i++) {
            for (int j = 0; j < this.board.getWidth(); j++) {
                Disk element = this.board.getElement(new Coordinates(i, j));
                if (element == null) {
                    continue;
                } else if (element.getDiskColor().equals(DiskColor.White) && this.getTurn().equals(Player.playerBlack)) {
                    this.addAllAbleToPutSpotsForOneElement(new Coordinates(i, j), DiskColor.Black);

                } else if (element.getDiskColor().equals(DiskColor.Black) && this.getTurn().equals(Player.playerWhite)) {
                    this.addAllAbleToPutSpotsForOneElement(new Coordinates(i, j), DiskColor.White);
                }
            }
        }
    }

    public void change() {
        if (this.getTurn().equals(Player.playerWhite)) {
            this.turn = Player.playerBlack;
            return;
        }
        this.turn = Player.playerWhite;
    }

    public boolean canPut(Coordinates coordinates) {
        for (AbleToPutSpotDto ableToPutSpotDto : this.ableToPutSpotDtos) {
            if (ableToPutSpotDto.getAbleToPutCoordinates().getX() == coordinates.getX() && ableToPutSpotDto.getAbleToPutCoordinates().getY() == coordinates.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        // prideti baigti kai zaidejas nebegali deti disko
        for (int i = 0; i < this.board.getHeight(); i++) {
            for (int j = 0; j < this.board.getWidth(); j++) {
                if (board.getElement(new Coordinates(i, j)) == null) {
                    return false;
                }
            }
        }
        return true;
    } public boolean checkIfAlreadyFlipped(ArrayList<Disk> alreadyFlipped, Disk disk) {
        for (Disk d : alreadyFlipped) {
            if (d.equals(disk)) {
                return true;
            }
        }
        return false;
    }
}
