public class BMPImageHandler {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se necesita una bandera y un nombre de archivo si necesita ayuda usar -help");
            return;
        }

        String flag = args[0];
        String filename = args[1];

        switch (flag) {
            case "-basics":
            BmpHandlerCore image = new BmpHandlerCore(filename);
            image.generateRedToneImage();
                image.generateGreenToneImage();
                image.generateBlueToneImage();
            image.generateSepiaToneImage("Image-sepia.bmp");
            case "-rotate":
                // Llamar a los métodos de rotación
                break;
            case "-resize":
                // Llamar a los métodos de redimensionamiento
                break;
            case "-all":
                // Llamar a todos los métodos
                break;
            case "-help":
                System.out.println("Instrucciones de uso...");
                
                break;
            default:
                System.out.println("Bandera no reconocida. Use -help para más información.");
        }
    }
}