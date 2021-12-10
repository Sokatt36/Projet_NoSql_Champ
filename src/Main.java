import dao.readFile;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        for (String[] data : Objects.requireNonNull(readFile.getClub())){
            System.out.println(data[0] + " " + data[1] + " " + data[2]);
        }
    }
}
