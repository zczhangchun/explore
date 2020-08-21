package structure.decorator;

public class MorriganChanger implements Morrigan{

    Morrigan morrigan;

    public MorriganChanger(Morrigan morrigan) {
        this.morrigan = morrigan;
    }

    @Override
    public void display() {
        morrigan.display();
    }
}
