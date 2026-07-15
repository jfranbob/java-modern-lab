package com.jfranbob.modules.records.sealedhierarchies;

public sealed interface Shape permits Circle, Rectangle, Triangle {

    double area();

    double perimeter();
}
