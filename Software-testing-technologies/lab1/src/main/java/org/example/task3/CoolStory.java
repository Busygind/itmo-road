package org.example.task3;

public class CoolStory {
    public static void main(String[] args) {
        Transmission transmission = new Transmission();
        Engine engine = new Engine();
        Car car = new Car(transmission, engine);

        Brain brain = new Brain();
        Person you = new Person(brain, "Вы", car);

        you.startDriving();
        you.changeGear(TransmissionGear.FIRST);
        you.changeGear(TransmissionGear.SECOND);
        you.changeGear(TransmissionGear.THIRD);
        you.changeGear(TransmissionGear.FOURTH);
        you.changeGear(TransmissionGear.FIRST);

    }
}

//    Когда вы мчитесь по шоссе, лениво обгоняя другие машины,
//        чувствуя, как вы довольны собой, и вдруг случайно переключаетесь с
//        четвертой скорости на первую вместо третьей, отчего ваш двигатель и ваши
//        мозги чуть не вылетают прочь, вы должны чувствовать себя примерно так же, как
//        почувствовал себя Форд Префект при этом замечании.