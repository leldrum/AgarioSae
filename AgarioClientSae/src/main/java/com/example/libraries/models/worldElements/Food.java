package com.example.libraries.models.worldElements;

import java.io.Serializable;

public class Food extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    public Food(double x, double y, double size) {
        super(x, y, size);
    }
}
