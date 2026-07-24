package com.jfranbob.modules.records.sealedhierarchies;

public final class ShapeClassifier {

    private ShapeClassifier() {}

    public static String describe(Shape shape) {
        return switch (shape) {
            case Circle c -> "Circle of radius " + c.radius() + ", area=" + c.area();
            case Rectangle r -> "Rectangle " + r.width() + "x" + r.height() + ", area=" + r.area();
            case Triangle t -> "Triangle sides " + t.a() + "," + t.b() + "," + t.c() + ", area=" + t.area();
        };
    }

    public static boolean isPolygonal(Shape shape) {
        return switch (shape) {
            case Circle _ -> false;
            case Rectangle _ -> true;
            case Triangle _ -> true;
        };
    }
}
