package structure.decorator;

public class MorriganOrigin implements Morrigan{

    public void setImage(String image) {
        this.image = image;
    }

    private String image = "莫莉卡原身";

    @Override
    public void display() {
        System.out.println(image);
    }



}
