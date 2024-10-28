package ru.netology.graphics;

import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ConvertImg implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema = new Swap();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        ImageIO.write(img, "png", new File("out.png"));
        System.out.println(img.getWidth());
        System.out.println(img.getHeight());

        if (maxRatio < img.getHeight() / img.getWidth()) {//не понятно как вычислить этот maxRatio
            throw new BadImageSizeException(maxRatio, img.getHeight() / img.getWidth());
        }
        int newWidth = 0;
        int newHeight = 0;
        if (width < img.getWidth() || height < img.getHeight() && width != 0) {
            double size1 = (double) img.getWidth() / width;
            double size2 = (double) img.getHeight() / height;
            newWidth = (int) (img.getWidth() / size1);
            newHeight = (int) (img.getHeight() / size2);
        } else {
            newHeight = height;
            newWidth = width;
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        // А этому инструменту скажем, чтобы он скопировал содержимое из нашей суженной картинки:
        graphics.drawImage(scaledImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out2.png"));
        WritableRaster bwRaster = bwImg.getRaster();
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < bwRaster.getHeight(); h++) {
            builder.append("\n");
            for (int w = 0; w < bwRaster.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                builder.append(c + " ");
            }
        }
        return builder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
