public class BMPImageHandler {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Se necesita una bandera. Usa -help para más información.");
            return;
        }

        String flag = args[0];

        if ("-help".equals(flag)) {
            printHelp();
            return;
        }

        if (args.length < 2) {
            System.out.println("Se necesita una bandera y un nombre de archivo si necesita ayuda usar -help");
            return;
        }


        String filename = args[1];

        System.out.println("Procesando imagen...");
        switch (flag) {
            case "-basics":
                BmpHandlerCore image = new BmpHandlerCore(filename);
                image.generateRedToneImage();
                image.generateGreenToneImage();
                image.generateBlueToneImage();
                image.generateSepiaToneImage("Image-sepia.bmp");
                System.out.println("Imagenes generadas exitosamente!!!");
                break;
            case "-rotate":
                BmpHandlerRotator rotate = new BmpHandlerRotator(filename);
                rotate.horizontalRotation("Image-hrotation.bmp");
                rotate.verticalRotation("Image-vrotation.bmp");
                System.out.println("Imagenes volteadas exitosamente!!!");
                break;
            case "-resize":
                BmpHandlerResizer aplastart = new BmpHandlerResizer(filename);
                aplastart.thinImage("Image-thin.bmp");
                System.out.println("Imagenes estiradas exitosamente!!!");
                break;
            case "-all":
                System.out.println("Ejecutando todas las partes, esperar porfavor");

                BmpHandlerCore primeraParte = new BmpHandlerCore(filename);
                BmpHandlerRotator segundaParte = new BmpHandlerRotator(filename);
                BmpHandlerResizer terceraPagina = new BmpHandlerResizer(filename);


                /*===Color imagenes====*/
                System.out.println("Cambiando color de imagenes...");
                primeraParte.generateRedToneImage();
                primeraParte.generateGreenToneImage();
                primeraParte.generateBlueToneImage();
                primeraParte.generateSepiaToneImage("Image-sepia.bmp");
                System.out.println("Cambio de color exitoso!!!");

                /*====Voltear imagenes====*/
                System.out.println("Volteando imagenes");
                segundaParte.horizontalRotation("Image-hrotation.bmp");
                segundaParte.verticalRotation("Image-vrotation.bmp");
                System.out.println("Imagenes volteadas exitosamente!!!");

                /*===Cambiar tamaño imagenes===*/
                System.out.println("Apachando imagenes");
                terceraPagina.thinImage("Image-thin.bmp");
                System.out.println("Imagenes apachada exitosamente!!!");

                System.out.println("Todos las imagenes fueron generadas existosamente!!!");
                break;
            default:
                System.out.println("Bandera no reconocida. Use -help para más información.");
        }


    }

    private static void printHelp() {
        System.out.println("Instrucciones de uso...");
        System.out.println("-basics <imagen.bmp> Con este comando se generara imagenes de color azul, rojo, verde y sepia");
        System.out.println("-rotate <imagen.bmp> Con este comando se rotaran las imagenes 180 grados de su horizontal y vertical");
        System.out.println("-resize <imagen.bmp> Con este comando se aplastara un 50% la anchura de la imagen");
        System.out.println("-all <imagen.bmp> Con este comando se generara todo lo anteriormente indicado");
        System.out.println("Es recomendable que tenga paciencia, ya que dependiendo de las limitaciones de su equipo es asi el tiempo que se va a tardar en generar las imagenes");
    }

}