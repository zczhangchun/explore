package structure.adapter.demo;

public class ElectricAdapter implements Motor {

    ElectricMotor electricMotor;

    public ElectricAdapter() {
        electricMotor = new ElectricMotor();
    }

    public void drive() {
        electricMotor.electricDrive();;
    }
}
