import java.io.*;

public class BmpHandlerResizer {

    private byte[] header;
    private byte[][][] pixelMatrix;
    private int width;
    private int height;

    public BmpHandlerResizer(String filename) {
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

    private void updateHeaderForWidth(int newWidth) {
        header[18] = (byte) newWidth;
        header[19] = (byte) (newWidth >> 8);
        header[20] = (byte) (newWidth >> 16);
        header[21] = (byte) (newWidth >> 24);
    }
    
    public void thinImage(String outputFilename) {
        try {

            int newWidth = width / 2;
            updateHeaderForWidth(newWidth);

            System.out.println("Aplastando 50% de su ancho...");
            FileOutputStream image = new FileOutputStream(outputFilename);
            image.write(header);

            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < newWidth; x++) {
                    image.write(pixelMatrix[y][x * 2][2]);
                    image.write(pixelMatrix[y][x * 2][1]);
                    image.write(pixelMatrix[y][x * 2][0]);
                }
                int padding = (4 - (newWidth * 3) % 4) % 4;
                for (int p = 0; p < padding; p++) {
                    image.write(0);
                }
            }
            image.close();
            System.out.println("Imagen aplastada 50% de su ancho!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}