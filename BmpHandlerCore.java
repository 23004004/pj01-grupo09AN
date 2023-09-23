import java.io.*;

public class BmpHandlerCore {
  private byte[] header;
  private byte[][][] pixelMatrix;
  private int width;
  private int height;

  public BmpHandlerCore(String filename) {
    try {
    FileInputStream imagen = new FileInputStream(filename);

    header = new byte[54];
    imagen.read(header, 0, 54);

    width = getIntValue(header, 18); // Posición 18 en el header contiene el ancho
    height = getIntValue(header, 22); // Posición 22 en el header contiene el alto

    pixelMatrix = new byte[height][width][3];

    for (int y = height - 1; y >= 0; y--) {
      for (int x = 0; x < width; x++) {
        pixelMatrix[y][x][2] = (byte) imagen.read(); // Azul
        pixelMatrix[y][x][1] = (byte) imagen.read(); // Verde
        pixelMatrix[y][x][0] = (byte) imagen.read(); // Rojo
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

  public void generateRedToneImage() {
    // Generar imagen con tonalidades de rojo
    generateToneImage("Image-red.bmp", 0);
  }

  public void generateGreenToneImage() {
    // Generar imagen con tonalidades de verde
    generateToneImage("Image-green.bmp", 1);

  }

  public void generateBlueToneImage() {
    // Generar imagen con tonalidades de azul
    generateToneImage("Image-blue.bmp", 2);

  }

  private void generateToneImage(String outputFilename, int colorIndex) {
     try {
    FileOutputStream image = new FileOutputStream(outputFilename);
    image.write(header);

    for (int y = height - 1; y >= 0; y--) {
      for (int x = 0; x < width; x++) {
        for (int c = 0; c < 3; c++) {
          if (c == colorIndex) {
            image.write(pixelMatrix[y][x][c]);
          } else {
            image.write(0);
          }
        }
      }
    }
    image.close();
 } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void generateSepiaToneImage(String outputFilename) {
   try {
        FileOutputStream imagen = new FileOutputStream(outputFilename);
        imagen.write(header);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalRed = pixelMatrix[y][x][0] & 0xFF;
                int originalGreen = pixelMatrix[y][x][1] & 0xFF;
                int originalBlue = pixelMatrix[y][x][2] & 0xFF;

                int tr = (int) (0.393 * originalRed + 0.769 * originalGreen + 0.189 * originalBlue);
                int tg = (int) (0.349 * originalRed + 0.686 * originalGreen + 0.168 * originalBlue);
                int tb = (int) (0.272 * originalRed + 0.534 * originalGreen + 0.131 * originalBlue);

                // Asegurarse de que los valores estén en el rango [0, 255]
                tr = Math.min(255, tr);
                tg = Math.min(255, tg);
                tb = Math.min(255, tb);

                imagen.write(tb);
                imagen.write(tg);
                imagen.write(tr);
            }
        }
        imagen.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}