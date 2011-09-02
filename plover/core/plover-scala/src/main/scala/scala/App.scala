package scala

object App {
    def main(args: Array[String]): Unit =
        {
            System.out.println("Howdy, from packaged code!")
            args.foreach((i) => System.out.println("Got " + i))
        }
}