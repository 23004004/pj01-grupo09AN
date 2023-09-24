import java.io.*;

public class BmpHandlerRotator {

    private byte[] header;
    private byte[][][] pixelMatrix;
    private int width;
    private int height;

    public BmpHandlerRotator(String filename) {
        try {
            FileInputStream imagen = new FileInputStream(filename);

            header = new byte[54];
            imagen.read(header, 0, 54);

            width = getIntValue(header, 18);
            height = getIntValue(header, 22);

            pixelMatrix = new byte[height][width][3];

            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    pixelMatrix[y][x][2] = (byte) imagen.read();
                    pixelMatrix[y][x][1] = (byte) imagen.read();
                    pixelMatrix[y][x][0] = (byte) imagen.read();
                }
            }
            imagen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getIntValue(byte[] bytes, int start) {
        // Convertir cada byte a un valor entero sin signo y luego desplazarlo a su posición correcta
        int byte1 = bytes[start] & 0xFF; // Byte menos significativo
        int byte2 = (bytes[start + 1] & 0xFF) << 8;
        int byte3 = (bytes[start + 2] & 0xFF) << 16;
        int byte4 = (bytes[start + 3] & 0xFF) << 24; // Byte más significativo

        // Combinar los 4 bytes para formar un entero de 32 bits y devolverlo
        return byte1 | byte2 | byte3 | byte4;
    }

    public void horizontalRotation(String outputFilename) {
        System.out.println("Volteando imagen horizontalmente...");
        try {
            FileOutputStream image = new FileOutputStream(outputFilename);
            image.write(header);

            // Recorremos la matriz de píxeles desde la parte superior hasta la inferior
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    image.write(pixelMatrix[y][x][2]);
                    image.write(pixelMatrix[y][x][1]);
                    image.write(pixelMatrix[y][x][0]);
                }
            }
            image.close();
            System.out.println("Imagen volteada horizontalmente!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verticalRotation(String outputFilename) {
        try {
            System.out.println("Volteando imagen verticalmente...");
            FileOutputStream image = new FileOutputStream(outputFilename);
            image.write(header);

            // Recorremos la matriz de píxeles desde la parte derecha hasta la izquierda
            for (int y = height - 1; y >= 0; y--) {
                for (int x = width - 1; x >= 0; x--) {
                    image.write(pixelMatrix[y][x][2]);
                    image.write(pixelMatrix[y][x][1]);
                    image.write(pixelMatrix[y][x][0]);
                }
            }
            image.close();
            System.out.println("Imagen volteada verticalmente!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}