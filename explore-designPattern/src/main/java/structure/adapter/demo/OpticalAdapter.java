package structure.adapter.demo;

public class OpticalAdapter implements Motor{

    OpticalMotor opticalMotor;

    public OpticalAdapter() {
        opticalMotor = new OpticalMotor();
    }

    public void drive() {
        opticalMotor.opticalDrive();
    }
}
