package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchema;

public class Swap implements TextColorSchema {
    @Override
    public char convert(int color) {

        return color < 100 ? '#' : color < 120 ? '$' : color < 130 ? '@' : color < 140 ?
                '%' : color < 180 ? '*' : color < 190 ? '+' : color < 250 ? '-' : '\'';
    }
}

//#', '$', '@', '%', '*', '+', '-', '''.