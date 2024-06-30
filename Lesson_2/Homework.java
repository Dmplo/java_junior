package JavaJunior.Homework.Lesson_2;

import JavaJunior.Homework.Lesson_2.lib.RandomDateAnnotationProcessor;

public class Homework {

  public static void main(String[] args) throws IllegalAccessException {
    Human rndHuman = new Human("Alex");
    Human rndHuman2 = new Human("John");
    RandomDateAnnotationProcessor.processAnnotation(rndHuman);
    RandomDateAnnotationProcessor.processAnnotation(rndHuman2);
    System.out.println(rndHuman);
    System.out.println(rndHuman2);
  }
}
