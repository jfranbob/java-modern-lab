package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.sealedhierarchies.Circle;
import com.jfranbob.modules.records.sealedhierarchies.Rectangle;
import com.jfranbob.modules.records.sealedhierarchies.Shape;
import com.jfranbob.modules.records.sealedhierarchies.ShapeClassifier;
import com.jfranbob.modules.records.sealedhierarchies.Triangle;
import org.junit.jupiter.api.Test;

class ShapeClassifierTest {

    @Test
    void shouldDescribeCircle() {
        Shape shape = new Circle(5);
        String description = ShapeClassifier.describe(shape);
        assertTrue(description.contains("radius 5.0"));
    }

    @Test
    void shouldDescribeRectangle() {
        Shape shape = new Rectangle(3, 4);
        String description = ShapeClassifier.describe(shape);
        assertTrue(description.contains("3.0x4.0"));
    }

    @Test
    void shouldIdentifyPolygonal() {
        assertFalse(ShapeClassifier.isPolygonal(new Circle(1)));
        assertTrue(ShapeClassifier.isPolygonal(new Rectangle(1, 2)));
        assertTrue(ShapeClassifier.isPolygonal(new Triangle(3, 4, 5)));
    }

    @Test
    void shouldCalculateCircleArea() {
        Circle circle = new Circle(5);
        assertEquals(Math.PI * 25, circle.area(), 0.0001);
    }

    @Test
    void shouldCalculateRectangleArea() {
        Rectangle rect = new Rectangle(3, 4);
        assertEquals(12, rect.area());
    }

    @Test
    void shouldCalculateTriangleArea() {
        Triangle tri = new Triangle(3, 4, 5);
        assertEquals(6, tri.area(), 0.0001);
    }

    @Test
    void shouldRejectInvalidTriangle() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(1, 1, 3));
    }

    @Test
    void shouldRejectNegativeRadius() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(-1));
    }
}
